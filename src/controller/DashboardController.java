package controller;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Estudiante;
import service.UVRateService;

public class DashboardController {

    @FXML
    private VBox sidebar;
    @FXML
    private Label nombreLabel;
    @FXML
    private Circle avatar;

    @FXML
    private Button btnCatedraticos;
    @FXML
    private Button btnCursos;
    @FXML
    private Button btnOrientador;
    @FXML
    private Button btnLogout;

    @FXML
    private StackPane contentPane;

    private Estudiante estudiante;
    private UVRateService service = new UVRateService();

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        nombreLabel.setText(estudiante.getNombre());

        // Avatar color random
        avatar.setFill(Color.web("#6C63FF"));

        cargarVista("catedraticos.fxml"); // vista inicial
    }

    @FXML
    public void initialize() {
        btnCatedraticos.setOnAction(e -> cargarVista("catedraticos.fxml"));
        btnCursos.setOnAction(e -> cargarVista("cursos.fxml"));
        btnOrientador.setOnAction(e -> cargarVista("orientador.fxml"));
        btnLogout.setOnAction(e -> cerrarSesion());
    }

    private void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + fxml));
            Pane vista = loader.load();

            // Pasar el estudiante y el servicio al controlador secundario
            if (loader.getController() instanceof SubControlador secc) {
                secc.setContext(estudiante, service);
            }

            contentPane.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cerrarSesion() {
        try {
            Stage stage = (Stage) contentPane.getScene().getWindow();
            Scene scene = new Scene(
                    FXMLLoader.load(getClass().getResource("/views/login.fxml")));
            scene.getStylesheets().add("/styles/common.css");
            scene.getStylesheets().add("/styles/light.css");
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
