package projetoPOOAndreMiguel;

import java.util.ArrayList;

/**
 * Represents a reduced food product with certificates.
 */
public class Reduced extends Food {
    private ArrayList<String> certificates;

    /**
     * Constructs a Reduced product.
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
     * @param certificates the certificates associated with the product
     */
    public Reduced(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType, boolean isBio, String taxType, ArrayList<String> certificates) {
        super(invoice, id, name, description, quantity, price, prodType, isBio, taxType);
        this.certificates = certificates;
    }

    /**
     * Gets the certificates associated with the product.
     *
     * @return the certificates
     */
    public ArrayList<String> getCertificates() {return this.certificates;}

    /**
     * Sets the certificates associated with the product.
     *
     * @param certificates the certificates to set
     */
    public void setCertificates(ArrayList<String> certificates) {this.certificates = certificates;}


    /**
     * Calculates the tax for the reduced food product.
     *
     * @return the tax rate
     */
    @Override
    public double calculateTax() {
        double tax = 0;

        if (this.getCertificates().size() == 4){
            tax -= 0.01;
        }

        if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("portugal")) {
            tax += 0.06;
        } else if (this.getInvoice().getClient().getLocation().equalsIgnoreCase("madeira")) {
            tax += 0.05;
        } else {
            tax += 0.04;
        }

        if (this.getIsBio()) {
            tax *= 0.9;
        }
        return tax;

    }

    /**
     * @return the product data as a string
     */
    @Override
    public String productData(){
        String info;
        info = "#" + this.getIsBio() + "#" + this.getTaxType() + "#";
        ArrayList<String> certificates = this.getCertificates();

        for (int i = 0; i < certificates.size(); i++) {
            info += certificates.get(i);
            if (i != certificates.size() - 1) {
                info += "!";
            }
        }
        return info;
    }

    /**
     * @return the string representation of the reduced food product
     */
    @Override
    public String toString() {
        double tax = this.calculateTax();
        double price = super.getPrice();

        String buffer = "\n   Product ID: " + super.getId() + 
            "\n   Product Name: " + super.getName() +  
            "\n   Quantity: " + super.getQuantity() +
            "\n   Description: " + super.getDescription() +
            "\n   Product Type: " + super.getProdType() +
            "\n   Bio: " + super.getIsBio() +
            "\n   Tax Type: " + super.getTaxType() +
            "\n   Certificates: ";
        for (String certificate : this.getCertificates()) {
            buffer += certificate + " ";
        }
        buffer += "\n   Price w/o IVA: " + String.format("%.2f", price) +
            "\n   Tax: " + String.format("%.2f", tax) +
            "\n   Price of IVA: " + String.format("%.2f", price * tax )+
            "\n   Price with IVA: " + String.format("%.2f", price + (price * tax) );

        return buffer;
    }

}
