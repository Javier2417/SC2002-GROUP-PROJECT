import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

// Class for managing prescriptions
class PrescriptionManager {
    private static final String PRESCRIPTION_STATUS_FILE = "/Users/matzchan/docs/Y2S1/sc2002 object/project/Hospital Management System (HMS)/data/PrescriptionStatus.csv"; // Adjust path as needed

    public PrescriptionManager() {
        createPrescriptionStatusFileIfNotExists(); // Check and create file if not exists
    }

    private void createPrescriptionStatusFileIfNotExists() {
        // Check if the file exists
        if (!Files.exists(Paths.get(PRESCRIPTION_STATUS_FILE))) {
            try (FileWriter writer = new FileWriter(PRESCRIPTION_STATUS_FILE)) {
                // Write header for prescription status file
                writer.write("Prescription ID,Status\n");
                System.out.println("Created PrescriptionStatus.csv with header.");
            } catch (IOException e) {
                System.out.println("Error creating PrescriptionStatus.csv: " + e.getMessage());
            }
        }
    }

    public void updatePrescriptionStatus(String prescriptionId, String status) {
        try (FileWriter writer = new FileWriter(PRESCRIPTION_STATUS_FILE, true)) {
            writer.write(prescriptionId + "," + status + "\n");
            System.out.println("Prescription status updated successfully.");
        } catch (IOException e) {
            System.out.println("Error updating prescription status: " + e.getMessage());
        }
    }
}