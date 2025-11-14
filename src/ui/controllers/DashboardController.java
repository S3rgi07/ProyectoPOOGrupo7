package ui.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Estudiante;
import service.UVRateService;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

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

    private Object controladorActual;

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
                // CSS global
                newScene.getStylesheets().add(
                        getClass().getResource("/ui/views/styles/common.css").toExternalForm());
                newScene.getStylesheets().add(
                        getClass().getResource("/ui/views/styles/light.css").toExternalForm());
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

            controladorActual = loader.getController();

            if (controladorActual instanceof SubControlador sub) {
                sub.setContext(estudiante, service);
                sub.setDashboard(this);
            }

            // limpiar CSS del node root
            vista.getStylesheets().clear();

            // agregar CSS común
            vista.getStylesheets().add(
                    getClass().getResource("/ui/views/styles/common.css").toExternalForm());
            vista.getStylesheets().add(
                    getClass().getResource("/ui/views/styles/light.css").toExternalForm());

            // agregar CSS especial
            if (fxml.equals("perfil_catedratico.fxml")) {
                vista.getStylesheets().add(
                        getClass().getResource("/ui/views/styles/perfil_catedratico.css").toExternalForm());
            }

            if (fxml.equals("perfil_curso.fxml")) {
                vista.getStylesheets().add(
                        getClass().getResource("/ui/views/styles/perfil_curso.css").toExternalForm());
            }

            contentPane.getChildren().setAll(vista);

            // Animación suave al cargar vista
            FadeTransition ft = new FadeTransition(Duration.millis(180), vista);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (Exception e) {
            System.out.println("ERROR cargando: /ui/views/" + fxml);
            e.printStackTrace();
        }
    }

    // =========================================================
    // Cargar vista DIRECTA (por ejemplo: perfil de catedrático)
    // =========================================================
    public void cargarVistaDirecta(Pane vista) {
        try {
            // limpiar CSS del node root
            vista.getStylesheets().clear();

            // agregar CSS común
            vista.getStylesheets().add(
                    getClass().getResource("/ui/views/styles/common.css").toExternalForm());
            vista.getStylesheets().add(
                    getClass().getResource("/ui/views/styles/light.css").toExternalForm());

            // agregar CSS especial si es perfil curso
            if (vista.getId() != null && vista.getId().equals("perfilCursoRoot")) {
                vista.getStylesheets().add(
                        getClass().getResource("/ui/views/styles/perfil_curso.css").toExternalForm());
            }

            contentPane.getChildren().setAll(vista);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public Object getControladorActual() {
        return controladorActual;
    }

}
