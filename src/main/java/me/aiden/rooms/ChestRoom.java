package me.aiden.rooms;

import me.aiden.Main;

public class ChestRoom extends Room {

    private final int coins;
    private boolean complete = false;

    public ChestRoom() {
        super(Rooms.CHEST);
        this.coins = Math.round((float) Math.random() * 5) + 5;
    }

    public void openChest() {
        if (complete) {
            return;
        }
        Main.game.player.addCoins(coins);
        Main.game.getInterface().showMessage("You found " + coins + " coins!");
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
