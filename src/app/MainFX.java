package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Label("Hola desde UVRate JavaFX!"), 400, 200));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

