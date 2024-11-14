public class Medication {
    private String name;
    private int stockLevel;
    private int lowStockLevel;

    public Medication(String name, int stockLevel, int lowStockLevel) {
        this.name = name;
        this.stockLevel = stockLevel;
        this.lowStockLevel = lowStockLevel;
    }

    public String getName() {
        return name;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getLowStockLevel() {
        return lowStockLevel;
    }

    public void setLowStockLevel(int lowStockLevel) {
        this.lowStockLevel = lowStockLevel;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "name='" + name + '\'' +
                ", stockLevel=" + stockLevel +
                ", lowStockLevel=" + lowStockLevel +
                '}';
    }
}
