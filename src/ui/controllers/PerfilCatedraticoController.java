package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Catedratico;
import model.Curso;
import model.Estudiante;
import service.UVRateService;

public class PerfilCatedraticoController implements SubControlador {

    @FXML
    private Label lblNombre;
    @FXML
    private Label lblUpvotes;
    @FXML
    private VBox listaCursos;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnUpvote;

    private UVRateService service;
    private Estudiante estudiante;
    private DashboardController dashboard;
    private Catedratico actual;

    @Override
    public void setContext(Estudiante estudiante, UVRateService service) {
        this.estudiante = estudiante;
        this.service = service;
    }

    @Override
    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    /** Recibido desde CatedraticosController */
    public void cargarDatos(Catedratico c) {
        this.actual = c;

        lblNombre.setText(c.getNombre());
        lblUpvotes.setText(String.valueOf(c.getCantidadUpvotes()));

        // mostrar cursos
        listaCursos.getChildren().clear();
        for (Curso curso : c.getCursos()) {
            Label item = new Label("• " + curso.getNombre());
            item.getStyleClass().add("curso-item");
            listaCursos.getChildren().add(item);
        }

        // actualizar apariencia de botón
        actualizarBotonUpvote();

        // actions
        btnVolver.setOnAction(e -> dashboard.cargarVista("catedraticos"));
        btnUpvote.setOnAction(e -> toggleUpvote());
    }

    /** Cambia el estilo según si el estudiante ya votó */
    private void actualizarBotonUpvote() {
        boolean yaVoto = service.estudianteYaUpvoteo(estudiante, actual);

        if (yaVoto) {
            btnUpvote.setText("✓ Upvoted");
            btnUpvote.getStyleClass().remove("btn-upvote-default");
            btnUpvote.getStyleClass().add("btn-upvote-active");
        } else {
            btnUpvote.setText("❤️ Upvote");
            btnUpvote.getStyleClass().remove("btn-upvote-active");
            btnUpvote.getStyleClass().add("btn-upvote-default");
        }
    }

    /** Maneja dar o quitar upvote */
    private void toggleUpvote() {
        boolean yaVoto = service.estudianteYaUpvoteo(estudiante, actual);

        if (yaVoto) {
            service.quitarUpvote(estudiante, actual);
        } else {
            service.darUpvote(estudiante, actual);
        }

        lblUpvotes.setText(String.valueOf(actual.getCantidadUpvotes()));
        actualizarBotonUpvote();
    }
}
