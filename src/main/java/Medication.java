public class Medication {
    private final String name;
    private int quantity;
    private final int price;

    public Medication(String name, int quantity, int price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
