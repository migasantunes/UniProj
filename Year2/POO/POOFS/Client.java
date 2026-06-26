package POOFS;

import java.util.ArrayList;

/**
 * Represents a client with a name, NIF, location, and a list of invoices.
 */
public class Client {
    /**
     * The name of the client.
     */
    private String name;
    /**
     * The NIF of the client.
     */
    private String nif;
    /**
     * The location of the client.
     */
    private String location;
    /**
     * The list of invoices associated with the client.
     */
    private ArrayList<Invoice> invoices;

    /**
     * Constructs a new Client with the specified name, NIF, location, and list of invoices.
     *
     * @param name the name of the client
     * @param nif the NIF of the client
     * @param location the location of the client
     * @param invoices the list of invoices associated with the client
     */
    public Client(String name, String nif, String location, ArrayList<Invoice> invoices) {
        this.name = name;
        this.nif = nif;
        this.location = location;
        this.invoices = invoices;
    }

    /**
     * Returns the name of the client.
     *
     * @return the name of the client
     */
    public String getName() {return this.name;}

    /**
     * Returns the NIF of the client.
     *
     * @return the NIF of the client
     */
    public String getNif() {return this.nif;}

    /**
     * Returns the location of the client.
     *
     * @return the location of the client
     */
    public String getLocation() {return this.location;}

    /**
     * Returns the list of invoices associated with the client.
     *
     * @return the list of invoices associated with the client
     */
    public ArrayList<Invoice> getInvoices() {return this.invoices;}

    /**
     * Sets the name of the client.
     *
     * @param name the name of the client
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the NIF of the client.
     *
     * @param nif the NIF of the client
     */
    public void setNif(String nif) {this.nif = nif;}

    /**
     * Sets the location of the client.
     *
     * @param location the location of the client
     */
    public void setLocation(String location) {this.location = location;}

    /**
     * Sets the list of invoices associated with the client.
     *
     * @param invoices the list of invoices associated with the client
     */
    public void setInvoices(ArrayList<Invoice> invoices) {this.invoices = invoices;}


    /**
     * Returns a string representation of the client.
     *
     * @return a string representation of the client
     */
    @Override
    public String toString() {
        return "Name: " + this.name + "\nNIF: " + this.nif + "\nLocation: " + this.location;
    }
}