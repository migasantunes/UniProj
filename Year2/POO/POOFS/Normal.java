package POOFS;

/**
 * Represents a normal food product.
 */
public class Normal extends Food {
    /**
     * Constructs a Normal food product.
     *
     * @param invoice the invoice associated with the product
     * @param id the product ID
     * @param name the product name
     * @param description the product description
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @param prodType the type of the product
     * @param isBio whether the product is bio
     * @param taxType the tax type of the product
     */
    public Normal(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType, boolean isBio, String taxType) {
        super(invoice, id, name, description, quantity, price, prodType, isBio, taxType);
    }

    /**
     * Calculates the tax for the normal food product.
     *
     * @return the tax rate
     */
    @Override
    public double calculateTax() {
        double tax = 0;
        
        if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("portugal")) {
            tax += 0.23;
        } else if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("madeira")) {
            tax += 0.22;
        } else {
            tax += 0.16;
        }

        if( this.getIsBio()) {
            tax *= 0.9;
        }
        return tax;
    }

    /**
     * @return the normal food product data as a string
     */
    @Override
    public String productData(){
        return "#" + this.getIsBio() + "#" + this.getTaxType();
    }

    /**
     * @return the string representation of the normal food product
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
            "\n   Price w/o IVA: " + String.format("%.2f", price) +
            "\n   Tax: " + String.format("%.2f", tax) +
            "\n   Price of IVA: " + String.format("%.2f", price * tax )+
            "\n   Price with IVA: " + String.format("%.2f", price + (price * tax) );
    }
}