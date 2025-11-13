package controller;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Curso;
import model.Estudiante;
import service.UVRateService;

import java.util.List;

public class CursosController implements SubControlador {

    @FXML
    private TextField searchField;
    @FXML
    private VBox listaCursos;

    private Estudiante estudiante;
    private UVRateService service;

    @Override
    public void setContext(Estudiante estudiante, UVRateService service) {
        this.estudiante = estudiante;
        this.service = service;

        cargarCursos();
        searchField.textProperty().addListener((obs, o, n) -> cargarCursos());
    }

    private void cargarCursos() {
        listaCursos.getChildren().clear();

        String filtro = searchField.getText().toLowerCase();
        List<Curso> cursos = service.obtenerTodosCursos();

        for (Curso c : cursos) {
            if (!c.getNombre().toLowerCase().contains(filtro))
                continue;

            HBox card = crearCard(c);
            listaCursos.getChildren().add(card);
        }
    }

    private HBox crearCard(Curso c) {
        HBox card = new HBox();
        card.getStyleClass().add("card");

        Label nombre = new Label(c.getNombre());
        nombre.getStyleClass().add("card-title");

        card.getChildren().add(nombre);

        card.setOnMouseClicked(e -> abrirPerfilCurso(c));

        return card;
    }

    private void abrirPerfilCurso(Curso curso) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/perfil_curso.fxml"));
            Pane vista = loader.load();

            PerfilCursoController controller = loader.getController();
            controller.setContext(estudiante, service, curso);

            // Reemplazar contenido (heredado del Dashboard)
            ((StackPane) listaCursos.getScene().lookup("#contentPane")).getChildren().setAll(vista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
