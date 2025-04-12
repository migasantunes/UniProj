package projetoPOOAndreMiguel;

import java.util.ArrayList;

/**
 * The Poofs class is the main entry point for the application.
 * It initializes components and provides a menu interface for managing clients and invoices.
 *
 * <p>Functionalities:
 * <ul>
 *   <li>Add a new client</li>
 *   <li>Manage existing clients</li>
 *   <li>List all clients</li>
 *   <li>List all invoices</li>
 *   <li>View statistics</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 * {@code
 * public static void main(String[] args) {
 *     new Poofs();
 * }
 * }
 * </pre>
 *
 * <p>Classes Containing The Used Methods:
 * <ul>
 *   <li>VerifyingMethods: Input verification</li>
 *   <li>ClientMethods: Client operations</li>
 *   <li>InvoiceMethods: Invoice operations</li>
 *   <li>FileHandler: File operations</li>
 * </ul>
 *
 * <p>Data Structures:
 * <ul>
 *   <li>ArrayListInvoice: List of invoices</li>
 *   <li>ArrayListClient: List of clients</li>
 * </ul>
 *
 * <p>Menu Options:
 * <ul>
 *   <li>1 - Add Client</li>
 *   <li>2 - Existing Client</li>
 *   <li>3 - List Clients</li>
 *   <li>4 - List Invoices</li>
 *   <li>5 - Statistics</li>
 *   <li>0 - Exit</li>
 * </ul>
 *
 * <p>Client Menu Options:
 * <ul>
 *   <li>1 - Add Invoice</li>
 *   <li>2 - Show Invoice</li>
 *   <li>3 - Client Statistics</li>
 *   <li>4 - Edit Invoice</li>
 *   <li>5 - Edit Client</li>
 *   <li>0 - Return</li>
 * </ul>
 *
 * @see VerifyingMethods
 * @see ClientMethods
 * @see InvoiceMethods
 * @see FileHandler
 */
public class Poofs {
    /**
     * Initializes the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        new Poofs();
    }

    /**
     * Constructs a Poofs object in order to run the application.
     */
    public Poofs() {
        VerifyingMethods vm = new VerifyingMethods();
        ClientMethods cm = new ClientMethods();
        InvoiceMethods im = new InvoiceMethods();
        FileHandler fh = new FileHandler();

        ArrayList<Invoice> invoices = new ArrayList<>();
        ArrayList<Client> clients = new ArrayList<>();
        fh.importData("clients.txt", clients, invoices);
        
        int option1 = -1;

        while (option1!=0){
            
            System.out.println("\n--------- Poofs ---------");
            System.out.println("1 - Add Client\n2 - Existing Client\n3 - List Clients\n4 - List Invoices\n5 - Statistics\n\n0 - Exit");
            System.out.println("-------------------------");
            
            option1 = vm.numVerifier(0, 5, String.format("Choose an option (%d - %d)--> ", 0, 5), "Please choose a valid option");

            switch(option1){
                case 1:
                Client newClient = cm.createClient(clients, invoices);
                if (newClient != null){clients.add(newClient);}
                break;

                case 2:
                if (clients.isEmpty()) {
                    System.out.println("No clients found.");
                    break;
                }

                Client currentClient = cm.clientSearch(clients);
                
                if (currentClient != null) {
                        int option2 = -1;
                        ArrayList<Invoice> clientInvoices;

                        while (option2 != 0){

                            System.out.println("\n--------- Client Menu ---------");
                            System.out.println("1 - Add Invoice\n2 - Show Invoice\n\n3 - Client Statistics\n\n4 - Edit Invoice\n5 - Edit Client\n0 - Return");
                            System.out.println("-------------------------------");

                            option2 = vm.numVerifier(0, 5, String.format("Choose an option (%d - %d)--> ", 0, 5), "Please choose a valid option");

                            switch (option2){
                                case 1:
                                    Invoice invoice;
                                    clientInvoices = currentClient.getInvoices();

                                    do {
                                        invoice = im.createInvoice(currentClient, invoices);
                                        if (invoice != null) {
                                            clientInvoices.add(invoice);
                                            invoices.add(invoice);

                                            currentClient.setInvoices(clientInvoices);
                                        }
                                    } while (vm.boolVerifier("Add more invoices? (yes/no) --> "));

                                    break;
                                case 2:
                                    Invoice currentInvoice = im.invoiceSearch(currentClient.getInvoices());

                                    if (currentInvoice != null) {im.showInvoice(currentInvoice);}
                                    else {System.out.println("Invoice not found.");}

                                    break;
                                case 3:
                                    cm.showStatistics(currentClient);
                                    break;
                                case 4:
                                    clientInvoices = currentClient.getInvoices();
                                    Invoice invoiceToEdit = im.invoiceSearch(clientInvoices);

                                    clientInvoices.remove(invoiceToEdit);
                                    invoices.remove(invoiceToEdit);

                                    invoiceToEdit = im.editInvoice(invoiceToEdit, invoices);
                                    
                                    clientInvoices.add(invoiceToEdit);
                                    invoices.add(invoiceToEdit);

                                    currentClient.setInvoices(clientInvoices);
                                    break;
                                case 5:
                                    currentClient = cm.editClient(currentClient, clients);
                                    break;
                                case 0:
                                    System.out.println("Returning...");
                                    break;
                            }
                        }
                    }else{System.out.println("Client not found.");}
                    break;

                case 3:
                    cm.showClients(clients);
                    break;
                case 4:
                    im.showInvoices(invoices);
                    break;
                case 5:
                    im.showStatistics(invoices);
                    break;
                case 0:
                    System.out.println("quitting...");
                    fh.exportData(clients, "projetoPOOAndreMiguel/clients.txt");
                    break;
           }
        }
    }
}
