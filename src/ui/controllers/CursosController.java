package ui.controllers;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Curso;
import model.Estudiante;
import service.UVRateService;

import java.util.ArrayList;
import java.util.List;

public class CursosController implements SubControlador {

    private DashboardController dashboard;

    @FXML
    private TextField searchField;

    @FXML
    private VBox listaCursos;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox rootBox;

    private Estudiante estudiante;
    private UVRateService service;

    @Override
    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void setContext(Estudiante estudiante, UVRateService service) {
        this.estudiante = estudiante;
        this.service = service;

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        cargarCursos();
        searchField.textProperty().addListener((obs, o, n) -> cargarCursos());
    }

    private void cargarCursos() {
        listaCursos.getChildren().clear();

        String filtro = searchField.getText().toLowerCase();
        ArrayList<Curso> cursos = service.obtenerTodosCursos();

        for (Curso c : cursos) {

            if (!c.getNombre().toLowerCase().contains(filtro)
                    && !String.valueOf(c.getId()).contains(filtro))
                continue;

            HBox card = crearCard(c);
            listaCursos.getChildren().add(card);

            System.out.println("Cursos encontrados: " + cursos.size());
            System.out.println("Servicio devolvió null? " + (cursos == null));

        }

        if (listaCursos.getChildren().isEmpty()) {
            Label empty = new Label("No hay cursos que coincidan con la búsqueda.");
            empty.getStyleClass().add("section-text");
            listaCursos.getChildren().add(empty);
        }

    }

    private HBox crearCard(Curso c) {
        HBox card = new HBox(10);
        card.getStyleClass().add("card");

        Label nombre = new Label(c.getNombre());
        nombre.getStyleClass().add("card-title");

        Label codigo = new Label("(" + c.getId() + ")");
        codigo.getStyleClass().add("card-upvotes");

        card.getChildren().addAll(nombre, codigo);

        card.setOnMouseClicked(e -> abrirPerfilCurso(c));

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
