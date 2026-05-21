package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa factory pentru a realiza conexiunea la baza de date.
 * De asemenea, ofera metode pentru crearea, obtinerea si inchiderea conexiunilor la baza de date.
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ordersdb";
    private static final String USER = "root";
    private static final String PASS = "NanaRoblox56123456$";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructorul privat.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creeam o noua conexiune la baza de date.
     *
     * @return un obiect {@link Connection} sau null daca conexiunea a esuat
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "A aparut o eroare la conectarea la baza de date");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Returnam o conexiune la baza de date folosind instanta singleton.
     *
     * @return un obiect {@link Connection}
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Inchide conexiunea data ca parametru.
     *
     * @param connection conexiunea {@link Connection} de inchis
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A aparut o eroare la inchiderea conexiunii");
            }
        }
    }

    /**
     * Inchidem statement-ul dat ca parametru.
     *
     * @param statement statement-ul {@link Statement} de inchis
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A aparut o eroare la inchiderea statement-ului");
            }
        }
    }

    /**
     * Inchidem result set-ul dat ca parametru.
     *
     * @param resultSet result set-ul {@link ResultSet} de inchis
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A aparut o eroare la inchiderea result set-ului");
            }
        }
    }
}