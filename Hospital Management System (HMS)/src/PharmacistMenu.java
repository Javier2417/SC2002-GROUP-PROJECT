import java.util.Map;
import java.util.Scanner;

// Main class for the Pharmacist Menu
public class PharmacistMenu {

    private InventoryManager inventoryManager;
    private PrescriptionManager prescriptionManager;

    public PharmacistMenu() {
        inventoryManager = new InventoryManager();
        prescriptionManager = new PrescriptionManager();
    }

    public static void main(String[] args) {
        PharmacistMenu menu = new PharmacistMenu();
        menu.showMenu();
    }

    private void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

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
                    submitReplenishmentRequest(scanner); // Updated method call
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
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
        inventoryManager.displayInventory();
    }

    private void checkStockLevels() {
        inventoryManager.checkStockLevels(); // Call to check stock levels
    }

   private void submitReplenishmentRequest(Scanner scanner) { // Updated method signature
       scanner.nextLine(); // Consume newline
       System.out.print("Enter Medicine Name for replenishment request: ");
       String name = scanner.nextLine();

       inventoryManager.submitReplenishmentRequest(name); // Call to submit replenishment request
   }
}