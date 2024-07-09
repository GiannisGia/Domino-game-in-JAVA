package gr.uop;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App1 extends Application {

    @Override
    public void start(Stage stage) {
        Player p=new Player("Giannis");

        var label = new Label("Welcome to Domino Game!");
        label.setAlignment(Pos.TOP_CENTER);
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}