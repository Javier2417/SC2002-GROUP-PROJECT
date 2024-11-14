import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManagement {
    private List<Medication> inventory;
    private Map<String, Medication> medicationMap;
    private CSVUtility csvUtility;

    public InventoryManagement(String csvFilePath) {
        this.csvUtility = new CSVUtility(csvFilePath);
        this.inventory = new ArrayList<>();
        this.medicationMap = new HashMap<>();
        loadInventoryFromCSV();
    }

    private void loadInventoryFromCSV() {
        List<String[]> data = csvUtility.readCSV();
        // Skip the header row
        for (int i = 1; i < data.size(); i++) {
            String[] row = data.get(i);
            String name = row[0];
            int stockLevel = Integer.parseInt(row[1]);
            int lowStockLevel = Integer.parseInt(row[2]);
            Medication medication = new Medication(name, stockLevel, lowStockLevel);
            inventory.add(medication);
            medicationMap.put(name, medication);
        }
    }

    private void saveInventoryToCSV() {
        List<String[]> data = new ArrayList<>();
        // Add the header row
        data.add(new String[]{"Medicine Name", "Initial Stock", "Low Stock Level Alert"});
        for (Medication med : inventory) {
            data.add(new String[]{med.getName(), String.valueOf(med.getStockLevel()), String.valueOf(med.getLowStockLevel())});
        }
        csvUtility.writeCSV(data);
    }

    public void addMedication(String name, int initialStock, int lowStockLevel) {
        Medication medication = new Medication(name, initialStock, lowStockLevel);
        inventory.add(medication);
        medicationMap.put(name, medication);
        saveInventoryToCSV();
        System.out.println(name + " added to inventory.");
    }

    public void removeMedication(String name) {
        inventory.removeIf(med -> med.getName().equals(name));
        medicationMap.remove(name);
        saveInventoryToCSV();
        System.out.println(name + " removed from inventory.");
    }

    public void updateStockLevel(String name, int newStockLevel) {
        for (Medication med : inventory) {
            if (med.getName().equals(name)) {
                med.setStockLevel(newStockLevel);
                saveInventoryToCSV();
                System.out.println("Stock level of " + name + " updated to " + newStockLevel);
                return;
            }
        }
        System.out.println(name + " not found in inventory.");
    }

    public void updateLowStockLevel(String name, int newLowStockLevel) {
        for (Medication med : inventory) {
            if (med.getName().equals(name)) {
                med.setLowStockLevel(newLowStockLevel);
                saveInventoryToCSV();
                System.out.println("Low stock level alert for " + name + " updated to " + newLowStockLevel);
                return;
            }
        }
        System.out.println(name + " not found in inventory.");
    }

    public void printInventory() {
        System.out.println("Inventory:");
        System.out.println("[Medication Name, Stock Level, Low Stock Level Alert]");
        for (Medication med : inventory) {
            System.out.println("[" + med.getName() + ", " + med.getStockLevel() + ", " + med.getLowStockLevel() + "]");
        }
    }

    public void checkStockLevels() {
        boolean lowStockFound = false; // Flag to check if any low stock items are found
        for (Medication medication : medicationMap.values()) {
            if (medication.getStockLevel() < medication.getLowStockLevel()) {
                System.out.println("Low stock alert for: " + medication.getName());
                lowStockFound = true; // Set flag to true if low stock is found
            }
        }
        if (!lowStockFound) {
            System.out.println("All medicines are sufficiently stocked.");
        }
    }

    public Map<String, Medication> getLowStockItems() {
        Map<String, Medication> lowStockItems = new HashMap<>();
        for (Medication medication : medicationMap.values()) {
            if (medication.getStockLevel() <= medication.getLowStockLevel()) {
                lowStockItems.put(medication.getName(), medication);
            }
        }
        return lowStockItems;
    }

    public void submitReplenishmentRequest(String name) {
        Medication medication = medicationMap.get(name);
        if (medication != null && medication.getStockLevel() <= medication.getLowStockLevel()) {
            // Logic to handle replenishment request
            System.out.println("Replenishment request submitted for: " + name);
            // Here you could add logic to notify an administrator or log the request.
        } else if (medication == null) {
            System.out.println("Medicine not found: " + name);
        } else {
            System.out.println("No replenishment needed for: " + name);
        }
    }
}
