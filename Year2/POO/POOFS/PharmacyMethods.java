package projetoPOOAndreMiguel;

/**
 * Provides methods for creating and editing pharmacy products.
 */
public class PharmacyMethods {
    private final VerifyingMethods vm;

    /**
     * Constructs a PharmacyMethods instance.
     */
    public PharmacyMethods() {
        this.vm = new VerifyingMethods();
    }

    /**
     * Creates a pharmacy product.
     *
     * @param invoice the invoice associated with the product
     * @param id the product ID
     * @param name the product name
     * @param description the product description
     * @param quantity the quantity of the product
     * @param price the price of the product
     * @return the created pharmacy product
     */
    public Pharmacy createPharmacy(Invoice invoice, String id, String name, String description, int quantity, double price) {

        boolean hasPrescription = vm.boolVerifier("Does your medicine have a prescription? (yes/no): ");

        if (hasPrescription) {
            String doctorName = vm.nameVerifier("Doctor's name --> ");
            
            return new Prescription(invoice, id, name, description, quantity, price, "Pharmacy", true, doctorName);
        }else{
            System.out.println("What category does your medicine belong to?\n");
            System.out.println("1 - Beauty\n2 - Well-being\n3 - Babies\n4 - Animals\n5 - Others");

            int choice = vm.numVerifier(1, 5, String.format("Choose an option (%d - %d)--> ", 1, 5), "Please choose a valid option");
            String pharmacyClass = "";

            switch (choice) {
                case 1:
                    pharmacyClass = "Beauty";
                    break;
                case 2:
                    pharmacyClass = "Well-being";
                    break;
                case 3:
                    pharmacyClass = "Babies";
                    break;
                case 4:
                    pharmacyClass = "Animals";
                    break;
                case 5:
                    pharmacyClass = "Others";
                    break;
            }
            return new NonPrescription(invoice, id, name, description, quantity, price, "Pharmacy", false, pharmacyClass);
        }
    }

    /**
     * Edits a pharmacy product.
     *
     * @param pharmacy the pharmacy product to be edited
     * @return the edited pharmacy product
     */
    public Pharmacy editPharmacy(Pharmacy pharmacy){

        if (pharmacy.getHasPrescription()) {
            Prescription prescription = (Prescription) pharmacy;
            if (vm.boolVerifier("Edit Doctor's name? (yes/no) --> ")) {
                prescription.setDocName(vm.nameVerifier("Doctor's name --> "));
            }
            return prescription;

        } else {
            NonPrescription nonPrescription = (NonPrescription) pharmacy;
            if (vm.boolVerifier("Edit Category? (yes/no) --> ")) {
                System.out.println("What category does your medicine belong to?\n");
                System.out.println("1 - Beauty\n2 - Well-being\n3 - Babies\n4 - Animals\n5 - Others");

                int choice = vm.numVerifier(1, 5, String.format("Choose an option (%d - %d)--> ", 1, 5), "Please choose a valid option");
                String pharmacyClass = "";

                switch (choice) {
                    case 1:
                        pharmacyClass = "Beauty";
                        break;
                    case 2:
                        pharmacyClass = "Well-being";
                        break;
                    case 3:
                        pharmacyClass = "Babies";
                        break;
                    case 4:
                        pharmacyClass = "Animals";
                        break;
                    case 5:
                        pharmacyClass = "Others";
                        break;
                }
                nonPrescription.setCategory(pharmacyClass);
            }
            return nonPrescription; 
        }
    }
}