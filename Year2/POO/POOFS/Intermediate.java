package projetoPOOAndreMiguel;

/**
 * Represents an intermediate food product with a class.
 */
public class Intermediate extends Food {
    /**
     * The class of the food
     */
    private String foodClass;

    /**
     * Constructs an Intermediate food product.
     *
     * @param invoice the invoice associated with the food product
     * @param id the unique identifier of the food product
     * @param name the name of the food product
     * @param description the description of the food product
     * @param quantity the quantity of the food product
     * @param price the price of the food product
     * @param prodType the type of the product
     * @param isBio true if the food product is organic, false otherwise
     * @param taxType the tax type of the food product
     * @param foodClass the class of the food product
     */
    public Intermediate(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType, boolean isBio, String taxType, String foodClass) {
        super(invoice, id, name, description, quantity, price, prodType, isBio, taxType);
        this.foodClass = foodClass;
    }

    /**
     * Returns the class of the food product.
     *
     * @return the class of the food product
     */
    public String getFoodClass() {return this.foodClass;}

    /**
     * Sets the class of the food product.
     *
     * @param foodClass the class of the food product
     */
    public void setFoodClass(String foodClass) {this.foodClass = foodClass;}

    /**
     * Calculates the tax for the intermediate food product.
     *
     * @return the calculated tax
     */
    @Override
    public double calculateTax() {
        double tax = 0;

        if (this.foodClass.equalsIgnoreCase("wine")) {
            tax += 0.01;
        }

        if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("portugal")) {
            tax += 0.13;
        } else if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("madeira")) {
            tax += 0.12;
        } else {
            tax += 0.09;
        }

        if(this.getIsBio()) {
            tax *= 0.9;
        }
        return tax;
    }

    /**
     * @return the product data as a string
     */
    @Override
    public String productData(){
        return "#" + this.getIsBio() + "#" + this.getTaxType() + "#" + this.getFoodClass();
    }

    /**
     * @return a string representation of the intermediate food product
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
            "\n   Bio: " + super.getIsBio() +
            "\n   Tax Type: " + super.getTaxType() +
            "\n   Food Class: " + this.getFoodClass() +
            "\n   Price w/o IVA: " + String.format("%.2f", price) +
            "\n   Tax: " + String.format("%.2f", tax) +
            "\n   Price of IVA: " + String.format("%.2f", price * tax )+
            "\n   Price with IVA: " + String.format("%.2f", price + (price * tax) );
    }
}