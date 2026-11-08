package dataAccess;

import model.Client;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Clasa DAO pentru entitatea Client.
 * Mosteneste operatiile CRUD din clasa {@link AbstractDAO}.
 */

public class ClientDAO extends AbstractDAO<Client> {

    @Override
    public int delete(int id) {
        Connection connection = null;
        PreparedStatement deleteOrders = null;
        PreparedStatement deleteClient = null;

        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false); // începem tranzacția

            // 1. Ștergem comenzile asociate clientului
            deleteOrders = connection.prepareStatement(
                    "DELETE FROM orders WHERE clientId = ?"
            );
            deleteOrders.setInt(1, id);
            deleteOrders.executeUpdate();

            // 2. Ștergem clientul
            deleteClient = connection.prepareStatement(
                    "DELETE FROM client WHERE id = ?"
            );
            deleteClient.setInt(1, id);
            int rows = deleteClient.executeUpdate();

            connection.commit(); // confirmăm tranzacția
            return rows;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
            try {
                if (connection != null) connection.rollback(); // anulăm dacă ceva eșuează
            } catch (SQLException ex) {
                LOGGER.log(Level.WARNING, "Rollback failed: " + ex.getMessage());
            }
            return -1;
        } finally {
            ConnectionFactory.close(deleteOrders);
            ConnectionFactory.close(deleteClient);
            ConnectionFactory.close(connection);
        }
    }
}