package me.aiden.rooms;

public enum Rooms {

    CHEST(1),
    ENEMY(2),
    SHOP(3);

    private final int id;

    Rooms(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Rooms getRoom(int id) {
        for (Rooms room : Rooms.values()) {
            if (room.id == id) {
                return room;
            }
        }
        return null;
    }
}
