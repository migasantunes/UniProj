package projetoPOOAndreMiguel;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains various methods for verifying different types of input data.
 */
public class VerifyingMethods {
    private final Scanner sc;

    /**
     * Constructs a VerifyingMethods object and initializes the Scanner.
     */
    public VerifyingMethods() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Verifies the NIF input.
     *
     * @return a valid NIF as a String
     */
    public String nifVerifier() {
        System.out.print("\nNIF (9 digits)--> ");
        String nif = sc.nextLine();

        while (nif.length() != 9 || !nif.matches("[0-9]+")) {
            System.out.println("NIF must have 9 digits\n");
            System.out.print("\nNIF (9 digits)--> ");
            nif = sc.nextLine();
        }
        
        return nif;
    }

    /**
     * Verifies an integer input within a specified range.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @param message the prompt message
     * @param warningMessage the warning prompt message for invalid input
     * @return a valid integer within the specified range
     */
    public int numVerifier(int min, int max, String message, String warningMessage) {
        int num = min-1;
        while (num<min || num>max) {
            System.out.print("\n" + message);
            while(!sc.hasNextInt()){
                sc.next();
                System.out.println(warningMessage);
                System.out.print("\n" + message);
            }
            num = sc.nextInt();
            
            if (num < min || num > max) {System.out.println(warningMessage);}
        }
        sc.nextLine(); // Clear buffer
        return num;
    }

    /**
     * Verifies a double input for price.
     *
     * @return a valid price as a double
     */
    public double priceVerifier() {
        double num = -1;
        while (num<0) {
            System.out.print("\nPrice --> ");
            while(!sc.hasNextDouble()){
                sc.next();
                System.out.println("Price must only contain numbers");
                System.out.println("\nPrice --> ");
            }
            num = sc.nextDouble();
        }
        sc.nextLine(); // Clear buffer
        return num;
    }

    /**
     * Checks if a given year is a leap year.
     *
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    public boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Verifies the date input.
     *
     * @param date the date to verify
     * @return true if the date is valid, false otherwise
     */
    public boolean validDate(String date) {
        if (!date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")) return true;

        String[] dateSplit = date.split("/");
        int day = Integer.parseInt(dateSplit[0]);
        int month = Integer.parseInt(dateSplit[1]);
        int year = Integer.parseInt(dateSplit[2]);
        
        if (year < 1900 || month < 1 || month > 12 || day < 1 || day > 31) return true;
        
        if (month == 2) {
            if (isLeapYear(year)) {
                return day > 29;
            } else {
                return day > 28;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day > 30;
        }
        return false;
    }

    /**
     * Verifies the date input.
     *
     * @return a valid date as a String
     */
    public String dateVerifier() {
        System.out.print("\nDate (dd/mm/yyyy) --> ");
        String date = sc.nextLine();
        while (validDate(date)) {
            System.out.println("Invalid date");
            System.out.print("\nDate (dd/mm/yyyy) --> ");
            date = sc.nextLine();
        }
        return date;
    }

    /**
     * Verifies the tax type input of a food product.
     *
     * @return a valid tax type as a String
     */
    public String taxTypeVerifier() {
        System.out.print("\nTax Type (reduced/intermediate/normal) --> ");
        String type = sc.nextLine();
        while (!type.equalsIgnoreCase("reduced") && !type.equalsIgnoreCase("intermediate")
            && !type.equalsIgnoreCase("normal")) {
                
            System.out.println("Please enter 'Reduced', 'Intermediate', or 'Normal'\n");
            System.out.print("Tax Type (reduced/intermediate/normal) --> ");
            type = sc.nextLine();
        }
        return type;
    }

    /**
     * Verifies the type of product input (Pharmacy or Food).
     *
     * @return a valid product type as a String
     */
    public String productTypeVerifier() {
        System.out.print("\nType of product (Pharmacy/Food) --> ");
        String type = sc.nextLine();
        while (!type.equalsIgnoreCase("pharmacy") && !type.equalsIgnoreCase("food")) {
            System.out.println("Please enter 'Pharmacy', or 'Food'\n");
            System.out.print("\nType of product (Pharmacy/Food) --> ");
            type = sc.nextLine();
        }
        return type;
    }

    /**
     * Verifies the location input (Portugal, Madeira, or Acores).
     *
     * @param message the prompt message
     * @return a valid location as a String
     */
    public String locationVerifier(String message) {
        System.out.print(message);
        String location = sc.nextLine();
        while (!location.equalsIgnoreCase("portugal") && !location.equalsIgnoreCase("madeira")
        && !location.equalsIgnoreCase("acores")) {
            System.out.println("Please enter 'Portugal', 'Madeira' or 'Acores'\n");
            System.out.print(message);
            location = sc.nextLine();
        }
        return location;
    }

    /**
     * Verifies the boolean input (yes or no).
     *
     * @param message the prompt message
     * @return true if the input is 'yes', false if the input is 'no'
     */
    public boolean boolVerifier(String message) {
        System.out.print(message);
        String answer = sc.nextLine();
        while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")) {
            System.out.println("Please enter 'yes' or 'no'\n");
            System.out.print(message);
            answer = sc.nextLine();
        }
        return answer.equalsIgnoreCase("yes");
    }

    /**
     * Verifies the name input, a valid name contains only letters, spaces, apostrophes, and hyphens.
     *
     * @param message the prompt message
     * @return a valid name as a String
     */
    public String nameVerifier(String message) {
        System.out.print(message);
        String name = sc.nextLine();
        while (name.isEmpty() || !name.matches("[a-zA-Z\\s'-]+")) {
            System.out.println("The name can only contain letters, spaces, apostrophes, hyphens and have atleast 1 caracter\n");
            System.out.print(message);
            name = sc.nextLine();
        }
        return name;
    }

    /**
     * Verifies the description input, a valid description contains at least 5 characters.
     *
     * @return a valid description as a String
     */
    public String descriptionVerifier() {
        System.out.print("\nDescription--> ");
        String description = sc.nextLine();
        while (description.length() < 5) {
            System.out.println("Description must have at least 5 characters\n");
            System.out.print("\nDescription--> ");
            description = sc.nextLine();
        }
        return description;
    }

    /**
     * Verifies the client data.
     *
     * @param name the name of the client
     * @param nif the NIF of the client
     * @param location the location of the client
     * @param clients the list of existing clients
     * @return true if the client data is valid, false otherwise
     */
    public boolean clientVerifier(String name, String nif, String location, ArrayList<Client> clients) {
        if ((name.isEmpty() || !name.matches("[a-zA-Z\\s'-]+"))){
            System.out.println("Invalid client name. Moving on to the next client\n");
            return false;
        }
        else if (nif.length() != 9 || !nif.matches("[0-9]+")){
            System.out.println("Invalid client NIF. Moving on to the next client\n");
            return false;
        }
        else if (!location.equalsIgnoreCase("portugal") && !location.equalsIgnoreCase("madeira")
                && !location.equalsIgnoreCase("acores")){
            System.out.println("Invalid client location. Moving on to the next client\n");
            return false;
        }
        for (Client client : clients) {
            if (clients.isEmpty()) {
                break;
            }   
            if (client.getNif().equals(nif)) {
                System.out.println("Client with NIF " + nif + " already exists. Moving on to the next client\n");
                return false;
            }
        }
        return true;
    }

    /**
     * Verifies the product data.
     *
     * @param id the ID of the product
     * @param name the name of the product
     * @param description the description of the product
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @param type the type of the product
     * @param invoices the list of existing invoices
     * @return true if the product data is valid, false otherwise
     */
    public boolean productVerifier(String id, String name, String description, String quantity, String price, String type, ArrayList<Invoice> invoices) {
        if (id.length() != 9 || !id.matches("[0-9]+")) {
            System.out.println("Invalid product. Moving on to the next product\n");
            return false;
        }else if (name.isEmpty() || !name.matches("[a-zA-Z\\s'-]+")) {
            System.out.println("Invalid product. Moving on to the next product\n");
            return false;
        } else if (description.length() < 5) {
            System.out.println("Invalid product. Moving on to the next product\n");
            return false;
        } else if (!type.equalsIgnoreCase("pharmacy") && !type.equalsIgnoreCase("food")) {
            System.out.println("Invalid product. Moving on to the next product\n");
            return false;
        } else if (quantity.isEmpty() || !quantity.matches("[0-9]+")) {
            System.out.println("Invalid product. Moving on to the next product\n");
            return false;
        }

        double priceValue;
        try {
            priceValue = Double.parseDouble(price);
            if (priceValue < 0) {
                System.out.println("Invalid product. Moving on to the next product\n");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid product. Moving on to the next product\n");
            return false;
        }

        for (Invoice invoice : invoices) {
            if (invoices.isEmpty()) {
                break;
            }   
            for (Product product : invoice.getProducts()) {
                if (product.getId().equals(id)) {
                    System.out.println("Product with ID " + id + " already exists. Moving on to the next product\n");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifies the invoice data.
     *
     * @param number the number of the invoice
     * @param date the date of the invoice
     * @param invoices the list of existing invoices
     * @return true if the invoice data is valid, false otherwise
     */
    public boolean invoiceVerifier(String number, String date, ArrayList<Invoice> invoices) {
        if (validDate(date)){
            System.out.println("Invalid invoice. Moving on to the next invoice\n");
            return false;
        }
        else if (number.length() != 9 || !number.matches("[0-9]+")){
            System.out.println("Invalid invoice. Moving on to the next invoice\n");
            return false;
            
        }
        for (Invoice invoice : invoices) {
            if (invoices.isEmpty()) {
                break;
            }   
            if (invoice.getNumber().equals(number)) {
                System.out.println("Invoice with number " + number + " already exists. Moving on to the next invoice\n");
                return false;
            }
        }
        return true;    
    }

    /**
     * Verifies the tax type of product.
     *
     * @param taxType the tax type to verify
     * @return true if the tax type is valid, false otherwise
     */
    public boolean taxImportVerifier(String taxType) {
        if (!taxType.equalsIgnoreCase("reduced") && !taxType.equalsIgnoreCase("intermediate")
                && !taxType.equalsIgnoreCase("normal")){
            System.out.println("Invalid tax type. Moving on to the next product\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies the certifications.
     *
     * @param certifications the array of certifications to verify
     * @return true if the certifications are valid, false otherwise
     */
    public boolean certificationsVerifier(String[] certifications) {
        String[] validCertifications = {"ISO22000", "FSSC22000", "HACCP", "GMP"};
        
        if (certifications.length < 1 || certifications.length > 4) {
            System.out.println("Invalid certifications. Moving on to the next product\n");
            return false;
        }
        
        for (String cert : certifications) {
            
            boolean isValid = false;
            for (String validCert : validCertifications) {
                if (cert.equals(validCert)) {
                    isValid = true;
                    break;
                }
            }
            
            if (!isValid) {
                System.out.println("Invalid certifications. Moving on to the next product\n");
                return false;
            }
        }
    
        return true;
    }

    /**
     * Checks if a string is a boolean value.
     *
     * @param answer the string to check
     * @return true if the string is 'true' or 'false', false otherwise
     */
    public boolean isBoolean(String answer) {
        return answer.equalsIgnoreCase("true") || answer.equalsIgnoreCase("false");
    }

    /**
     * Verifies the product class.
     *
     * @param actualClass the actual class of the product
     * @param validClasses the array of valid classes
     * @return true if the actual class is valid, false otherwise
     */
    public boolean classVerifier(String actualClass, String[] validClasses) {
        for (String validClass : validClasses) {
            if (actualClass.equalsIgnoreCase(validClass)) {
                return true;
            }
        }
        System.out.println("Invalid product class. Moving on to the next product.\n");
        return false;
    }

    /**
     * Checks if a string is a valid non-empty string containing only letters, spaces, apostrophes, and hyphens.
     *
     * @param str the string to check
     * @return true if the string is valid, false otherwise
     */
    public boolean isString(String str) {
        return !(str.isEmpty() || !str.matches("[a-zA-Z\\s'-]+"));
    }

    /**
     * Checks if a client with the given NIF exists in the list of clients.
     *
     * @param nif the NIF to check
     * @param clients the list of clients
     * @return true if the client exists, false otherwise
     */
    public boolean clientExists(String nif, ArrayList<Client> clients) {
        if (clients.isEmpty()) {return false;}

        for (Client client : clients) {
            if (client.getNif().equals(nif)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an invoice with the given number exists in the list of invoices.
     *
     * @param number the invoice number to check
     * @param invoices the list of invoices
     * @return true if the invoice exists, false otherwise
     */
    public boolean invoiceExists(String number, ArrayList<Invoice> invoices) {
        if (invoices.isEmpty()) {return false;}

        for (Invoice invoice : invoices) {
            if (invoice.getNumber().equals(number)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a product with the given ID exists in the list of invoices.
     *
     * @param id the product ID to check
     * @param invoices the list of invoices
     * @return true if the product exists, false otherwise
     */
    public boolean productExists(String id, ArrayList<Invoice> invoices) {
        for (Invoice invoice : invoices) {
            for (Product product : invoice.getProducts()) {
                if (product.getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }
}
