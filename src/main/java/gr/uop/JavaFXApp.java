package gr.uop;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class JavaFXApp extends Application {

    @Override
    public void start(Stage stage) {
        var label = new Label("Welcome to Domino Game!");
        Button startGameButton = new Button("Start Game");
        Button exitGameButton =new Button("Exit Game");

        VBox mainSceneButtons = new VBox(10, startGameButton, exitGameButton);
        mainSceneButtons.setAlignment(Pos.CENTER);

        StackPane mainScene = new StackPane();
        mainScene.getChildren().addAll(label,mainSceneButtons);
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setAlignment(mainSceneButtons, Pos.CENTER);

        var scene = new Scene(mainScene, 640, 480);
        stage.setScene(scene);
        stage.setTitle("Domino Game");
        stage.show();

        exitGameButton.setOnAction((e->{
            stage.close();
        }));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
