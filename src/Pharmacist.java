import java.util.Scanner;

public class Pharmacist extends User {

    private InventoryManagement inventoryManagement;
    private PrescriptionManager prescriptionManager;

    public Pharmacist(String userID, String password, String name) {
        super(userID, password, name, Role.PHARMACIST);
        inventoryManagement = new InventoryManagement("Medicine_List.csv"); // Use relative path
        prescriptionManager = new PrescriptionManager();
    }

    @Override
    public void displayRoleSpecificMenu() {
        showMenu();
    }

    private void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        try {
            do {
                System.out.println("\nPharmacist Menu:");
                System.out.println("1. View Appointment Outcome Record");
                System.out.println("2. Update Prescription Status");
                System.out.println("3. View Medication Inventory");
                System.out.println("4. Check Stock Levels");
                System.out.println("5. Submit Replenishment Request");
                System.out.println("6. Logout");
                System.out.print("Enter your choice: ");

                while (!scanner.hasNextInt()) { // Input validation
                    System.out.print("Invalid input. Please enter a number: ");
                    scanner.next(); // Clear invalid input
                }

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewAppointmentOutcomeRecord();
                        break;
                    case 2:
                        updatePrescriptionStatus(scanner);
                        break;
                    case 3:
                        viewMedicationInventory();
                        break;
                    case 4:
                        checkStockLevels();
                        break;
                    case 5:
                        submitReplenishmentRequest(scanner);
                        break;
                    case 6:
                        logout();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 6);
        } finally {
            
        }
    }

    private void viewAppointmentOutcomeRecord() {
        // Placeholder for viewing appointment outcome records
        System.out.println("Displaying appointment outcome records...");
    }

    private void updatePrescriptionStatus(Scanner scanner) {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Prescription ID to update: ");
        String prescriptionId = scanner.nextLine();

        System.out.print("Enter new status for the prescription (e.g., 'Filled', 'Pending'): ");
        String status = scanner.nextLine();

        prescriptionManager.updatePrescriptionStatus(prescriptionId, status);
    }

    private void viewMedicationInventory() {
        inventoryManagement.printInventory();
    }

    private void checkStockLevels() {
        inventoryManagement.checkStockLevels(); // Call to check stock levels
    }

    private void submitReplenishmentRequest(Scanner scanner) {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Medicine Name for replenishment request: ");
        String name = scanner.nextLine();

        inventoryManagement.submitReplenishmentRequest(name); // Call to submit replenishment request
    }

    
}
