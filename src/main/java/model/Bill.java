package model;

import java.sql.Timestamp;

/**
 * Inregistrare imutabila care reprezinta un bill generat pentru fiecare comanda.
 * Bill-urile sunt stocate in tabelul log si nu pot fi modificate dupa creare.
 *
 * @param id id-ul bill-ului
 * @param clientId id-ul clientului care a plasat comanda
 * @param productId id-ul produsului comandat
 * @param quantity cantitatea comandata
 * @param totalPrice pretul total al comenzii
 * @param orderDate data si ora la care a fost plasata comanda
 */
public record Bill(int id, int clientId, int productId, int quantity, double totalPrice, Timestamp orderDate)
{

}