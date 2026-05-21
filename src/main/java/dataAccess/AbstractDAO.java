package dataAccess;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;

/**
 * Clasa generica care contine operatiile CRUD pentru orice entitate din baza de date.
 * Foloseste reflection pentru a construi query-urile SQL in mod dinamic.
 *
 * @param <T> tipul entitatii pentru care se realizeaza operatiile
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    /**
     * Constructorul care obtine tipul generic T prin reflection.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Construim query-ul SELECT cu conditie WHERE pentru un anumit camp.
     *
     * @param field numele campului folosit in conditia WHERE
     * @return query-ul SELECT construit
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" WHERE ").append(field).append(" = ?");
        return sb.toString();
    }

    /**
     * Construim query-ul SELECT pentru toate inregistrarile din tabel.
     *
     * @return query-ul SELECT construit
     */
    private String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName().toLowerCase());
        return sb.toString();
    }

    /**
     * Construim query-ul INSERT pentru entitatea curenta folosind reflection.
     *
     * @return query-ul INSERT construit
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" (");
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName());
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append(") VALUES (");
        for (int i = 1; i < fields.length; i++) {
            sb.append("?");
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Construim query-ul UPDATE pentru entitatea curenta folosind reflection.
     *
     * @return query-ul UPDATE construit
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" SET ");
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName()).append(" = ?");
            if (i < fields.length - 1) sb.append(", ");
        }
        sb.append(" WHERE id = ?");
        return sb.toString();
    }

    /**
     * Construim query-ul DELETE pentru entitatea curenta.
     *
     * @return query-ul DELETE construit
     */
    private String createDeleteQuery() {
        return "DELETE FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";
    }

    /**
     * Cream o lista de obiecte de tip T din rezultatul unui query folosind reflection.
     *
     * @param resultSet rezultatul query-ului executat
     * @return lista de obiecte de tip T
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T instance = type.getDeclaredConstructor().newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Eroare la crearea obiectelor din ResultSet: " + e.getMessage());
        }
        return list;
    }

    /**
     * Cautam o inregistrare dupa id-ul sau.
     *
     * @param id id-ul inregistrarii cautate
     * @return obiectul de tip T gasit sau null daca nu exista
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> results = createObjects(resultSet);
            if (!results.isEmpty()) return results.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Returnam toate inregistrarile din tabel.
     *
     * @return lista de obiecte de tip T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return new ArrayList<>();
    }

    /**
     * Inseram o noua inregistrare in tabel.
     *
     * @param object obiectul de tip T de inserat
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int insert(T object) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field[] fields = type.getDeclaredFields();
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(i, fields[i].get(object));
            }
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    /**
     * Actualizam o inregistrare existenta in tabel.
     *
     * @param object obiectul de tip T cu datele actualizate
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int update(T object) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            Field[] fields = type.getDeclaredFields();
            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(i, fields[i].get(object));
            }
            fields[0].setAccessible(true);
            statement.setObject(fields.length, fields[0].get(object));
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }

    /**
     * Stergem o inregistrare din tabel dupa id-ul sau.
     *
     * @param id id-ul inregistrarii de sters
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }
}