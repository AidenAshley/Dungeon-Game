package me.aiden.items;

import me.aiden.Main;
import me.aiden.Player;

public class UpgradeItem extends Item {

    public final Upgrades item;

    /**
     * Initialize an UpgradeItem
     *
     * @param item - The type of upgrade
     */
    public UpgradeItem(Upgrades item) { this.item = item; }

    public void buy() {
        Player player = Main.game.player;
        switch (item) {
            case DAMAGE:
                if (player.getCoins() < item.getPrice()) {
                    System.out.println("You do not have enough coins to purchase this item.");
                    return;
                }
                player.removeCoins(item.getPrice());
                player.upgradeDamage(item.getAmount());
                break;
            case HEALTH:
                if (player.getCoins() < item.getPrice()) {
                    System.out.println("You do not have enough coins to purchase this item.");
                    return;
                }
                player.removeCoins(item.getPrice());
                player.setMaxHealth(player.getMaxHealth() + item.getAmount());
                break;
        }
    }
}
