package gr.uop;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class JavaFXApp extends Application {

    private TextArea outputArea;
    private Player player;

    @Override
    public void start(Stage stage) {
        player = new Player("Alice"); // Create a new player for the demonstration

        var label = new Label("Welcome to Domino Game!");
        Button startGameButton = new Button("Start Game");
        Button exitGameButton = new Button("Exit Game");
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        VBox mainSceneButtons = new VBox(10, startGameButton, exitGameButton, outputArea);
        mainSceneButtons.setAlignment(Pos.CENTER);

        StackPane mainScene = new StackPane();
        mainScene.getChildren().addAll(label, mainSceneButtons);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(mainSceneButtons, Pos.CENTER);

        var scene = new Scene(mainScene, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Domino Game");
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.show();

        startGameButton.setOnAction(e -> startGame());
        exitGameButton.setOnAction(e -> stage.close());
    }

    private void startGame() {
        // Call the game logic and update the output area
        String output = generateGameOutput();
        outputArea.setText(output);

        // You can still call another Java program if needed
        runExternalGameProgram();
    }

    private String generateGameOutput() {
        // Simulate the game state for demonstration purposes
        List<String> playerTiles = Arrays.asList("[1:2]", "[0:1]", "[3:4]", "[3:6]", "[2:6]", "[0:4]", "[1:5]");
        List<String> lineOfPlay = Arrays.asList();
        List<String> openEnds = Arrays.asList();

        StringBuilder output = new StringBuilder();
        output.append(player.getName()).append("'s turn\n");
        output.append("List of player tiles: ").append(playerTiles).append("\n");
        output.append("Line of play: ").append(lineOfPlay).append("\n");
        output.append("Open ends: ").append(openEnds).append("\n");
        return output.toString();
    }

    private void runExternalGameProgram() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "-cp", "target/classes",
                "gr.uop.App"
            );

            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
