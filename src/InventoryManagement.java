import java.util.ArrayList;
import java.util.List;

public class InventoryManagement {
    private List<Medication> inventory;
    private CSVUtility csvUtility;

    public InventoryManagement(String csvFilePath) {
        this.csvUtility = new CSVUtility(csvFilePath);
        this.inventory = new ArrayList<>();
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
            inventory.add(new Medication(name, stockLevel, lowStockLevel));
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
        inventory.add(new Medication(name, initialStock, lowStockLevel));
        saveInventoryToCSV();
        System.out.println(name + " added to inventory.");
    }

    public void removeMedication(String name) {
        inventory.removeIf(med -> med.getName().equals(name));
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

}
