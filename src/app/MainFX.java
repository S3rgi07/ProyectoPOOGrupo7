package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ui/views/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/ui/views/styles/common.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/ui/views/styles/light.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/ui/views/styles/login.css").toExternalForm());

        stage.setTitle("UVRate");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
