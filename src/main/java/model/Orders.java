package model;

/**
 * Clasa model care reprezinta o comanda din baza de date.
 * Contine informatiile despre o comanda: clientul, produsul si cantitatea comandata.
 */
public class Orders {
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    /**
     * Constructorul fara parametri necesar pentru reflection.
     */
    public Orders() {}

    /**
     * Constructorul cu toti parametrii inclusiv id-ul.
     *
     * @param id id-ul comenzii
     * @param clientId id-ul clientului care a plasat comanda
     * @param productId id-ul produsului comandat
     * @param quantity cantitatea comandata
     */
    public Orders(int id, int clientId, int productId, int quantity) {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Constructorul fara id folosit la inserarea unei comenzi noi.
     *
     * @param clientId id-ul clientului care a plasat comanda
     * @param productId id-ul produsului comandat
     * @param quantity cantitatea comandata
     */
    public Orders(int clientId, int productId, int quantity) {
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Returnam id-ul comenzii.
     *
     * @return id-ul comenzii
     */
    public int getId() { return id; }

    /**
     * Setam id-ul comenzii.
     *
     * @param id id-ul comenzii
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returnam id-ul clientului care a plasat comanda.
     *
     * @return id-ul clientului
     */
    public int getClientId() { return clientId; }

    /**
     * Setam id-ul clientului care a plasat comanda.
     *
     * @param clientId id-ul clientului
     */
    public void setClientId(int clientId) { this.clientId = clientId; }

    /**
     * Returnam id-ul produsului comandat.
     *
     * @return id-ul produsului
     */
    public int getProductId() { return productId; }

    /**
     * Setam id-ul produsului comandat.
     *
     * @param productId id-ul produsului
     */
    public void setProductId(int productId) { this.productId = productId; }

    /**
     * Returnam cantitatea comandata.
     *
     * @return cantitatea comandata
     */
    public int getQuantity() { return quantity; }

    /**
     * Setam cantitatea comandata.
     *
     * @param quantity cantitatea comandata
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }
}