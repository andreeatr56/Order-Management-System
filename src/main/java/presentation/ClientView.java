package presentation;

import businessLogic.ClientBLL;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Fereastra pentru operatiile asupra entitatii Client.
 * Permite adaugarea, editarea, stergerea si vizualizarea clientilor.
 */
public class ClientView extends JFrame {

    private ClientBLL clientBLL = new ClientBLL();

    private JTextField nameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField idField;
    private JTable clientTable;
    private JScrollPane scrollPane;

    /**
     * Constructorul ferestrei de management al clientilor.
     * Initializeaza componentele grafice si actiunile butoanelor.
     */
    public ClientView() {
        setTitle("Client Management");
        setSize(850, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Client Details"));

        inputPanel.add(new JLabel("ID (for edit/delete):"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View All");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        clientTable = new JTable();
        scrollPane = new JScrollPane(clientTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Clients"));

        /**
         * Adaugam un client nou in baza de date cu datele din campurile de input.
         */
        addButton.addActionListener(e -> {
            try {
                Client client = new Client(
                        nameField.getText(),
                        addressField.getText(),
                        emailField.getText(),
                        phoneField.getText()
                );
                clientBLL.insertClient(client);
                JOptionPane.showMessageDialog(this, "Client added successfully!");
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Actualizam datele unui client existent folosind id-ul si datele din campurile de input.
         */
        editButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Client client = new Client(
                        id,
                        nameField.getText(),
                        addressField.getText(),
                        emailField.getText(),
                        phoneField.getText()
                );
                clientBLL.updateClient(client);
                JOptionPane.showMessageDialog(this, "Client updated successfully!");
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Stergem un client din baza de date folosind id-ul din campul de input.
         */
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                clientBLL.deleteClient(id);
                JOptionPane.showMessageDialog(this, "Client deleted successfully!");
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewButton.addActionListener(e -> refreshTable());

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        refreshTable();
    }

    /**
     * Actualizam tabelul cu lista de clienti din baza de date.
     */
    private void refreshTable() {
        List<Client> clients = clientBLL.findAllClients();
        clientTable.setModel(Controller.createTableModel(clients));
    }

    /**
     * Golim campurile de input dupa o operatie.
     */
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        addressField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}