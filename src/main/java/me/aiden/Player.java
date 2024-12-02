package me.aiden;

import me.aiden.items.Heals;
import me.aiden.items.HealthItem;
import me.aiden.items.Item;

import java.util.ArrayList;

public class Player {

    private int maxHealth = 100;
    private int health = 100;

    private int coins = 0;
    private int damage = 5;

    private ArrayList<Item> inventory = new ArrayList<>();

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public void heal(int amount) {
        if (this.health + amount > this.maxHealth) {
            this.health = this.maxHealth;
        } else {
            this.health += amount;
        }
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public void removeCoins(int amount) {
        this.coins -= amount;
    }

    public void upgradeDamage(int amount) {
        this.damage += amount;
    }

    public int getHealth() {
        return this.health;
    }

    public int getCoins() {
        return this.coins;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void addItemToInventory(Item item) {
        this.inventory.add(item);
    }

    public void removeItemFromInventory(Heals item) {
        for (Item i : this.inventory) {
            if (((HealthItem) i).item == item) {
                this.inventory.remove(i);
                break;
            }
        }
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public int getInventoryItemCount(Heals item) {
        int count = 0;
        for (Item i : this.inventory) {
            if (((HealthItem) i).item == item) {
                count++;
            }
        }
        return count;
    }

    public void reset() {
        this.maxHealth = 100;
        this.health = 100;
        this.coins = 0;
        this.damage = 5;
    }
}
