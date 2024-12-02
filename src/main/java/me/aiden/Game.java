package me.aiden;

import javafx.stage.Stage;
import me.aiden.gui.Interface;
import me.aiden.rooms.*;

public class Game {

    public Player player;

    public Room currentRoom;

    public Interface gui;

    public Game() {
        this.player = new Player();
    }

    public void setInterface(Stage primaryStage) {
        this.gui = new Interface(primaryStage);
        this.gotoRoom();
    }

    public Interface getInterface() {
        return this.gui;
    }

    public void gotoRoom() {
        if (currentRoom != null) {
            Boolean completed = false;
            switch (this.currentRoom.roomType) {
                case SHOP:
                    completed = ((ShopRoom) this.currentRoom).isComplete();
                    break;

                case ENEMY:
                    completed = ((EnemyRoom) this.currentRoom).isComplete();
                    break;

                case CHEST:
                    completed = ((ChestRoom) this.currentRoom).isComplete();
                    break;
            }

            if (completed) {
                int roomType = Math.round((float) Math.random() * 9) + 1;

                switch (roomType) {
                    case 9:
                    case 10:
                        this.currentRoom = new ShopRoom();
                        break;

                    case 8:
                    case 7:
                    case 6:
                    case 5:
                        Enemies enemy = Enemies.getEnemy(Math.round((float) Math.random() * 2) + 1);
                        this.currentRoom = new EnemyRoom(enemy);
                        break;

                    case 4:
                    case 3:
                    case 2:
                    case 1:
                        this.currentRoom = new ChestRoom();
                        break;
                }

                this.gui.renderInterface(this.currentRoom);
            }
        }else {
            int roomType = Math.round((float) Math.random() * 9) + 1;

            switch (roomType) {
                case 10:
                case 9:
                case 8:
                case 7:
                case 6:
                    Enemies enemy = Enemies.getEnemy(Math.round((float) Math.random() * 2) + 1);
                    this.currentRoom = new EnemyRoom(enemy);
                    break;

                case 5:
                case 4:
                case 3:
                case 2:
                case 1:
                    this.currentRoom = new ChestRoom();
                    break;
            }

            this.gui.renderInterface(this.currentRoom);
        }
    }

    public void reset() {
        this.player.reset();
        this.currentRoom = null;
        this.gotoRoom();
    }
}
