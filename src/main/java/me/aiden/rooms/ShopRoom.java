package me.aiden.rooms;

import me.aiden.items.*;

import java.util.ArrayList;

public class ShopRoom extends Room {

    private final ArrayList<Item> items = new ArrayList<>();

    private boolean complete = false;

    public ShopRoom() {
        super(Rooms.SHOP);
        items.add(new HealthItem(Heals.BANANA));
        items.add(new HealthItem(Heals.BANANA_BATCH));

        items.add(new UpgradeItem(Upgrades.DAMAGE));
        items.add(new UpgradeItem(Upgrades.HEALTH));
    }

    public void buyItem(Item item) {
        if (complete) {
            System.out.println("You have already completed this room.");
            return;
        }

        if (item instanceof HealthItem) {
            ((HealthItem) item).buy();

        } else if (item instanceof UpgradeItem) {
            ((UpgradeItem) item).buy();

        }

        complete = true;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void skipRoom() {
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
