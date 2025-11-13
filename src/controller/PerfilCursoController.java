package controller;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Catedratico;
import model.Curso;
import model.Estudiante;
import service.UVRateService;

import java.util.List;

public class PerfilCursoController {

    @FXML
    private Label nombreLabel;
    @FXML
    private Label descLabel;
    @FXML
    private VBox listaCatedraticos;

    private UVRateService service;
    private Estudiante estudiante;

    public void setContext(Estudiante estudiante, UVRateService service, Curso curso) {
        this.service = service;
        this.estudiante = estudiante;

        nombreLabel.setText(curso.getNombre());
        descLabel.setText(curso.getDescripcion());

        cargarCatedraticos(curso);
    }

    private void cargarCatedraticos(Curso curso) {
        listaCatedraticos.getChildren().clear();

        List<Catedratico> lista = service.obtenerCatedraticosPorCurso(curso.getCodigo());

        for (Catedratico cat : lista) {
            HBox card = new HBox();
            card.getStyleClass().add("card");

            Label label = new Label(cat.getNombre() + "   ❤️ " + cat.getUpvotes().getUpvotes());
            label.getStyleClass().add("card-title");

            card.getChildren().add(label);
            listaCatedraticos.getChildren().add(card);

            // TODO: Abrir perfil de catedrático
        }
    }
}
