package POOFS;

/**
 * Represents a pharmacy product.
 */
public abstract class Pharmacy extends Product {
    /**
     * Whether the product has a prescription.
     */
    private Boolean hasPrescription;

    /**
     * Constructs a Pharmacy product.
     *
     * @param invoice the invoice associated with the product
     * @param id the product ID
     * @param name the product name
     * @param description the product description
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @param prodType the type of the product
     * @param hasPrescription whether the product has a prescription
     */
    public Pharmacy(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType, boolean hasPrescription) {
        super(invoice, id, name, description, quantity, price, prodType);
        this.hasPrescription = hasPrescription;
    }

    /**
     * Gets whether the product has a prescription.
     *
     * @return if the product has a prescription
     */
    public Boolean getHasPrescription() {return this.hasPrescription;}

    /**
     * Sets whether the product has a prescription.
     *
     * @param hasPrescription if the product has a prescription
     */
    public void setHasPrescription(Boolean hasPrescription) {this.hasPrescription = hasPrescription;}

    /**
     * @return the calculated tax of Prescription or NonPrescription pharmacy products
     */
    @Override
    public abstract double calculateTax();

    /**
     * @return the pharmacy product data as a string
     */
    @Override
    public abstract String productData();

    /**
     * @return the string representation of the pharmacy product
     */
    @Override
    public abstract String toString();
}
