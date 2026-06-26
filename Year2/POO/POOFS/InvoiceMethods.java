package POOFS;

import java.util.ArrayList;

/**
 * Provides methods to create, edit, search, and display invoices.
 */
public class InvoiceMethods {
    private final VerifyingMethods vm;
    private final ProductMethods pm;

    /**
     * Constructs an InvoiceMethods object and initializes the VerifyingMethods and ProductMethods instances.
     */
    public InvoiceMethods() {
        this.vm = new VerifyingMethods();
        this.pm = new ProductMethods();

    }

    /**
     * Creates a new invoice for a given client.
     *
     * @param client the client for whom the invoice is created
     * @param invoices the list of existing invoices
     * @return the created invoice, or null if the invoice could not be created
     */
    public Invoice createInvoice(Client client, ArrayList<Invoice> invoices) {
        ArrayList<Product> products = new ArrayList<>();
        Product product;

        System.out.println("\n--------- Add Invoice ---------");

        String number = String.format("%09d", vm.numVerifier(1, 999999999, "Number (1 - 999999999) --> ", "Invalid number"));

        if (vm.invoiceExists(number, invoices)) {
            System.out.println("Invoice already exists.\n");
            return null;
        }

        String date = vm.dateVerifier();

        Invoice invoice = new Invoice(client, number, date, null);

        do { 
            product = pm.createProduct(invoice, invoices);

            if (product != null){
                products.add(product);
                invoice.setProducts(products);
                
                System.out.println("\nProduct added successfully!\n");

                invoice.getProducts().forEach(productInvoice -> productInvoice.setInvoice(invoice));
            }

        } while (vm.boolVerifier("Add more products? (yes/no) --> "));

        if (invoice.getProducts() == null || invoice.getProducts().isEmpty()) {
            System.out.println("\nInvoice must have at least one product.\n\n--------------------------");
            return null;
        }

        System.out.println("\nInvoice added successfully!\n\n--------------------------");

        return invoice;
    }

    /**
     * Edits an existing invoice.
     *
     * @param invoice the invoice to be edited
     * @param invoices the list of existing invoices
     * @return the edited invoice, or null if the invoice could not be edited
     */
    public Invoice editInvoice(Invoice invoice, ArrayList<Invoice> invoices) {
        ArrayList<Product> products = invoice.getProducts();
        Product productToAdd;
        Product productToRemove;
        Product productToEdit;

        System.out.println("\n--------- Edit Invoice ---------");

        if (vm.boolVerifier("\nEdit Invoice Number? (yes/no) --> ")) {
            String number = String.format("%09d", vm.numVerifier(1, 999999999, "Invoice Number (1 - 999999999) --> ", "Invalid invoice number"));
            invoice.setNumber(number);

            if (vm.invoiceExists(number, invoice.getClient().getInvoices())) {
                System.out.println("Invoice already exists.");
                return null;
            }
        }

        if (vm.boolVerifier("\nEdit Date? (yes/no) --> ")) {
            String date = vm.dateVerifier();
            invoice.setDate(date);
        }
        
        while (vm.boolVerifier("\nWould you like to add more products? (yes/no) --> ")){
            productToAdd = pm.createProduct(invoice, invoices);
            if (productToAdd != null){
                products.add(productToAdd);
                invoice.setProducts(products);
                System.out.print("\nProduct added successfully!\n");

                invoice.getProducts().forEach(product -> product.setInvoice(invoice));
            }
        }
        
        while (vm.boolVerifier("\nWould you like to edit a product? (yes/no) --> ")){
            productToEdit = pm.productSearch(products);
            if (productToEdit != null){
                products.set(products.indexOf(productToEdit), pm.editProduct(productToEdit, invoices));
                invoice.setProducts(products);
                System.out.print("\nProduct edited successfully!");

                invoice.getProducts().forEach(product -> product.setInvoice(invoice));
            }
        }
        
        while (vm.boolVerifier("\nWould you like to remove a product? (yes/no) --> ")){
            productToRemove = pm.productSearch(products);
            if (productToRemove != null){
                products.remove(productToRemove);
                invoice.setProducts(products);
                System.out.print("\nProduct removed successfully!");
                
                invoice.getProducts().forEach(product -> product.setInvoice(invoice));
            }
        }

        if (invoice.getProducts() == null || invoice.getProducts().isEmpty()) {
            System.out.println("\nInvoice must have at least one product.\n\n--------------------------");
            return null;
        }

        System.out.println("\nInvoice updated successfully!\n\n--------------------------"); 

        return invoice;
    }

    /**
     * Searches for an invoice by its number.
     *
     * @param invoices the list of existing invoices
     * @return the found invoice, or null if the invoice was not found
     */
    public Invoice invoiceSearch(ArrayList<Invoice> invoices) {
        String searchingNumber;
        System.out.println("\n------- Search Invoice -------");

        do{
            searchingNumber = String.format("%09d", vm.numVerifier(1, 999999999, "Invoice Number (1 - 999999999) --> ", "Invalid invoice number"));
            for (Invoice invoice : invoices) {
                if (invoice.getNumber().equals(searchingNumber)) {
                    return invoice;
                }
            }
            System.out.println("Invoice not found.");

        } while (vm.boolVerifier("Try again? (yes/no) --> "));
        return null;
    }

    /**
     * Displays the details of a given invoice.
     *
     * @param invoice the invoice to be displayed
     */
    public void showInvoice(Invoice invoice) {
        double totalNoIva = 0;
        double totalOfIva = 0;
        double totalWithIva = 0;
        double iva;
        double price;

        System.out.println("\n--------- Invoice ---------\n" + invoice + "\nProducts:");
        for (Product product : invoice.getProducts()) {
            System.out.println(product);

            price = product.getPrice();
            iva = product.calculateTax();
            totalNoIva += price;
            totalOfIva += iva*price;
            totalWithIva += iva*price + price;
        }
        System.out.println("\nTotal without IVA: " + String.format("%.2f", totalNoIva) +
                            "\nTotal of IVA: " + String.format("%.2f", totalOfIva) +
                            "\nTotal with IVA: " + String.format("%.2f", totalWithIva) +
                            "\n\n--------------------------");
    }

    /**
     * Displays a list of all invoices.
     *
     * @param invoices the list of existing invoices
     */
    public void showInvoices(ArrayList<Invoice> invoices) {
        System.out.println("\n------- List Invoices -------\n");

        if (invoices.isEmpty()) {
            System.out.println("The Invoice list is empty.");
            return;
        }
        
        for (Invoice invoice : invoices) {

            int numberOfProducts = 0;
            double totalNoIva = 0;
            double totalWithIva = 0;
            double iva;
            double price;

            for (Product product : invoice.getProducts()) {
                price = product.getPrice();
                iva = product.calculateTax();
                numberOfProducts += product.getQuantity();

                totalNoIva += price;
                totalWithIva += iva*price + price;
            }

            System.out.println("Invoice Id: " + invoice.getNumber() + 
                                "\nClient: " + invoice.getClient().getName() +
                                "\nLocation: " + invoice.getClient().getLocation() +
                                "\nNumber of products: " + numberOfProducts +
                                "\nTotal w/o IVA: " + String.format("%.2f", totalNoIva) +
                                "\nTotal with IVA: " + String.format("%.2f", totalWithIva) + "\n");
        }

        System.out.println("--------------------------------");
    }

    /**
     * Displays statistics for a list of invoices.
     *
     * @param invoices the list of existing invoices
     */
    public void showStatistics(ArrayList<Invoice> invoices) {
        if (invoices.isEmpty()) {
            System.out.println("\nThe Invoice list is empty.\n");
            return;
        }

        int numberOfProducts = 0;
        double totalNoIva = 0;
        double totalOfIva = 0;
        double totalWithIva = 0;
        double iva;
        double price;

        System.out.println("\n---------- Statistics ----------\n");
        for (Invoice invoice : invoices) {
            for (Product product : invoice.getProducts()) {
                price = product.getPrice();
                iva = product.calculateTax();
                numberOfProducts += product.getQuantity();

                totalNoIva += price;
                totalOfIva += iva*price;
                totalWithIva += iva*price + price;
            }
        }
        System.out.println("Number of invoices: " + invoices.size() +
                            "\nNumber of products: " + numberOfProducts +
                            "\nTotal without IVA: " +  String.format("%.2f", totalNoIva) +
                            "\nTotal of IVA: " +  String.format("%.2f", totalOfIva) +
                            "\nTotal with IVA: " +  String.format("%.2f", totalWithIva));
        System.out.println("\n-------------------------------");
    }
}
