package me.aiden.items;

public enum Heals {

    BANANA(1, "Icon", "Banana", 10, 20),
    BANANA_BATCH(2, "Icon", "Banana Batch", 50, 30);



    private final int id;
    private final String icon;
    private final String name;
    private final int amount;
    private final int price;

    Heals(int id, String icon, String name, int amount, int price) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public int getId() { return id; }
    public String getIcon() { return icon; }
    public String getName() { return name; }
    public int getAmount() { return amount; }
    public int getPrice() { return price; }
}
