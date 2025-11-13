package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/views/login.fxml"));

        Scene scene = new Scene(loader.load());

        // RUTAS REALES ABSOLUTAS
        scene.getStylesheets().add(getClass().getResource("/ui/views/styles/login.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/ui/views/styles/common.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/ui/views/styles/light.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
