package businessLogic;

import dataAccess.ClientDAO;
import model.Client;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Clasa care contine logica de business pentru operatiile asupra entitatii Client.
 * Realizeaza validarea datelor inainte de a le trimite catre stratul de acces la date.
 */
public class ClientBLL {
    private ClientDAO clientDAO = new ClientDAO();

    /**
     * Cautam un client dupa id-ul sau.
     *
     * @param id id-ul clientului cautat
     * @return obiectul {@link Client} gasit
     * @throws NoSuchElementException daca clientul nu a fost gasit
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("Client with id " + id + " not found!");
        }
        return client;
    }

    /**
     * Returnam lista tuturor clientilor din baza de date.
     *
     * @return lista de obiecte {@link Client}
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Inseram un client nou in baza de date dupa validarea datelor.
     *
     * @param client obiectul {@link Client} de inserat
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int insertClient(Client client) {
        validateClient(client);
        return clientDAO.insert(client);
    }

    /**
     * Actualizam datele unui client existent dupa validarea datelor.
     *
     * @param client obiectul {@link Client} cu datele actualizate
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int updateClient(Client client) {
        validateClient(client);
        return clientDAO.update(client);
    }

    /**
     * Stergem un client din baza de date dupa id-ul sau.
     *
     * @param id id-ul clientului de sters
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int deleteClient(int id) {
        return clientDAO.delete(id);
    }

    /**
     * Validam datele unui client inainte de inserare sau actualizare.
     *
     * @param client obiectul {@link Client} de validat
     * @throws IllegalArgumentException daca datele clientului sunt invalide
     */
    private void validateClient(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be empty!");
        }
        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Client email cannot be empty!");
        }
        List<String> errors = List.of(client.getName(), client.getEmail())
                .stream()
                .filter(s -> s == null || s.isEmpty())
                .collect(Collectors.toList());
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Invalid client data!");
        }
    }
}