package businessLogic;

import dataAccess.BillDAO;
import model.Bill;

/**
 * Clasa care contine logica de business pentru operatiile asupra entitatii Bill.
 * Bill-urile pot fi doar inserate si citite, nu si modificate sau sterse.
 */

public class BillBLL
{
    private BillDAO billDAO = new BillDAO();

    /**
     * Inseram un bill nou in tabelul log.
     *
     * @param bill obiectul {@link Bill} de inserat
     * @return numarul de randuri afectate sau -1 daca operatia a esuat
     */

    public int insertBill(Bill bill)
    {
        return billDAO.insert(bill);
    }
}