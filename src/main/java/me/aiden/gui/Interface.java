package me.aiden.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import me.aiden.Main;
import me.aiden.items.*;
import me.aiden.rooms.*;

public class Interface {

    public Stage primaryStage;

    public Interface(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void renderInterface(Room room) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: black; -fx-padding: 25px;");

        if (room instanceof ChestRoom)
        {
            ChestRoom chestRoom = (ChestRoom) room;

            // Picture container
            ImageView imageView = new ImageView(new Image("file:./resources/room-chest.png"));
            imageView.setFitWidth(256);
            imageView.setFitHeight(256);
            imageView.setPreserveRatio(true);
            VBox pictureContainer = new VBox(imageView);
            pictureContainer.setAlignment(Pos.CENTER);
            pictureContainer.setPrefSize(258, 258);
            pictureContainer.setMinSize(258, 258);
            pictureContainer.setMaxSize(258, 258);
            pictureContainer.setStyle("-fx-border-color: #4af626;");

            // Row container for buttons
            HBox rowContainer = new HBox(5);
            rowContainer.setAlignment(Pos.CENTER);
            rowContainer.setStyle("-fx-padding: 10px;");

            Button button;

            if (chestRoom.isComplete()) {
                button = new Button("Next Room");
                button.setOnAction(e -> {
                    Main.game.gotoRoom();
                });
            }else {
                button = new Button("Open Chest");
                button.setOnAction(e -> {
                    chestRoom.openChest();
                    renderInterface(chestRoom);
                });
            }
            styleButton(button);

            rowContainer.getChildren().addAll(button);

            //Divider
            Rectangle divider = new Rectangle(100, 1, Color.web("#4af626"));
            VBox layout = new VBox(20);
            layout.setAlignment(Pos.CENTER);
            layout.getChildren().add(divider);


            // Column container for player stats
            VBox playerStats = new VBox(5);
            playerStats.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
            playerStats.setPrefWidth(256);

            Label statsTitle = new Label("Player Stats:");
            statsTitle.setTextFill(Color.WHITE);
            statsTitle.setFont(Font.font("Helvetica", 16));
            Label playerMaxHealth = new Label("- Max Health: " + Main.game.player.getMaxHealth());
            playerMaxHealth.setTextFill(Color.WHITE);

            Label playerHealth = new Label("- Health: " + Main.game.player.getHealth());
            playerHealth.setTextFill(Color.WHITE);

            Label playerDamage = new Label("- Damage: " + Main.game.player.getDamage());
            playerDamage.setTextFill(Color.WHITE);

            Label playerCoins = new Label("- Coins: " + Main.game.player.getCoins());
            playerCoins.setTextFill(Color.WHITE);

            playerStats.getChildren().addAll(statsTitle, playerMaxHealth, playerHealth, playerDamage, playerCoins);

            // Setting up the layout
            VBox mainContainer = new VBox(25, pictureContainer, rowContainer, layout, playerStats);
            mainContainer.setAlignment(Pos.CENTER);
            root.setCenter(mainContainer);

        }else if (room instanceof EnemyRoom)
        {
            EnemyRoom enemyRoom = (EnemyRoom) room;

            ImageView imageView;
            VBox pictureContainer;
            VBox columnContainer;
            Label enemyTitle;
            Label statsTitle;
            Label healthLabel;
            Label attackLabel;
            Label playerMaxHealth;
            Label playerHealth;
            Label playerDamage;
            Label playerCoins;
            HBox rowContainer;
            Button button1;
            Button button2;
            Button button3;
            VBox mainContainer;
            Rectangle divider;
            VBox layout;
            VBox playerStats;
            switch (enemyRoom.getEnemy()) {
                case MINER:
                    // Picture container
                    imageView = new ImageView(new Image("file:./resources/enemy-miner.png"));
                    imageView.setFitWidth(256);
                    imageView.setFitHeight(256);
                    imageView.setPreserveRatio(true);
                    pictureContainer = new VBox(imageView);
                    pictureContainer.setAlignment(Pos.CENTER);
                    pictureContainer.setPrefSize(258, 258);
                    pictureContainer.setMinSize(258, 258);
                    pictureContainer.setMaxSize(258, 258);
                    pictureContainer.setStyle("-fx-border-color: #4af626;");

                    // Column container for stats
                    columnContainer = new VBox(5);
                    columnContainer.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
                    columnContainer.setPrefWidth(256);

                    enemyTitle = new Label("Enemy: " + enemyRoom.getEnemy().getName());
                    enemyTitle.setTextFill(Color.WHITE);
                    enemyTitle.setFont(Font.font("Helvetica", 16));

                    statsTitle = new Label("Stats:");
                    statsTitle.setTextFill(Color.WHITE);
                    statsTitle.setFont(Font.font("Helvetica", 16));

                    healthLabel = new Label("- Health: " + enemyRoom.getHealth());
                    healthLabel.setTextFill(Color.WHITE);

                    attackLabel = new Label("- Damage: " + enemyRoom.getEnemy().getDamage());
                    attackLabel.setTextFill(Color.WHITE);

                    columnContainer.getChildren().addAll(enemyTitle, statsTitle, healthLabel, attackLabel);

                    // Row container for buttons
                    rowContainer = new HBox(5);
                    rowContainer.setAlignment(Pos.CENTER);
                    rowContainer.setStyle("-fx-padding: 10px;");

                    if (!enemyRoom.isComplete()) {
                        button1 = new Button("Attack!");
                        styleButton(button1);
                        button1.setOnAction(e -> {
                            int enemyHealth = enemyRoom.attackEnemy(Main.game.player.getDamage());

                            if (enemyHealth == 0) {
                                Main.game.player.addCoins(enemyRoom.getCoins());
                                showMessage("You defeated the Miner and gained " + enemyRoom.getCoins() + " coins!");
                            }

                            enemyRoom.attackPlayer();

                            if (Main.game.player.getHealth() == 0) {
                                showMessage("You died to the Miner!");
                                Main.game.reset();
                                return;
                            }
                            renderInterface(enemyRoom);
                        });

                        button2 = new Button("Use Banana (x" + Main.game.player.getInventoryItemCount(Heals.BANANA) + ")");
                        styleButton(button2);
                        button2.setOnAction(e -> {
                            if (Main.game.player.getInventoryItemCount(Heals.BANANA) == 0) {
                                showMessage("You don't have any Bananas!");
                                return;
                            }
                            Main.game.player.heal(Heals.BANANA.getAmount());
                            Main.game.player.removeItemFromInventory(Heals.BANANA);
                            showMessage("You used a Banana to heal " + Heals.BANANA.getAmount() + " health!");
                            renderInterface(enemyRoom);
                        });

                        button3 = new Button("Use Banana Batch (x" + Main.game.player.getInventoryItemCount(Heals.BANANA_BATCH) + ")");
                        styleButton(button3);
                        button3.setOnAction(e -> {
                            if (Main.game.player.getInventoryItemCount(Heals.BANANA_BATCH) == 0) {
                                showMessage("You don't have any Banana Batches!");
                                return;
                            }
                            Main.game.player.heal(Heals.BANANA_BATCH.getAmount());
                            Main.game.player.removeItemFromInventory(Heals.BANANA_BATCH);
                            showMessage("You used a Banana Batch to heal " + Heals.BANANA_BATCH.getAmount() + " health!");
                            renderInterface(enemyRoom);
                        });

                        rowContainer.getChildren().addAll(button1, button2, button3);
                    }else {
                        button1 = new Button("Next Room");
                        button1.setOnAction(e -> {
                            Main.game.gotoRoom();
                        });
                        styleButton(button1);
                        rowContainer.getChildren().add(button1);
                    }

                    //Divider
                    divider = new Rectangle(100, 1, Color.web("#4af626"));
                    layout = new VBox(20);
                    layout.setAlignment(Pos.CENTER);
                    layout.getChildren().add(divider);

                    // Column container for player stats
                    playerStats = new VBox(5);
                    playerStats.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
                    playerStats.setPrefWidth(256);

                    statsTitle = new Label("Player Stats:");
                    statsTitle.setTextFill(Color.WHITE);
                    statsTitle.setFont(Font.font("Helvetica", 16));

                    playerMaxHealth = new Label("- Max Health: " + Main.game.player.getMaxHealth());
                    playerMaxHealth.setTextFill(Color.WHITE);

                    playerHealth = new Label("- Health: " + Main.game.player.getHealth());
                    playerHealth.setTextFill(Color.WHITE);

                    playerDamage = new Label("- Damage: " + Main.game.player.getDamage());
                    playerDamage.setTextFill(Color.WHITE);

                    playerCoins = new Label("- Coins: " + Main.game.player.getCoins());
                    playerCoins.setTextFill(Color.WHITE);

                    playerStats.getChildren().addAll(statsTitle, playerMaxHealth, playerHealth, playerDamage, playerCoins);

                    // Setting up the layout
                    mainContainer = new VBox(25, pictureContainer, columnContainer, rowContainer, layout, playerStats);
                    mainContainer.setAlignment(Pos.CENTER);
                    root.setCenter(mainContainer);
                    break;

                case GRUNT:
                    // Picture container
                    imageView = new ImageView(new Image("file:./resources/enemy-grunt.png"));
                    imageView.setFitWidth(256);
                    imageView.setFitHeight(256);
                    imageView.setPreserveRatio(true);
                    pictureContainer = new VBox(imageView);
                    pictureContainer.setAlignment(Pos.CENTER);
                    pictureContainer.setPrefSize(258, 258);
                    pictureContainer.setMinSize(258, 258);
                    pictureContainer.setMaxSize(258, 258);
                    pictureContainer.setStyle("-fx-border-color: #4af626;");

                    // Column container for stats
                    columnContainer = new VBox(5);
                    columnContainer.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
                    columnContainer.setPrefWidth(256);

                    enemyTitle = new Label("Enemy: " + enemyRoom.getEnemy().getName());
                    enemyTitle.setTextFill(Color.WHITE);
                    enemyTitle.setFont(Font.font("Helvetica", 16));

                    statsTitle = new Label("Stats:");
                    statsTitle.setTextFill(Color.WHITE);
                    statsTitle.setFont(Font.font("Helvetica", 16));

                    healthLabel = new Label("- Health: " + enemyRoom.getHealth());
                    healthLabel.setTextFill(Color.WHITE);

                    attackLabel = new Label("- Damage: " + enemyRoom.getEnemy().getDamage());
                    attackLabel.setTextFill(Color.WHITE);

                    columnContainer.getChildren().addAll(enemyTitle, statsTitle, healthLabel, attackLabel);

                    // Row container for buttons
                    rowContainer = new HBox(5);
                    rowContainer.setAlignment(Pos.CENTER);
                    rowContainer.setStyle("-fx-padding: 10px;");

                    if (!enemyRoom.isComplete()) {
                        button1 = new Button("Attack!");
                        styleButton(button1);
                        button1.setOnAction(e -> {
                            int enemyHealth = enemyRoom.attackEnemy(Main.game.player.getDamage());

                            if (enemyHealth == 0) {
                                Main.game.player.addCoins(enemyRoom.getCoins());
                                showMessage("You defeated the Grunt and gained " + enemyRoom.getCoins() + " coins!");
                            }

                            enemyRoom.attackPlayer();

                            if (Main.game.player.getHealth() == 0) {
                                showMessage("You died to the Grunt!");
                                Main.game.reset();
                                return;
                            }
                            renderInterface(enemyRoom);
                        });

                        button2 = new Button("Use Banana (x" + Main.game.player.getInventoryItemCount(Heals.BANANA) + ")");
                        styleButton(button2);
                        button2.setOnAction(e -> {
                            if (Main.game.player.getInventoryItemCount(Heals.BANANA) == 0) {
                                showMessage("You don't have any Bananas!");
                                return;
                            }
                            Main.game.player.heal(Heals.BANANA.getAmount());
                            Main.game.player.removeItemFromInventory(Heals.BANANA);
                            showMessage("You used a Banana to heal " + Heals.BANANA.getAmount() + " health!");
                            renderInterface(enemyRoom);
                        });

                        button3 = new Button("Use Banana Batch (x" + Main.game.player.getInventoryItemCount(Heals.BANANA_BATCH) + ")");
                        styleButton(button3);
                        button3.setOnAction(e -> {
                            if (Main.game.player.getInventoryItemCount(Heals.BANANA_BATCH) == 0) {
                                showMessage("You don't have any Banana Batches!");
                                return;
                            }
                            Main.game.player.heal(Heals.BANANA_BATCH.getAmount());
                            Main.game.player.removeItemFromInventory(Heals.BANANA_BATCH);
                            showMessage("You used a Banana Batch to heal " + Heals.BANANA_BATCH.getAmount() + " health!");
                            renderInterface(enemyRoom);
                        });

                        rowContainer.getChildren().addAll(button1, button2, button3);
                    }else {
                        button1 = new Button("Next Room");
                        button1.setOnAction(e -> {
                            Main.game.gotoRoom();
                        });
                        styleButton(button1);
                        rowContainer.getChildren().add(button1);
                    }

                    //Divider
                    divider = new Rectangle(100, 1, Color.web("#4af626"));
                    layout = new VBox(20);
                    layout.setAlignment(Pos.CENTER);
                    layout.getChildren().add(divider);

                    // Column container for player stats
                    playerStats = new VBox(5);
                    playerStats.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
                    playerStats.setPrefWidth(256);

                    statsTitle = new Label("Player Stats:");
                    statsTitle.setTextFill(Color.WHITE);
                    statsTitle.setFont(Font.font("Helvetica", 16));

                    playerMaxHealth = new Label("- Max Health: " + Main.game.player.getMaxHealth());
                    playerMaxHealth.setTextFill(Color.WHITE);

                    playerHealth = new Label("- Health: " + Main.game.player.getHealth());
                    playerHealth.setTextFill(Color.WHITE);

                    playerDamage = new Label("- Damage: " + Main.game.player.getDamage());
                    playerDamage.setTextFill(Color.WHITE);

                    playerCoins = new Label("- Coins: " + Main.game.player.getCoins());
                    playerCoins.setTextFill(Color.WHITE);

                    playerStats.getChildren().addAll(statsTitle, playerMaxHealth, playerHealth, playerDamage, playerCoins);

                    // Setting up the layout
                    mainContainer = new VBox(25, pictureContainer, columnContainer, rowContainer, layout, playerStats);
                    mainContainer.setAlignment(Pos.CENTER);
                    root.setCenter(mainContainer);
                    break;

                case ZOMBIE:
                    // Picture container
                    imageView = new ImageView(new Image("file:./resources/enemy-zombie.png"));
                    imageView.setFitWidth(256);
                    imageView.setFitHeight(256);
                    imageView.setPreserveRatio(true);
                    pictureContainer = new VBox(imageView);
                    pictureContainer.setAlignment(Pos.CENTER);
                    pictureContainer.setPrefSize(258, 258);
                    pictureContainer.setMinSize(258, 258);
                    pictureContainer.setMaxSize(258, 258);
                    pictureContainer.setStyle("-fx-border-color: #4af626;");

                    // Column container for stats
                    columnContainer = new VBox(5);
                    columnContainer.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
                    columnContainer.setPrefWidth(256);

                    enemyTitle = new Label("Enemy: " + enemyRoom.getEnemy().getName());
                    enemyTitle.setTextFill(Color.WHITE);
                    enemyTitle.setFont(Font.font("Helvetica", 16));

                    statsTitle = new Label("Stats:");
                    statsTitle.setTextFill(Color.WHITE);
                    statsTitle.setFont(Font.font("Helvetica", 16));

                    healthLabel = new Label("- Health: " + enemyRoom.getHealth());
                    healthLabel.setTextFill(Color.WHITE);

                    attackLabel = new Label("- Damage: " + enemyRoom.getEnemy().getDamage());
                    attackLabel.setTextFill(Color.WHITE);

                    columnContainer.getChildren().addAll(enemyTitle, statsTitle, healthLabel, attackLabel);

                    // Row container for buttons
                    rowContainer = new HBox(5);
                    rowContainer.setAlignment(Pos.CENTER);
                    rowContainer.setStyle("-fx-padding: 10px;");

                    if (!enemyRoom.isComplete()) {
                        button1 = new Button("Attack!");
                        styleButton(button1);
                        button1.setOnAction(e -> {
                            int enemyHealth = enemyRoom.attackEnemy(Main.game.player.getDamage());

                            if (enemyHealth == 0) {
                                Main.game.player.addCoins(enemyRoom.getCoins());
                                showMessage("You defeated the Zombie and gained " + enemyRoom.getCoins() + " coins!");
                            }

                            enemyRoom.attackPlayer();

                            if (Main.game.player.getHealth() == 0) {
                                showMessage("You died to the Zombie!");
                                Main.game.reset();
                                return;
                            }
                            renderInterface(enemyRoom);
                        });

                        button2 = new Button("Use Banana (x" + Main.game.player.getInventoryItemCount(Heals.BANANA) + ")");
                        styleButton(button2);
                        button2.setOnAction(e -> {
                            if (Main.game.player.getInventoryItemCount(Heals.BANANA) == 0) {
                                showMessage("You don't have any Bananas!");
                                return;
                            }
                            Main.game.player.heal(Heals.BANANA.getAmount());
                            Main.game.player.removeItemFromInventory(Heals.BANANA);
                            showMessage("You used a Banana to heal " + Heals.BANANA.getAmount() + " health!");
                            renderInterface(enemyRoom);
                        });

                        button3 = new Button("Use Banana Batch (x" + Main.game.player.getInventoryItemCount(Heals.BANANA_BATCH) + ")");
                        styleButton(button3);
                        button3.setOnAction(e -> {
                            if (Main.game.player.getInventoryItemCount(Heals.BANANA_BATCH) == 0) {
                                showMessage("You don't have any Banana Batches!");
                                return;
                            }
                            Main.game.player.heal(Heals.BANANA_BATCH.getAmount());
                            Main.game.player.removeItemFromInventory(Heals.BANANA_BATCH);
                            showMessage("You used a Banana Batch to heal " + Heals.BANANA_BATCH.getAmount() + " health!");
                            renderInterface(enemyRoom);
                        });

                        rowContainer.getChildren().addAll(button1, button2, button3);
                    }else {
                        button1 = new Button("Next Room");
                        button1.setOnAction(e -> {
                            Main.game.gotoRoom();
                        });
                        styleButton(button1);
                        rowContainer.getChildren().add(button1);
                    }

                    //Divider
                    divider = new Rectangle(100, 1, Color.web("#4af626"));
                    layout = new VBox(20);
                    layout.setAlignment(Pos.CENTER);
                    layout.getChildren().add(divider);

                    // Column container for player stats
                    playerStats = new VBox(5);
                    playerStats.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
                    playerStats.setPrefWidth(256);

                    statsTitle = new Label("Player Stats:");
                    statsTitle.setTextFill(Color.WHITE);
                    statsTitle.setFont(Font.font("Helvetica", 16));

                    playerMaxHealth = new Label("- Max Health: " + Main.game.player.getMaxHealth());
                    playerMaxHealth.setTextFill(Color.WHITE);

                    playerHealth = new Label("- Health: " + Main.game.player.getHealth());
                    playerHealth.setTextFill(Color.WHITE);

                    playerDamage = new Label("- Damage: " + Main.game.player.getDamage());
                    playerDamage.setTextFill(Color.WHITE);

                    playerCoins = new Label("- Coins: " + Main.game.player.getCoins());
                    playerCoins.setTextFill(Color.WHITE);

                    playerStats.getChildren().addAll(statsTitle, playerMaxHealth, playerHealth, playerDamage, playerCoins);

                    // Setting up the layout
                    mainContainer = new VBox(25, pictureContainer, columnContainer, rowContainer, layout, playerStats);
                    mainContainer.setAlignment(Pos.CENTER);
                    root.setCenter(mainContainer);
                    break;
            }

        }else if (room instanceof ShopRoom)
        {
            ShopRoom shopRoom = (ShopRoom) room;

            // Picture container
            ImageView imageView = new ImageView(new Image("file:./resources/room-shop.png"));
            imageView.setFitWidth(256);
            imageView.setFitHeight(256);
            imageView.setPreserveRatio(true);
            VBox pictureContainer = new VBox(imageView);
            pictureContainer.setAlignment(Pos.CENTER);
            pictureContainer.setPrefSize(258, 258);
            pictureContainer.setMinSize(258, 258);
            pictureContainer.setMaxSize(258, 258);
            pictureContainer.setStyle("-fx-border-color: #4af626;");

            VBox rowContainer = new VBox(5);
            rowContainer.setAlignment(Pos.CENTER);
            rowContainer.setStyle("-fx-padding: 10px;");

            if (!shopRoom.isComplete()) {
                // Row container for buttons

                HBox rowContainer1 = new HBox(5);
                rowContainer1.setAlignment(Pos.CENTER);
                rowContainer1.setStyle("-fx-padding: 10px;");

                Button button1 = new Button("BANANA ($" + Heals.BANANA.getPrice() + ") Heal " + Heals.BANANA.getAmount() + " Health");
                styleButton(button1);
                button1.setOnAction(e -> {
                    if (Main.game.player.getCoins() < Heals.BANANA.getPrice()) {
                        showMessage("You do not have enough coins to purchase this item.");
                        return;
                    }
                    for (Item item : shopRoom.getItems()) {
                        if (item instanceof HealthItem) {
                            if (((HealthItem) item).item == Heals.BANANA) {
                                shopRoom.buyItem(item);
                                break;
                            }
                        }
                    }
                    renderInterface(shopRoom);
                });

                Button button2 = new Button("BANANA BATCH ($" + Heals.BANANA_BATCH.getPrice() + ") Heal " + Heals.BANANA_BATCH.getAmount() + " Health");
                styleButton(button2);
                button2.setOnAction(e -> {
                    if (Main.game.player.getCoins() < Heals.BANANA_BATCH.getPrice()) {
                        showMessage("You do not have enough coins to purchase this item.");
                        return;
                    }
                    for (Item item : shopRoom.getItems()) {
                        if (item instanceof HealthItem) {
                            if (((HealthItem) item).item == Heals.BANANA_BATCH) {
                                shopRoom.buyItem(item);
                                break;
                            }
                        }
                    }
                    renderInterface(shopRoom);
                });

                HBox rowContainer2 = new HBox(5);
                rowContainer2.setAlignment(Pos.CENTER);
                rowContainer2.setStyle("-fx-padding: 10px;");

                Button button3 = new Button("DAMAGE UPGRADE ($" + Upgrades.DAMAGE.getPrice() + ") +" + Upgrades.DAMAGE.getAmount() + " Damage");
                styleButton(button3);

                button3.setOnAction(e -> {
                    if (Main.game.player.getCoins() < Upgrades.DAMAGE.getPrice()) {
                        showMessage("You do not have enough coins to purchase this item.");
                        return;
                    }
                    for (Item item : shopRoom.getItems()) {
                        if (item instanceof UpgradeItem) {
                            if (((UpgradeItem) item).item == Upgrades.DAMAGE) {
                                shopRoom.buyItem(item);
                                break;
                            }
                        }
                    }
                    renderInterface(shopRoom);
                });

                Button button4 = new Button("HEALTH UPGRADE ($" + Upgrades.HEALTH.getPrice() + ") +" + Upgrades.HEALTH.getAmount() + " Health");
                styleButton(button4);
                button4.setOnAction(e -> {
                    if (Main.game.player.getCoins() < Upgrades.HEALTH.getPrice()) {
                        showMessage("You do not have enough coins to purchase this item.");
                        return;
                    }
                    for (Item item : shopRoom.getItems()) {
                        if (item instanceof UpgradeItem) {
                            if (((UpgradeItem) item).item == Upgrades.HEALTH) {
                                shopRoom.buyItem(item);
                                break;
                            }
                        }
                    }
                    renderInterface(shopRoom);
                });

                HBox rowContainer3 = new HBox(5);
                rowContainer3.setAlignment(Pos.CENTER);
                rowContainer3.setStyle("-fx-padding: 10px;");

                Button button5 = new Button("Skip");
                styleButton(button5);

                button5.setOnAction(e -> {
                    shopRoom.skipRoom();
                    renderInterface(shopRoom);
                });

                rowContainer1.getChildren().addAll(button1, button2);
                rowContainer2.getChildren().addAll(button3, button4);
                rowContainer3.getChildren().add(button5);

                rowContainer.getChildren().addAll(rowContainer1, rowContainer2, rowContainer3);
            }else {
                Button button = new Button("Next Room");
                styleButton(button);
                button.setOnAction(e -> {
                    Main.game.gotoRoom();
                });
                rowContainer.getChildren().add(button);
            }

            //Divider
            Rectangle divider = new Rectangle(100, 1, Color.web("#4af626"));
            VBox layout = new VBox(20);
            layout.setAlignment(Pos.CENTER);
            layout.getChildren().add(divider);


            // Column container for player stats
            VBox playerStats = new VBox(5);
            playerStats.setStyle("-fx-border-color: #4af626; -fx-background-color: #081c03; -fx-padding: 10px;");
            playerStats.setPrefWidth(256);

            Label statsTitle = new Label("Player Stats:");
            statsTitle.setTextFill(Color.WHITE);
            statsTitle.setFont(Font.font("Helvetica", 16));

            Label playerMaxHealth = new Label("- Max Health: " + Main.game.player.getMaxHealth());
            playerMaxHealth.setTextFill(Color.WHITE);

            Label playerHealth = new Label("- Health: " + Main.game.player.getHealth());
            playerHealth.setTextFill(Color.WHITE);

            Label playerDamage = new Label("- Damage: " + Main.game.player.getDamage());
            playerDamage.setTextFill(Color.WHITE);

            Label playerCoins = new Label("- Coins: " + Main.game.player.getCoins());
            playerCoins.setTextFill(Color.WHITE);

            playerStats.getChildren().addAll(statsTitle, playerMaxHealth, playerHealth, playerDamage, playerCoins);

            // Setting up the layout
            VBox mainContainer = new VBox(25, pictureContainer, rowContainer, layout, playerStats);
            mainContainer.setAlignment(Pos.CENTER);
            root.setCenter(mainContainer);
        }

        // Creating the scene
        Scene scene = new Scene(root, 750, 800);
        primaryStage.setTitle("Dungeons");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void styleButton(Button button) {
        button.setStyle("-fx-background-color: #081c03; -fx-border-color: #4af626; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px;");
        button.setPrefHeight(40);
    }

    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
