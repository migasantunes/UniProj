package POOFS;

import java.util.ArrayList;

/**
 * Represents an invoice containing client information, invoice number, date, and a list of products.
 */
public class Invoice {
    /**
     * The client associated with the invoice.
     */
    private Client client;
    /**
     * The invoice number.
     */
    private String number;
    /**
     * The date of the invoice.
     */
    private String date;
    /**
     * The list of products in the invoice.
     */
    private ArrayList<Product> products;

    /**
     * Constructs an Invoice object.
     *
     * @param client the client associated with the invoice
     * @param number the invoice number
     * @param date the date of the invoice
     * @param products the list of products in the invoice
     */
    public Invoice(Client client, String number, String date, ArrayList<Product> products) {
        this.client = client;
        this.number = number;
        this.date = date;
        this.products = products;
    }

    /**
     * Returns the client associated with the invoice.
     *
     * @return the client
     */
    public Client getClient() {return this.client;}

    /**
     * Returns the invoice number.
     *
     * @return the invoice number
     */
    public String getNumber() {return this.number;}

    /**
     * Returns the date of the invoice.
     *
     * @return the date
     */
    public String getDate() {return this.date;}

    /**
     * Returns the list of products in the invoice.
     *
     * @return the list of products
     */
    public ArrayList<Product> getProducts() {return this.products;}

    /**
     * Sets the client associated with the invoice.
     *
     * @param client the client to set
     */
    public void setClient(Client client) {this.client = client;}

    /**
     * Sets the invoice number.
     *
     * @param number the invoice number to set
     */
    public void setNumber(String number) {this.number = number;}

    /**
     * Sets the date of the invoice.
     *
     * @param date the date to set
     */
    public void setDate(String date) {this.date = date;}

    /**
     * Sets the list of products in the invoice.
     *
     * @param products the list of products to set
     */
    public void setProducts(ArrayList<Product> products) {this.products = products;}

    /**
     * @return a string representation of the invoice
     */
    @Override
    //For testing
    public String toString() {
        return "\nNumber: " + this.number +
        "\nClient Name: " + this.client.getName() + 
        "\nClient NIF: " + this.client.getNif() +
        "\nClient Location: " + this.client.getLocation();
    }
}