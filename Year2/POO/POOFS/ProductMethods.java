package POOFS;

import java.util.ArrayList;

/**
 * Provides methods for creating, editing, and searching products.
 */
public class ProductMethods {
    private final VerifyingMethods vm;
    private final PharmacyMethods pm;
    private final FoodMethods fm;

    /**
     * Constructs a ProductMethods object and initializes the VerifyingMethods, PharmacyMethods, and FoodMethods instances.
     */
    public ProductMethods() {
        this.vm = new VerifyingMethods();
        this.pm = new PharmacyMethods();
        this.fm = new FoodMethods();
    }

    /**
     * Creates a new product.
     *
     * @param invoice the invoice associated with the product
     * @param invoices the list of all invoices
     * @return the created product, or null if the product already exists
     */
    public Product createProduct(Invoice invoice, ArrayList<Invoice> invoices){
        System.out.println("\n--------- Add Product ---------");

        String id = String.format("%09d", vm.numVerifier(1, 999999999, "ID (1 - 999999999) --> ", "Invalid ID"));

        if (vm.productExists(id, invoices)) {
            System.out.println("Product already exists.");
            return null;
        }

        String name = vm.nameVerifier("\nProduct name --> ");

        String description = vm.descriptionVerifier();

        int quantity = vm.numVerifier(0, Integer.MAX_VALUE, "Quantity --> ", "Invalid quantity");

        double price = vm.priceVerifier();

        String type = vm.productTypeVerifier();

        if (type.equalsIgnoreCase("pharmacy")) {return pm.createPharmacy(invoice, id, name, description, quantity, price);}
        else {return fm.createFood(invoice, id, name, description, quantity, price);}
    }

    /**
     * Edits an existing product.
     *
     * @param product the product to edit
     * @param invoices the list of all invoices
     * @return the edited product, or null if the new product ID already exists
     */
    public Product editProduct(Product product, ArrayList<Invoice> invoices){
        System.out.println("\n--------- Edit Product ---------");

        if(vm.boolVerifier("Edit Product ID? (yes/no) --> ")){
            String id = String.format("%09d", vm.numVerifier(1, 999999999, "New Product ID (1 - 999999999) --> ", "Invalid ID"));
            product.setId(id);

            if (vm.productExists(id, invoices)) {
                System.out.println("Product already exists.");
                return null;
            }
        }

        if(vm.boolVerifier("Edit Product Name? (yes/no) --> ")){
            String name = vm.nameVerifier("\nNew Product Name --> ");
            product.setName(name);
        }

        if(vm.boolVerifier("Edit Description? (yes/no) --> ")){
            String description = vm.descriptionVerifier();
            product.setDescription(description);
        }

        if(vm.boolVerifier("Edit Quantity? (yes/no) --> ")){
            int quantity = vm.numVerifier(0, Integer.MAX_VALUE, "New Quantity --> ", "Invalid quantity");
            product.setQuantity(quantity);
        }

        if(vm.boolVerifier("Edit Price? (yes/no) --> ")){
            double price = vm.priceVerifier();
            product.setPrice(price);
        }

        if (product.getProdType().equals("Pharmacy")) {
            return pm.editPharmacy((Pharmacy) product);
        } else {
            return fm.editFood((Food) product);
        }
    }

    /**
     * Searches for a product by its ID.
     *
     * @param products the list of all products
     * @return the found product, or null if the product is not found
     */
    public Product productSearch(ArrayList<Product> products){
        String searchingId;

        System.out.println("\n--------- Search Product ---------");

        do{
            searchingId = String.format("%09d", vm.numVerifier(1, 999999999, "Product ID (1 - 999999999) --> ", "Invalid product ID"));
            for (Product product : products) {
                if (product.getId().equals(searchingId)) {
                    return product;
                }
            }
            System.out.println("\nProduct not found.");

        } while (vm.boolVerifier("Try again? (yes/no) --> "));
        return null;
    }
}
