package POOFS;

/**
 * Represents a NonPrescription pharmacy product.
 */
public class NonPrescription extends Pharmacy{
    /**
     * The category of the pharmacy product.
     */
    String pharmacyClass;

    /**
     * Constructs a NonPrescription pharmacy product .
     *
     * @param invoice the invoice associated with the product
     * @param id the product ID
     * @param name the product name
     * @param description the product description
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @param prodType the type of the product
     * @param hasPrescription whether the product requires a prescription
     * @param pharmacyClass the category of the pharmacy product
     */
    public NonPrescription(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType, boolean hasPrescription, String pharmacyClass) {
        super(invoice, id, name, description, quantity, price, prodType, hasPrescription);
        this.pharmacyClass = pharmacyClass;
    }

    /**
     * Gets the category of the NonPrescription pharmacy product.
     *
     * @return the NonPrescription pharmacy product category
     */
    public String getCategory() {return this.pharmacyClass;}

    /**
     * Sets the category of the NonPrescription pharmacy product.
     *
     * @param pharmacyClass the new NonPrescription pharmacy product category
     */
    public void setCategory(String pharmacyClass) {this.pharmacyClass = pharmacyClass;}


    /**
     * Calculates the tax for the NonPrescription Pharmacy product.
     *
     * @return the tax rate
     */
    @Override
    public double calculateTax() {
        double tax = 0.23;
        
        if(this.pharmacyClass.equalsIgnoreCase("animais")) {tax -= 0.01;}

        return tax;
    }

    /**
     * @return the product data as a string
     */
    @Override
    public String productData(){
        return "#" + this.getHasPrescription() + "#" + this.getCategory();
    }

    /**
     * @return the string representation of the product
     */
    @Override
    public String toString() {
        double tax = this.calculateTax();
        double price = super.getPrice();
        
        return "\n   Product ID: " + super.getId() +
            "\n   Product Name: " + super.getName() +  
            "\n   Quantity: " + super.getQuantity() +
            "\n   Description: " + super.getDescription() +
            "\n   Product Type: " + super.getProdType() +
            "\n   Prescription: " + super.getHasPrescription() +
            "\n   Category: " + this.getCategory() +
            "\n   Price w/o IVA: " + String.format("%.2f", price) +
            "\n   Tax: " + String.format("%.2f", tax) +
            "\n   Price of IVA: " + String.format("%.2f", price * tax )+
            "\n   Price with IVA: " + String.format("%.2f", price + (price * tax) );
    }
}
