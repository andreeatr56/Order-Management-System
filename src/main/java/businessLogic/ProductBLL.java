package businessLogic;

import dataAccess.ProductDAO;
import model.Product;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Clasa care contine logica de business pentru operatiile asupra entitatii Product.
 * Realizeaza validarea datelor si gestionarea stocului produselor.
 */
public class ProductBLL
{
    private ProductDAO productDAO = new ProductDAO();

    /**
     * Cautam un produs dupa id-ul sau.
     *
     * @param id id-ul produsului cautat
     * @return obiectul {@link Product} gasit
     * @throws NoSuchElementException daca produsul nu a fost gasit
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("Product with id " + id + " not found!");
        }
        return product;
    }

    /**
     * Returnam lista tuturor produselor din baza de date.
     *
     * @return lista de obiecte {@link Product}
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Inseram un produs nou in baza de date dupa validarea datelor.
     *
     * @param product obiectul {@link Product} de inserat
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int insertProduct(Product product) {
        validateProduct(product);
        return productDAO.insert(product);
    }

    /**
     * Actualizam datele unui produs existent dupa validarea datelor.
     *
     * @param product obiectul {@link Product} cu datele actualizate
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int updateProduct(Product product) {
        validateProduct(product);
        return productDAO.update(product);
    }

    /**
     * Stergem un produs din baza de date dupa id-ul sau.
     *
     * @param id id-ul produsului de sters
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int deleteProduct(int id) {
        return productDAO.delete(id);
    }

    /**
     * Decrementam stocul unui produs dupa plasarea unei comenzi.
     *
     * @param productId id-ul produsului
     * @param quantity cantitatea de decrementat
     * @throws IllegalArgumentException daca stocul este insuficient
     */
    public void decrementStock(int productId, int quantity) {
        Product product = findProductById(productId);
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Under-stock! Available stock: " + product.getStock());
        }
        product.setStock(product.getStock() - quantity);
        productDAO.update(product);
    }

    /**
     * Validam datele unui produs inainte de inserare sau actualizare.
     *
     * @param product obiectul {@link Product} de validat
     * @throws IllegalArgumentException daca datele produsului sunt invalide
     */
    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty!");
        }
        List<Double> prices = List.of(product.getPrice())
                .stream()
                .filter(p -> p <= 0)
                .collect(Collectors.toList());
        if (!prices.isEmpty()) {
            throw new IllegalArgumentException("Product price must be greater than 0!");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Product stock cannot be negative!");
        }
    }
}