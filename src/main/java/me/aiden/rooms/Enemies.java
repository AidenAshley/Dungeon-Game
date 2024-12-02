package me.aiden.rooms;

public enum Enemies {

    GRUNT(1, 10, 8, "Grunt"),

    MINER(2, 15, 5, "Miner"),

    ZOMBIE(3, 20, 4, "Zombie"),

    BOSS(3, 50, 10, "Boss");

    private final int id;
    private final int health;
    private final int damage;

    private final String name;

    Enemies(int id, int health, int damage, String name) {
        this.id = id;
        this.health = health;
        this.damage = damage;
        this.name = name;
    }

    public int getId() { return id; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }

    public String getName() { return name; }

    public static Enemies getEnemy(int id) {
        for (Enemies enemy : Enemies.values()) {
            if (enemy.id == id) {
                return enemy;
            }
        }
        return null;
    }
}