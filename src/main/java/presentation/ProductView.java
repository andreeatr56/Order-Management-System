package presentation;

import businessLogic.ProductBLL;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Fereastra pentru operatiile asupra entitatii Product.
 * Permite adaugarea, editarea, stergerea si vizualizarea produselor.
 */
public class ProductView extends JFrame {

    private ProductBLL productBLL = new ProductBLL();

    private JTextField idField;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField stockField;
    private JTable productTable;
    private JScrollPane scrollPane;

    /**
     * Constructorul ferestrei de management al produselor.
     * Initializeaza componentele grafice si actiunile butoanelor.
     */
    public ProductView() {
        setTitle("Product Management");
        setSize(850, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        inputPanel.add(new JLabel("ID (for edit/delete):"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Stock:"));
        stockField = new JTextField();
        inputPanel.add(stockField);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View All");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        productTable = new JTable();
        scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Products"));

        /**
         * Adaugam un produs nou in baza de date cu datele din campurile de input.
         */
        addButton.addActionListener(e -> {
            try {
                Product product = new Product(
                        nameField.getText(),
                        Double.parseDouble(priceField.getText()),
                        Integer.parseInt(stockField.getText())
                );
                productBLL.insertProduct(product);
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Actualizam datele unui produs existent folosind id-ul si datele din campurile de input.
         */
        editButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Product product = new Product(
                        id,
                        nameField.getText(),
                        Double.parseDouble(priceField.getText()),
                        Integer.parseInt(stockField.getText())
                );
                productBLL.updateProduct(product);
                JOptionPane.showMessageDialog(this, "Product updated successfully!");
                refreshTable();
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Stergem un produs din baza de date folosind id-ul din campul de input.
         */
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                productBLL.deleteProduct(id);
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
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
     * Actualizam tabelul cu lista de produse din baza de date.
     */
    private void refreshTable() {
        List<Product> products = productBLL.findAllProducts();
        productTable.setModel(Controller.createTableModel(products));
    }

    /**
     * Golim campurile de input dupa o operatie.
     */
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
    }
}