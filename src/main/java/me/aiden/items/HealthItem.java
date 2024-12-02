package me.aiden.items;

import me.aiden.Main;
import me.aiden.Player;

public class HealthItem extends Item {

    public final Heals item;

    public HealthItem(Heals item) {
        this.item = item;
    }

    public void buy() {
        Player player = Main.game.player;
        if (player.getCoins() < item.getPrice()) {
            System.out.println("You do not have enough coins to purchase this item.");
            return;
        }
        player.removeCoins(item.getPrice());
        player.addItemToInventory(this);
    }
}
