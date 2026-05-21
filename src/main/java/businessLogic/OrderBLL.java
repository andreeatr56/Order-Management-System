package businessLogic;

import dataAccess.OrderDAO;
import dataAccess.BillDAO;
import model.Orders;
import model.Bill;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa care contine logica de business pentru operatiile asupra entitatii Orders.
 * Gestioneaza crearea comenzilor, decrementarea stocului si generarea bill-urilor.
 */
public class OrderBLL {
    private OrderDAO ordersDAO = new OrderDAO();
    private BillDAO billDAO = new BillDAO();
    private ProductBLL productBLL = new ProductBLL();

    /**
     * Cautam o comanda dupa id-ul sau.
     *
     * @param id id-ul comenzii cautate
     * @return obiectul {@link Orders} gasit
     * @throws NoSuchElementException daca comanda nu a fost gasita
     */
    public Orders findOrderById(int id) {
        Orders order = ordersDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("Order with id " + id + " not found!");
        }
        return order;
    }

    /**
     * Returnam lista tuturor comenzilor din baza de date.
     *
     * @return lista de obiecte {@link Orders}
     */
    public List<Orders> findAllOrders() {
        return ordersDAO.findAll();
    }

    /**
     * Inseram o comanda noua in baza de date, decrementam stocul produsului
     * si generam un bill care este salvat in tabelul log.
     *
     * @param order obiectul {@link Orders} de inserat
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     * @throws IllegalArgumentException daca stocul produsului este insuficient
     */
    public int insertOrder(Orders order) {
        productBLL.decrementStock(order.getProductId(), order.getQuantity());

        int result = ordersDAO.insert(order);

        double totalPrice = productBLL.findProductById(order.getProductId()).getPrice() * order.getQuantity();
        Bill bill = new Bill(0, order.getClientId(), order.getProductId(), order.getQuantity(), totalPrice, null);
        billDAO.insert(bill);

        return result;
    }

    /**
     * Stergem o comanda din baza de date dupa id-ul sau.
     *
     * @param id id-ul comenzii de sters
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */
    public int deleteOrder(int id) {
        return ordersDAO.delete(id);
    }
}