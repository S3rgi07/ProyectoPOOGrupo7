package ui.controllers;

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

    // =========================================================
    // Recibir estudiante desde Login
    // =========================================================
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
        nombreLabel.setText(estudiante.getNombre());

        // Avatar simple
        avatar.setFill(Color.web("#6C63FF"));

        // Vista inicial
        cargarVista("catedraticos.fxml");
    }

    // =========================================================
    // Inicialización del dashboard
    // =========================================================
    @FXML
    public void initialize() {

        // botones del menú
        btnCatedraticos.setOnAction(e -> cargarVista("catedraticos.fxml"));
        btnCursos.setOnAction(e -> cargarVista("cursos.fxml"));
        btnOrientador.setOnAction(e -> cargarVista("orientador.fxml"));
        btnLogout.setOnAction(e -> cerrarSesion());

        // ⚠️ IMPORTANTE: guardar referencia del Dashboard para subcontroladores
        // Se hará cuando la escena exista
        contentPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getRoot().setUserData(this);
            }
        });
    }

    // =========================================================
    // Cargar vista desde un archivo FXML
    // =========================================================
    public void cargarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/views/" + fxml));

            Pane vista = loader.load();

            // Pasar estudiante + service si el controlador es un SubControlador
            if (loader.getController() instanceof SubControlador sub) {
                sub.setContext(estudiante, service);
            }

            contentPane.getChildren().setAll(vista);

        } catch (Exception e) {
            System.out.println("ERROR cargando: /ui/views/" + fxml);
            e.printStackTrace();
        }
    }

    // =========================================================
    // Cargar vista DIRECTA (por ejemplo: perfil de catedrático)
    // =========================================================
    public void cargarVistaDirecta(Pane vista) {
        contentPane.getChildren().setAll(vista);
    }

    // =========================================================
    // Cerrar sesión
    // =========================================================
    private void cerrarSesion() {
        try {
            Stage stage = (Stage) contentPane.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/views/login.fxml"));
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().add("/ui/views/styles/common.css");
            scene.getStylesheets().add("/ui/views/styles/light.css");

            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
