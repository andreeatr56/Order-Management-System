package presentation;

import businessLogic.ClientBLL;
import businessLogic.OrderBLL;
import businessLogic.ProductBLL;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Fereastra pentru crearea comenzilor de produse.
 * Permite selectarea unui client si a unui produs, introducerea cantitatii
 * si plasarea comenzii. Afiseaza un mesaj de eroare daca stocul este insuficient.
 */
public class OrderView extends JFrame {

    private OrderBLL orderBLL = new OrderBLL();
    private ClientBLL clientBLL = new ClientBLL();
    private ProductBLL productBLL = new ProductBLL();

    private JComboBox<String> clientComboBox;
    private JComboBox<String> productComboBox;
    private JTextField quantityField;
    private JTable orderTable;
    private JScrollPane scrollPane;

    private List<Client> clients;
    private List<Product> products;

    /**
     * Constructorul ferestrei de creare a comenzilor.
     * Initializeaza componentele grafice si actiunile butoanelor.
     */
    public OrderView() {
        setTitle("Order Management");
        setSize(850, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));

        inputPanel.add(new JLabel("Select Client:"));
        clientComboBox = new JComboBox<>();
        inputPanel.add(clientComboBox);

        inputPanel.add(new JLabel("Select Product:"));
        productComboBox = new JComboBox<>();
        inputPanel.add(productComboBox);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton placeOrderButton = new JButton("Place Order");
        JButton viewAllButton = new JButton("View All Orders");
        buttonPanel.add(placeOrderButton);
        buttonPanel.add(viewAllButton);

        orderTable = new JTable();
        scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Orders"));

        loadClients();
        loadProducts();

        /**
         * Plasam o comanda noua cu clientul, produsul si cantitatea selectate.
         * Afisam un mesaj de eroare daca stocul este insuficient.
         */
        placeOrderButton.addActionListener(e -> {
            try {
                int clientIndex = clientComboBox.getSelectedIndex();
                int productIndex = productComboBox.getSelectedIndex();
                int quantity = Integer.parseInt(quantityField.getText());

                int clientId = clients.get(clientIndex).getId();
                int productId = products.get(productIndex).getId();

                Orders order = new Orders(clientId, productId, quantity);
                orderBLL.insertOrder(order);

                JOptionPane.showMessageDialog(this, "Order placed successfully!");
                refreshTable();
                quantityField.setText("");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Under-stock Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewAllButton.addActionListener(e -> refreshTable());

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        refreshTable();
    }

    /**
     * Incarcam lista de clienti din baza de date in combobox.
     */
    private void loadClients() {
        clients = clientBLL.findAllClients();
        clientComboBox.removeAllItems();
        clients.stream()
                .map(c -> c.getId() + " - " + c.getName())
                .forEach(clientComboBox::addItem);
    }

    /**
     * Incarcam lista de produse din baza de date in combobox.
     */
    private void loadProducts() {
        products = productBLL.findAllProducts();
        productComboBox.removeAllItems();
        products.stream()
                .map(p -> p.getId() + " - " + p.getName() + " (stock: " + p.getStock() + ")")
                .forEach(productComboBox::addItem);
    }

    /**
     * Actualizam tabelul cu lista de comenzi din baza de date.
     */
    private void refreshTable() {
        List<Orders> orders = orderBLL.findAllOrders();
        orderTable.setModel(Controller.createTableModel(orders));
    }
}