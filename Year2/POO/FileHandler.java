package projetoPOOAndreMiguel;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class that provides methods for importing and exporting data from and to files.
 */
public class FileHandler {
    private final VerifyingMethods vm;

    /**
     * Constructs a FileHandler object and initializes the VerifyingMethods instance.
     */
    public FileHandler() {
        this.vm = new VerifyingMethods();
    }

    /**
     * Imports data from a specified file and fills the provided lists of clients and invoices.
     *
     * @param filePath the path to the file to import data from
     * @param clients the list to fill with imported clients
     * @param invoices the list to fill with imported invoices
     */
    public void importData(String filePath, ArrayList<Client> clients, ArrayList<Invoice> invoices) {
        System.out.println("Importing data from file: " + filePath + "\n");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] clientData = line.split("\\|");
                String[] clientInfo = clientData[0].split(";");
                
                if (!vm.clientVerifier(clientInfo[0], clientInfo[1], clientInfo[2], clients)) {
                    continue;
                }
                String clientName = clientInfo[0];
                String nif = clientInfo[1];
                String location = clientInfo[2];
                Client client = new Client(clientName, nif, location, null);
                
                ArrayList<Invoice> clientInvoices= new ArrayList<>();

                for (int i = 1; i < clientData.length; i++) { // Process faturas
                    String[] invoiceData = clientData[i].split("@");
                    if (!vm.invoiceVerifier(invoiceData[0], invoiceData[1], clientInvoices)) {
                        continue;
                    }
                    String invoiceNumber = invoiceData[0];
                    String invoiceDate = invoiceData[1];
                    ArrayList<Product> overallProductsData = new ArrayList<>();

                    for (int j = 2; j < invoiceData.length; j++) { // Process products
                        String[] productData = invoiceData[j].split("#");
                        
                        if (!vm.productVerifier(productData[0], productData[1], productData[2], productData[3], productData[4], productData[5], clientInvoices)) {
                            continue;
                        }

                        String productId = productData[0];
                        String productName = productData[1];
                        String description = productData[2];
                        int quantity = Integer.parseInt(productData[3]);
                        double price = Double.parseDouble(productData[4]);
                        String productType = productData[5];

                        if (productType.equalsIgnoreCase("food")) {
                            boolean isBio = false;
                            if (!vm.isBoolean(productData[6])) {
                                System.out.println("Invalid isBio value, moving on to next product\n");
                                continue;
                            }
                            if(productData[6].equalsIgnoreCase("true")) {
                                isBio = true;
                            }

                            String taxType = productData[7];

                            if (!vm.taxImportVerifier(taxType)) {
                                continue;
                            }
                            if (taxType.equalsIgnoreCase("reduced")) {
                                ArrayList<String> certifications = new ArrayList<>();
                                String[] certificationsData = productData[8].split("!");
                                if (!vm.certificationsVerifier(certificationsData)) {
                                    continue;
                                }
                                Collections.addAll(certifications, certificationsData);
                                overallProductsData.add(new Reduced(null, productId, productName, description, quantity, price, productType, isBio, taxType, certifications));

                            } else if (taxType.equalsIgnoreCase("intermediate")) {
                                String[] validClasses = {"frozen","canned", "wine"};
                                if (!vm.classVerifier(productData[8], validClasses)) {
                                    continue;
                                }
                                String foodClass = productData[8];
                                overallProductsData.add(new Intermediate(null, productId, productName, description, quantity, price, productType, isBio, taxType, foodClass));

                            } else {overallProductsData.add(new Normal(null, productId, productName, description, quantity, price, productType, isBio, taxType));} 
                        
                        } else {
                            if (!vm.isBoolean(productData[6])) {
                                System.out.println("Invalid Prescription value, moving on to next product\n");
                                continue;
                            }
                            boolean hasPrescription = false;
                            if(productData[6].equalsIgnoreCase("true")) {
                                hasPrescription = true;
                            }
                            if(hasPrescription) {
                                if (!vm.isString(productData[7])) {
                                    System.out.println("Invalid Doctor's name, moving on to next product\n");
                                    continue;
                                }
                                String doctorName = productData[7];
                                overallProductsData.add(new Prescription(null, productId, productName, description, quantity, price, productType, hasPrescription, doctorName));
                            } else {
                                String[] validClasses = {"beauty", "Well-being", "Babies", "animals", "Others"};
                                if (!vm.classVerifier(productData[7], validClasses)) {
                                    continue;
                                }
                                String pharmacyClass = productData[7];
                                overallProductsData.add(new NonPrescription(null, productId, productName, description, quantity, price, productType, hasPrescription, pharmacyClass));
                            }
                        }
                    }

                    if (overallProductsData.isEmpty()) {
                        System.out.println("Invoice " + invoiceNumber + " has no valid products, moving on to next invoice\n");
                        continue;
                    }

                    Invoice invoice = new Invoice(client, invoiceNumber, invoiceDate, overallProductsData);
                    invoice.getProducts().forEach(product -> product.setInvoice(invoice));

                    clientInvoices.add(invoice);
                    invoices.add(invoice);
                }
                client.setInvoices(clientInvoices);
                clientInvoices.forEach(invoice -> invoice.setClient(client));
                
                clients.add(client);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    /**
     * Exports the provided list of clients to a specified file.
     *
     * @param overallClientsData the list of clients to export
     * @param filePath the path to the file to export data to
     */
    public void exportData(ArrayList<Client> overallClientsData, String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Client client : overallClientsData) {
                String clientData = "";
                clientData += client.getName() + ";" +
                            client.getNif() + ";" +
                            client.getLocation();

                for (Invoice invoice : client.getInvoices()) {
                    clientData += "|" +
                                invoice.getNumber() + "@" +
                                invoice.getDate();

                    for (Product product : invoice.getProducts()) {
                        clientData += "@" + product.getId() + "#" +
                                    product.getName() + "#" +
                                    product.getDescription() + "#" +
                                    product.getQuantity() + "#" +
                                    product.getPrice() + "#" +
                                    product.getProdType();
                        
                        String productData = product.productData();
                        clientData += productData;
                    }
                }
                bw.write(clientData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}