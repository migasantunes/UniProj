package projetoPOOAndreMiguel;

/**
 * Represents a prescription pharmacy product.
 */
public class Prescription extends Pharmacy{
    /**
     * The name of the doctor who prescribed the product.
     */
    String docName;

    /**
     * Constructs a Prescription instance.
     *
     * @param invoice the invoice associated with the product
     * @param id the product ID
     * @param name the product name
     * @param description the product description
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @param prodType the product type
     * @param hasPrescription whether the product has a prescription
     * @param docName the name of the doctor who prescribed the product
     */
    public Prescription(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType, boolean hasPrescription, String docName) {
        super(invoice, id, name, description, quantity, price, prodType, hasPrescription);
        this.docName = docName;
    }

    /**
     * Gets the doctor's name.
     *
     * @return the doctor's name
     */
    public String getDocName() {return this.docName;}

    /**
     * Sets the doctor's name.
     *
     * @param docName the doctor's name
     */
    public void setDocName(String docName) {this.docName = docName;}


    /**
     * Calculates the tax for a prescription pharmacy product.
     *
     * @return the tax rate
     */
    @Override
    public double calculateTax() {
        double tax = 0;
        
        if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("portugal")) {
            tax += 0.06;
        } else if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("madeira")) {
            tax += 0.05;
        } else {
            tax += 0.04;
        }
        return tax;
    }

    /**
     * @return the product data as a string
     */
    @Override
    public String productData(){
        return "#" + this.getHasPrescription() + "#" + this.getDocName();
    }

    /**
     * @return the string representation of the prescription product
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
            "\n   Doctor Name: " + this.getDocName() +
            "\n   Price w/o IVA: " + String.format("%.2f", price) +
            "\n   Tax: " + String.format("%.2f", tax) +
            "\n   Price of IVA: " + String.format("%.2f", price * tax )+
            "\n   Price with IVA: " + String.format("%.2f", price + (price * tax) );
    }
}
