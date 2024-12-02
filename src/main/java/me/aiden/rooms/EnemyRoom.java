package me.aiden.rooms;

import me.aiden.Main;
import me.aiden.Player;

public class EnemyRoom extends Room {

    private final Enemies enemy;
    private int health;

    private int coins;

    public EnemyRoom(Enemies enemy) {
        super(Rooms.ENEMY);
        this.enemy = enemy;
        this.health = enemy.getHealth();
        this.coins = Math.round((float) Math.random() * 2) + 2;
    }

    public Enemies getEnemy() {
        return enemy;
    }

    public int getHealth() {
        return health;
    }

    public int attackEnemy(int damage) {
        if (health - damage <= 0) {
            health = 0;
        } else {
            health -= damage;
        }

        return health;
    }

    public int attackPlayer() {
        Player player = Main.game.player;

        if (player.getHealth() - enemy.getDamage() <= 0) {
            player.setHealth(0);
        } else {
            player.setHealth(player.getHealth() - enemy.getDamage());
        }

        return player.getHealth();
    }

    public int getCoins() {
        return coins;
    }

    public boolean isComplete() {
        return health == 0;
    }
}
