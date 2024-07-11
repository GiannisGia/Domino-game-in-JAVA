package gr.uop;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class JavaFXApp extends Application {

    private TextArea outputArea;
    private TextField tileInputField;
    private Player player1;
    private Player player2;
    private List<Player> players;
    private GameEngine gameEngine;
    private int currentPlayerIndex;

    @Override
    public void start(Stage stage) {
        var label = new Label("Welcome to Domino Game!");
        Button startGameButton = new Button("Start Game");
        Button exitGameButton = new Button("Exit Game");

        VBox mainSceneButtons = new VBox(10, startGameButton, exitGameButton);
        mainSceneButtons.setAlignment(Pos.CENTER);

        StackPane mainScene = new StackPane();
        mainScene.getChildren().addAll(label, mainSceneButtons);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(mainSceneButtons, Pos.CENTER);

        var scene = new Scene(mainScene, 640, 480);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Domino Game");
        stage.show();

        startGameButton.setOnAction(e -> startGameScene(stage));
        exitGameButton.setOnAction(e -> stage.close());
    }

    private void startGameScene(Stage stage) {
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        gameEngine = new GameEngine(players);
        currentPlayerIndex = 0;

        Button drawTileButton = new Button("Draw Tile");
        tileInputField = new TextField();
        Button playTileButton = new Button("Play Tile");
        Button exitGameButton = new Button("Exit Game");

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        VBox gameSceneButtons = new VBox(10, tileInputField, playTileButton, drawTileButton,outputArea,exitGameButton);
        gameSceneButtons.setAlignment(Pos.CENTER);

        StackPane gameScene = new StackPane();
        gameScene.getChildren().addAll(gameSceneButtons);
        StackPane.setAlignment(gameSceneButtons, Pos.CENTER);

        var scene = new Scene(gameScene, 640, 480);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Domino Game");

        // Start game and update output area
        startGame();
        drawTileButton.setOnAction(e -> drawTile());
        playTileButton.setOnAction(e -> playTile());
        exitGameButton.setOnAction(e -> stage.close());
    }

    private void startGame() {
        updateOutput("Game started!");
        nextTurn();
    }

    private void drawTile() {
        Player currentPlayer = players.get(currentPlayerIndex);
        gameEngine.drawTile(currentPlayer);
        updateOutput("Player: " + currentPlayer.getName() + " drew a tile");
        updateOutput("Your tiles: " + currentPlayer.getTiles());
        nextTurn(); // Draw does not change turn
    }

    private void playTile() {
        Player currentPlayer = players.get(currentPlayerIndex);
        String input = tileInputField.getText();
        if (input != null && !input.isEmpty()) {
            String[] values = input.split(":");
            if (values.length == 2) {
                try {
                    int value1 = Integer.parseInt(values[0]);
                    int value2 = Integer.parseInt(values[1]);
                    Tile tile = new Tile(value1, value2);

                    if (gameEngine.playTile(currentPlayer, tile)) {
                        updateOutput("Player: " + currentPlayer.getName() + " played tile " + tile);
                        nextTurn(); // Switch turn after valid play
                    } else {
                        showAlert("Invalid tile or no matching open end. Try again");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid input format. Format is 'value1:value2'.");
                }
            } else {
                showAlert("Invalid input format");
            }
        }
        tileInputField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private void nextTurn() {
        if (gameEngine.isGameOver()) {
            Player winnerPlayer = gameEngine.determineWinner();
            updateOutput("Game over! The winner is " + winnerPlayer.getName() + " with score " + winnerPlayer.getScore());
            for (Player player : players) {
                updateOutput(player.getName() + " with score " + player.getScore());
            }
        } else {
            Player currentPlayer = players.get(currentPlayerIndex);
            updateOutput(currentPlayer.getName() + "'s turn");
            updateOutput("Your tiles: " + currentPlayer.getTiles());
            updateOutput("Line of play: " + gameEngine.getLineOfPlay());
            updateOutput("Open ends: " + gameEngine.getOpenEnds());
        }
    }

    private void updateOutput(String message) {
        outputArea.appendText(message + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
