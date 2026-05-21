package model;

/**
 * Clasa model care reprezinta un produs din baza de date.
 * Contine informatiile despre un produs: nume, pret si stoc disponibil.
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    /**
     * Constructorul fara parametri necesar pentru reflection.
     */
    public Product() {}

    /**
     * Constructorul cu toti parametrii inclusiv id-ul.
     *
     * @param id id-ul produsului
     * @param name numele produsului
     * @param price pretul produsului
     * @param stock stocul disponibil al produsului
     */
    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Constructorul fara id folosit la inserarea unui produs nou.
     *
     * @param name numele produsului
     * @param price pretul produsului
     * @param stock stocul disponibil al produsului
     */
    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Returnam id-ul produsului.
     *
     * @return id-ul produsului
     */
    public int getId() { return id; }

    /**
     * Setam id-ul produsului.
     *
     * @param id id-ul produsului
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returnam numele produsului.
     *
     * @return numele produsului
     */
    public String getName() { return name; }

    /**
     * Setam numele produsului.
     *
     * @param name numele produsului
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returnam pretul produsului.
     *
     * @return pretul produsului
     */
    public double getPrice() { return price; }

    /**
     * Setam pretul produsului.
     *
     * @param price pretul produsului
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Returnam stocul disponibil al produsului.
     *
     * @return stocul disponibil
     */
    public int getStock() { return stock; }

    /**
     * Setam stocul disponibil al produsului.
     *
     * @param stock stocul disponibil
     */
    public void setStock(int stock) { this.stock = stock; }
}