package POOFS;

import java.util.ArrayList;

/**
 * This class contains methods for managing clients, including creating, editing, searching, and displaying clients.
 */
public class ClientMethods {
    private final VerifyingMethods vm;
    private final InvoiceMethods im;

    /**
     * Constructs a new ClientMethods object and initializes the VerifyingMethods and InvoiceMethods instances.
     */
    public ClientMethods() {
        this.vm = new VerifyingMethods();
        this.im = new InvoiceMethods();
    }

    /**
     * Creates a new client and optionally adds invoices to the client.
     *
     * @param clients the list of existing clients
     * @param overallInvoices the list of all invoices
     * @return the created client, or null if the client already exists
     */
    public Client createClient(ArrayList<Client> clients, ArrayList<Invoice> overallInvoices) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        Invoice invoice;

        System.out.println("\n--------- Add Client ---------\n");

        String name = vm.nameVerifier("Client's Name --> ");

        String nif = vm.nifVerifier();
        
        if (vm.clientExists(nif, clients)) {
            System.out.println("Client already exists.");
            return null;
        }

        String location = vm.locationVerifier("\nLocation (Portugal/Acores/Madeira) --> ");

        Client client = new Client(name, nif, location, null);

        boolean moreinvoices = vm.boolVerifier("\nDo you want to add an invoice? (yes/no) --> ");

        while (moreinvoices){
        
            invoice = im.createInvoice(client, overallInvoices);
            if (invoice != null) {
                invoices.add(invoice);
                overallInvoices.add(invoice);

                client.getInvoices().forEach(invoiceClient -> invoiceClient.setClient(client));
            }
            moreinvoices = vm.boolVerifier("Add more invoices? (yes/no) --> ");
        } 

        client.setInvoices(invoices);

        return client;
    }

    /**
     * Edits the details of an existing client.
     *
     * @param client the client to be edited
     * @param clients the list of existing clients
     * @return the edited client
     */
    public Client editClient(Client client, ArrayList<Client> clients) {
        String oldName = client.getName();
        System.out.println("--------- Edit Client ---------");

        if(vm.boolVerifier("Edit Client Name? (yes/no) --> ")){
            String name = vm.nameVerifier("New Client Name --> ");
            client.setName(name);
        }

        if(vm.boolVerifier("Edit NIF? (yes/no) --> ")){
            String nif = vm.nifVerifier();
            
            if (vm.clientExists(nif, clients)) {
                System.out.println("Client already exists.");
                client.setName(oldName);
                return client;
            }

            client.setNif(nif);
        }

        if(vm.boolVerifier("Edit Location? (yes/no) --> ")){
            String location = vm.locationVerifier("New Location (Portugal/Acores/Madeira) --> ");
            client.setLocation(location);
        }
        
        client.getInvoices().forEach(invoice -> invoice.setClient(client));

        return client;
    }

    /**
     * Searches for a client by NIF.
     *
     * @param clients the list of existing clients
     * @return the found client, or null if the client is not found
     */
    public Client clientSearch(ArrayList<Client> clients) {
        String searchingNif;
        System.out.println("\n--------- Search Client ---------");
        
        do{
            searchingNif = vm.nifVerifier();
            for (Client client : clients) {
                if (client.getNif().equals(searchingNif)) {
                    return client;
                }
            }
            System.out.println("Client not found.");

        } while (vm.boolVerifier("Try again? (yes/no) --> "));
        return null;
    }

    /**
     * Displays the list of all clients.
     *
     * @param clients the list of existing clients
     */
    public void showClients(ArrayList<Client> clients) {
        if (clients.isEmpty()) {
            System.out.println("The Client list is empty.");
            return;
        }
        System.out.println("\n--------- List Clients ---------\n");
        for (Client client : clients) {
            System.out.println(client + "\n");
        }
        System.out.println("--------------------------------");
    }

    /**
     * Displays statistics for a specific client.
     *
     * @param client the client whose statistics are to be displayed
     */
    public void showStatistics(Client client) {
        ArrayList<Invoice> invoices = client.getInvoices();

        if (invoices.isEmpty()) {
            System.out.println("The Client has no invoices.");
            return;
        }

        int numberOfProducts = 0;
        double totalNoIva = 0;
        double totalOfIva = 0;
        double totalWithIva = 0;
        double iva;

        System.out.println("\n------ Client Statistics ------\n");
        for (Invoice invoice : invoices) {
            for (Product product : invoice.getProducts()) {
                iva = product.calculateTax();
                numberOfProducts += product.getQuantity();

                totalNoIva += product.getPrice();
                totalOfIva += iva*product.getPrice();
                totalWithIva += iva*product.getPrice() + product.getPrice();
            }
        }
        System.out.println("Number of invoices: " + invoices.size() +
                            "\nNumber of products: " + numberOfProducts +
                            "\nTotal without IVA: " + String.format("%.2f", totalNoIva) +
                            "\nTotal of IVA: " + String.format("%.2f", totalOfIva) +
                            "\nTotal with IVA: " + String.format("%.2f", totalWithIva));
        System.out.println("\n-------------------------------");
    }
}