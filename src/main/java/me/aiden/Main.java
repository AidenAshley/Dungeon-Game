package me.aiden;

import javafx.application.Application;
import javafx.stage.Stage;
import me.aiden.rooms.Room;

public class Main extends Application {

    public static Game game;

    @Override
    public void start(Stage primaryStage) {
        game = new Game();
        game.setInterface(primaryStage);
    }
}