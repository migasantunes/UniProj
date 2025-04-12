package projetoPOOAndreMiguel;

/**
 * Represents a Food product.
 */
public abstract class Food extends Product {
    /**
     * Whether the food product is organic.
     */
    private boolean isBio;
    /**
     * The tax type of the food product (Reduced,Intermediate or Normal).
     */
    private String taxType;

    /**
     * Constructs a Food object.
     *
     * @param invoice the invoice associated with the food product
     * @param id the unique identifier of the food product
     * @param name the name of the food product
     * @param description the description of the food product
     * @param quantity the quantity of the food product
     * @param price the price of the food product
     * @param prodType the type of the food product
     * @param isBio whether the food product is organic
     * @param taxType the tax type of the food product
     */
    public Food(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType, boolean isBio, String taxType) {
        super(invoice, id, name, description, quantity, price, prodType);
        this.isBio = isBio;
        this.taxType = taxType;
    }


    /**
     * Returns whether the food product is organic.
     *
     * @return true if the food product is organic, false otherwise
     */
    public boolean getIsBio() {return this.isBio;}

    /**
     * Returns the tax type of the food product.
     *
     * @return the tax type of the food product
     */
    public String getTaxType() {return this.taxType;}

    /**
     * Sets whether the food product is organic.
     *
     * @param isBio true if the food product is organic, false otherwise
     */
    public void setIsBio(boolean isBio) {this.isBio = isBio;}

    /**
     * Sets the tax type of the food product.
     *
     * @param taxType the tax type of the food product
     */
    public void setTaxType(String taxType) {this.taxType = taxType;}

    /**
     * @return the calculated tax of Intermediate, Reduced or Normal food products
     */
    @Override
    public abstract double calculateTax();

    /**
     * @return the product data as a string
     */
    @Override
    public abstract String productData();

    /**
     * @return a string representation of the food product
     */
    @Override
    public abstract String toString();
}