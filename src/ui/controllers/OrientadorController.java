package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Curso;
import model.Estudiante;
import service.UVRateService;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;

import java.util.List;

public class OrientadorController implements SubControlador {

    @FXML
    private TextField txtMeta;

    @FXML
    private VBox boxResultados;

    private Estudiante estudiante;
    private UVRateService service;
    private DashboardController dashboard;

    @Override
    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void setContext(Estudiante est, UVRateService serv) {
        this.estudiante = est;
        this.service = serv;

        // Listener de búsqueda en vivo
        txtMeta.textProperty().addListener((obs, oldV, newV) -> cargarSugerencias());
    }

    private void cargarSugerencias() {
        boxResultados.getChildren().clear();

        String meta = txtMeta.getText().trim();
        if (meta.isEmpty())
            return;

        List<Curso> sugeridos = service.sugerirCursosPorMeta(meta);

        if (sugeridos.isEmpty()) {
            Label empty = new Label("No hay cursos asociados a esa meta.");
            empty.getStyleClass().add("section-text");
            boxResultados.getChildren().add(empty);
            return;
        }

        for (Curso c : sugeridos) {
            boxResultados.getChildren().add(crearCardCurso(c));
        }
    }

    private HBox crearCardCurso(Curso curso) {
        HBox card = new HBox(10);
        card.getStyleClass().add("card");
        card.setPadding(new Insets(15));

        VBox text = new VBox(3);

        Label nombre = new Label(curso.getNombre());
        nombre.getStyleClass().add("card-title");

        Label desc = new Label(
                (curso.getDescripcion() == null || curso.getDescripcion().isEmpty())
                        ? "Sin descripción disponible."
                        : curso.getDescripcion());
        desc.getStyleClass().add("section-text");
        desc.setWrapText(true);

        text.getChildren().addAll(nombre, desc);
        card.getChildren().add(text);

        card.setOnMouseClicked(e -> abrirPerfilCurso(curso));

        return card;
    }

    private void abrirPerfilCurso(Curso curso) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/views/perfil_curso.fxml"));

            Pane vista = loader.load();
            PerfilCursoController controller = loader.getController();

            controller.setDashboard(dashboard);
            controller.setContext(estudiante, service);
            controller.setCurso(curso);

            dashboard.cargarVistaDirecta(vista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
