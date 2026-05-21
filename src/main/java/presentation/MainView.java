package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Fereastra principala a aplicatiei de management al comenzilor.
 * Contine butoane pentru deschiderea ferestrelor de management al clientilor,
 * produselor si comenzilor.
 */
public class MainView extends JFrame {

    private JButton clientButton;
    private JButton productButton;
    private JButton orderButton;

    /**
     * Constructorul ferestrei principale.
     * Initializeaza componentele grafice si actiunile butoanelor.
     */
    public MainView() {
        setTitle("Orders Management");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        clientButton = new JButton("Manage Clients");
        productButton = new JButton("Manage Products");
        orderButton = new JButton("Create Order");

        /**
         * Deschidem fereastra de management al clientilor.
         */
        clientButton.addActionListener(e -> {
            ClientView clientView = new ClientView();
            clientView.setVisible(true);
        });

        /**
         * Deschidem fereastra de management al produselor.
         */
        productButton.addActionListener(e -> {
            ProductView productView = new ProductView();
            productView.setVisible(true);
        });

        /**
         * Deschidem fereastra de creare a comenzilor.
         */
        orderButton.addActionListener(e -> {
            OrderView orderView = new OrderView();
            orderView.setVisible(true);
        });

        add(clientButton);
        add(productButton);
        add(orderButton);

        setVisible(true);
    }

    /**
     * Metoda principala care porneste aplicatia.
     *
     * @param args argumentele liniei de comanda
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainView());
    }
}