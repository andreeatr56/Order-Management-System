package dataAccess;

import model.Bill;
import connection.ConnectionFactory;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa DAO pentru entitatea Bill.
 * Bill-urile pot fi doar inserate in tabelul log, nu si modificate sau sterse.
 */
public class BillDAO
{
    private static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());

    /**
     * Inseram un bill nou in tabelul log.
     *
     * @param bill obiectul {@link Bill} de inserat
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int insert(Bill bill)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO log (clientId, productId, quantity, totalPrice) VALUES (?, ?, ?, ?)";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, bill.clientId());
            statement.setInt(2, bill.productId());
            statement.setInt(3, bill.quantity());
            statement.setDouble(4, bill.totalPrice());
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return -1;
    }
}