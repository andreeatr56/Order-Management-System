package model;

/**
 * Clasa model care reprezinta un client din baza de date.
 * Contine informatiile despre un client: nume, adresa, email si telefon.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;

    /**
     * Constructorul fara parametri necesar pentru reflection.
     */
    public Client() {}

    /**
     * Constructorul cu toti parametrii inclusiv id-ul.
     *
     * @param id id-ul clientului
     * @param name numele clientului
     * @param address adresa clientului
     * @param email email-ul clientului
     * @param phone numarul de telefon al clientului
     */
    public Client(int id, String name, String address, String email, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Constructorul fara id folosit la inserarea unui client nou.
     *
     * @param name numele clientului
     * @param address adresa clientului
     * @param email email-ul clientului
     * @param phone numarul de telefon al clientului
     */
    public Client(String name, String address, String email, String phone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Returnam id-ul clientului.
     *
     * @return id-ul clientului
     */
    public int getId() { return id; }

    /**
     * Setam id-ul clientului.
     *
     * @param id id-ul clientului
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returnam numele clientului.
     *
     * @return numele clientului
     */
    public String getName() { return name; }

    /**
     * Setam numele clientului.
     *
     * @param name numele clientului
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returnam adresa clientului.
     *
     * @return adresa clientului
     */
    public String getAddress() { return address; }

    /**
     * Setam adresa clientului.
     *
     * @param address adresa clientului
     */
    public void setAddress(String address) { this.address = address; }

    /**
     * Returnam email-ul clientului.
     *
     * @return email-ul clientului
     */
    public String getEmail() { return email; }

    /**
     * Setam email-ul clientului.
     *
     * @param email email-ul clientului
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Returnam numarul de telefon al clientului.
     *
     * @return numarul de telefon al clientului
     */
    public String getPhone() { return phone; }

    /**
     * Setam numarul de telefon al clientului.
     *
     * @param phone numarul de telefon al clientului
     */
    public void setPhone(String phone) { this.phone = phone; }
}