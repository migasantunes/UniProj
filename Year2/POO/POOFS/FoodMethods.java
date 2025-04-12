package projetoPOOAndreMiguel;

import java.util.ArrayList;

/**
 * A class that provides methods for creating and editing Food products.
 */
public class FoodMethods {
    private final VerifyingMethods vm;

    /**
     * Constructs a FoodMethods object and initializes the VerifyingMethods instance.
     */
    public FoodMethods() {
        this.vm = new VerifyingMethods();
    }

    /**
     * Creates a Food product.
     *
     * @param invoice the invoice associated with the food product
     * @param id the unique identifier of the food product
     * @param name the name of the food product
     * @param description the description of the food product
     * @param quantity the quantity of the food product
     * @param price the price of the food product
     * @return a Food object
     */
    public Food createFood(Invoice invoice, String id, String name, String description, int quantity, double price) {

        boolean isBio = vm.boolVerifier("\nOrganic (yes/no) --> ");

        String taxType = vm.taxTypeVerifier();

        if (taxType.equals("reduced")) {
            ArrayList<String> certificates = new ArrayList<>();
            int option;
            
            String[] allCertificates = {"ISO22000", "FSSC22000", "HACCP", "GMP"};
    
            System.out.println("Choose the cerificates:\n\n1 - ISO22000\n2 - FSSC22000\n3 - HACCP\n4 - GMP");
            
            do { 
                option = vm.numVerifier(1, 4, String.format("Choose an option (%d - %d)--> ", 1, 4), "Please choose a valid option");

                if (certificates.contains(allCertificates[option - 1])) {
                    System.out.println("\nCertificate already added\n");
                
                }else{
                    certificates.add(allCertificates[option - 1]);
                }

                if(certificates.size() == 4){break;}

            } while (vm.boolVerifier("Add more certificates? --> "));
            
            return new Reduced(invoice, id, name, description, quantity, price, "Food", isBio, taxType, certificates);
        
        } else if (taxType.equals("intermediate")) {
            System.out.println("In wich class is your product included?\n");
            System.out.println("1 - Frozen\n2 - Canned\n3 - Wine\n4 - Others");

            int choice = vm.numVerifier(1, 4, String.format("Choose an option (%d - %d)--> ", 1, 4), "Please choose a valid option");
            String foodClass = "";

            switch (choice) {
                case 1:
                    foodClass = "Frozen";
                    break;
                case 2:
                    foodClass = "Canned";
                    break;
                case 3:
                    foodClass = "Wine";
                    break;
                case 4:
                    foodClass = "Others";
                    break;
            }

            return new Intermediate(invoice, id, name, description, quantity, price, "Food", isBio, taxType, foodClass); 

        } else {return new Normal(invoice, id, name, description, quantity, price, "Food", isBio, taxType);}
    }

    /**
     * Edits the properties of an existing Food product.
     *
     * @param food the Food product to be edited
     * @return the edited Food product
     */
    public Food editFood(Food food) {
        if (vm.boolVerifier("\nEdit Organic?  (yes/no) --> ")) {
            food.setIsBio(vm.boolVerifier("Organic (yes/no) --> "));
        }

        if (food.getTaxType().equals("reduced")) {
            Reduced reduced = (Reduced) food;

            if (vm.boolVerifier("\nEdit Certificates? (yes/no) --> ")) {
                ArrayList<String> certificates = new ArrayList<>();
                int option;
                
                String[] allCertificates = {"ISO22000", "FSSC22000", "HACCP", "GMP"};
        
                System.out.println("Which Certificates?\n\n1 - ISO22000\n2 - FSSC22000\n3 - HACCP\n4 - GMP");
                
                do { 
                    option = vm.numVerifier(1, 4, String.format("Choose an option (%d - %d)--> ", 1, 4), "Please choose a valid option");
                    
                    if (certificates.contains(allCertificates[option - 1])) {
                        System.out.println("Certificate already added\n");
                    }else{
                        certificates.add(allCertificates[option - 1]);
                    }

                    if(certificates.size() == 4){break;}

                } while (vm.boolVerifier("Add more certificates? (yes/no) --> "));
                
                reduced.setCertificates(certificates);
            }
            return reduced;
        
        } else if (food.getTaxType().equals("intermediate")) {
            Intermediate intermediate = (Intermediate) food;

            if (vm.boolVerifier("\nEdit Food Class? (yes/no) --> ")) {
                System.out.println("In wich class is your product included?\n");
                System.out.println("1 - Frozen\n2 - Canned\n3 - Wine\n4 - Others");

                int choice = vm.numVerifier(1, 4, String.format("Choose an option (%d - %d)--> ", 1, 4), "Please choose a valid option");
                String foodClass = "";

                switch (choice) {
                    case 1:
                        foodClass = "Frozen";
                        break;
                    case 2:
                        foodClass = "Canned";
                        break;
                    case 3:
                        foodClass = "Wine";
                        break;
                    case 4:
                        foodClass = "Others";
                        break;
                }
                intermediate.setFoodClass(foodClass);
            }
            return intermediate;
        } else {return (Normal) food;}
    }
}