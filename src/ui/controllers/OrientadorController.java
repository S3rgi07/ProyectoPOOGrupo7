package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import model.Curso;
import model.Estudiante;
import model.MetaData;
import service.UVRateService;

import java.util.List;

public class OrientadorController implements SubControlador {

    @FXML
    private ComboBox<String> comboMeta;

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

        cargarMetasDisponibles();

        comboMeta.valueProperty().addListener((obs, o, n) -> cargarSugerencias());
    }

    /** Llena el ComboBox con todas las metas del MetaData */
    private void cargarMetasDisponibles() {
        comboMeta.getItems().setAll(MetaData.getMetas()); // << Nuevo método
    }

    private void cargarSugerencias() {
        boxResultados.getChildren().clear();

        String meta = comboMeta.getValue();
        if (meta == null || meta.isEmpty())
            return;

        List<String> codigos = MetaData.buscarCursos(meta);

        if (codigos.isEmpty()) {
            Label empty = new Label("No hay cursos asociados a esa meta.");
            empty.getStyleClass().add("section-text");
            boxResultados.getChildren().add(empty);
            return;
        }

        for (String codigo : codigos) {
            Curso c = new Curso(
                    -1,
                    codigo, // mostramos código como nombre
                    "Curso recomendado según tu meta.");

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

        Label desc = new Label(curso.getDescripcion());
        desc.getStyleClass().add("section-text");
        desc.setWrapText(true);

        text.getChildren().addAll(nombre, desc);
        card.getChildren().add(text);

        return card;
    }
}
