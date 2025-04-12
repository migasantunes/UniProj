package projetoPOOAndreMiguel;

/**
 * Represents a product.
 */
public abstract class Product{
    /**
     * The invoice associated with the product.
     */
    private Invoice invoice;
    /**
     * The product ID.
     */
    private String id;
    /**
     * The product name.
     */
    private String name;
    /**
     * The product description.
     */
    private String description;
    /**
     * The quantity of the product.
     */
    private int quantity;
    /**
     * The price of the product.
     */
    private double price;
    /**
     * The type of the product.
     */
    private String prodType; //Pharmacy or Food

    /**
     * Constructs a Product.
     *
     * @param invoice the invoice associated with the product
     * @param id the product ID
     * @param name the product name
     * @param description the product description
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @param prodType the type of the product
     */
    public Product(Invoice invoice, String id, String name, String description, int quantity, double price, String prodType){
        this.invoice = invoice;
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.prodType = prodType;
    }

    /**
     * Gets the invoice associated with the product.
     *
     * @return the invoice
     */
    public Invoice getInvoice() { return this.invoice; }

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public String getId() { return this.id; }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getName() { return this.name; }

    /**
     * Gets the product description.
     *
     * @return the product description
     */
    public String getDescription() { return this.description; }

    /**
     * Gets the quantity of the product.
     *
     * @return the quantity
     */
    public int getQuantity() { return this.quantity; }

    /**
     * Gets the price of the product.
     *
     * @return the price
     */
    public double getPrice() { return this.price; }

    /**
     * Gets the type of the product.
     *
     * @return the product type
     */
    public String getProdType() { return this.prodType; }


    /**
     * Sets the invoice associated with the product.
     *
     * @param invoice the invoice to set
     */
    public void setInvoice(Invoice invoice) { this.invoice = invoice; }

    /**
     * Sets the product ID.
     *
     * @param id the product ID to set
     */
    public void setId(String id) { this.id = id; }

    /**
     * Sets the product name.
     *
     * @param name the product name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the product description.
     *
     * @param description the product description to set
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Sets the price of the product.
     *
     * @param price the price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Sets the type of the product.
     *
     * @param prodType the product type to set
     */
    public void setProdType(String prodType) { this.prodType = prodType; }


    /**
     * Calculates the tax for the product.
     *
     * @return the tax rate
     */
    public abstract double calculateTax();


    /**
     * Gets the product data as a formatted string.
     *
     * @return the product data
     */
    public abstract String productData();

    /**
     * @return the string representation of the product
     */
    @Override
    public abstract String toString();
}    
