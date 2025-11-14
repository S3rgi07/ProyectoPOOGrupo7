package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Catedratico;
import model.Curso;
import model.Estudiante;
import service.UVRateService;

public class PerfilCursoController implements SubControlador {

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblCodigo;

    // @FXML
    // private Label lblTipo;

    @FXML
    private Label lblDescripcion;

    @FXML
    private Button btnVolver;

    @FXML
    private VBox boxRankingUpvotes;
    @FXML
    private VBox boxRankingSemestres;

    private Estudiante estudiante;
    private UVRateService service;
    private DashboardController dashboard;

    private Curso curso;

    @Override
    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void setContext(Estudiante est, UVRateService serv) {
        this.estudiante = est;
        this.service = serv;

        // Acción de volver
        btnVolver.setOnAction(e -> dashboard.cargarVista("cursos.fxml"));
    }

    public void setCurso(Curso curso) {
        this.curso = curso;

        lblNombre.setText(curso.getNombre());
        lblCodigo.setText("Código: " + curso.getId());
        // lblTipo.setText("Tipo: " + (curso.getTipo() == null ? "N/A" :
        // curso.getTipo()));

        lblDescripcion.setText(
                (curso.getDescripcion() == null || curso.getDescripcion().isEmpty())
                        ? "Sin descripción disponible."
                        : curso.getDescripcion());

        cargarRankings();

    }

    private void cargarRankings() {
        boxRankingUpvotes.getChildren().clear();
        boxRankingSemestres.getChildren().clear();

        // 1) Traer SIEMPRE los catedráticos desde la BD
        java.util.List<Catedratico> catedraticos = service.obtenerCatedraticosPorCurso(curso.getId());

        if (catedraticos.isEmpty()) {
            Label lbl = new Label("Este curso aún no tiene catedráticos registrados.");
            lbl.getStyleClass().add("section-text");
            boxRankingUpvotes.getChildren().add(lbl);
            return;
        }

        // 2) ORDENAR por upvotes usando SIEMPRE la BD
        java.util.List<Catedratico> ordenadosPorUpvotes = new java.util.ArrayList<>(catedraticos);
        ordenadosPorUpvotes.sort((a, b) -> {
            int upA = service.contarUpvotes(a.getId());
            int upB = service.contarUpvotes(b.getId());
            return Integer.compare(upB, upA); // descendente
        });

        // Tomar top 3 (o todos si quieres)
        int limite = Math.min(3, ordenadosPorUpvotes.size());

        for (int i = 0; i < limite; i++) {
            Catedratico cat = ordenadosPorUpvotes.get(i);
            int upvotes = service.contarUpvotes(cat.getId());

            Label lbl = new Label(
                    "#" + (i + 1) + " " + cat.getNombre() + " — " + upvotes + " upvotes");
            lbl.getStyleClass().add("section-text");

            boxRankingUpvotes.getChildren().add(lbl);

            // (Opcional) click para ir al perfil del catedrático
            lbl.setOnMouseClicked(e -> abrirPerfilCatedratico(cat));
        }

        // 3) ORDENAR por semestres (esto sí usando el campo semestres del modelo)
        java.util.List<Catedratico> ordenadosPorSemestres = new java.util.ArrayList<>(catedraticos);
        ordenadosPorSemestres.sort((a, b) -> Integer.compare(b.getSemestres(), a.getSemestres()));

        limite = Math.min(3, ordenadosPorSemestres.size());

        for (int i = 0; i < limite; i++) {
            Catedratico cat = ordenadosPorSemestres.get(i);

            Label lbl = new Label(
                    "#" + (i + 1) + " " + cat.getNombre() + " — " + cat.getSemestres() + " semestres");
            lbl.getStyleClass().add("section-text");

            boxRankingSemestres.getChildren().add(lbl);

            lbl.setOnMouseClicked(e -> abrirPerfilCatedratico(cat));
        }
    }

    @FXML
    private void volverCursos() {
        dashboard.cargarVista("cursos.fxml");
    }

    private void abrirPerfilCatedratico(Catedratico cat) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/views/perfil_catedratico.fxml"));
            Pane vista = loader.load();

            PerfilCatedraticoController controller = loader.getController();
            controller.setDashboard(dashboard);
            controller.setContext(estudiante, service);
            controller.setCatedratico(cat);

            dashboard.cargarVistaDirecta(vista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
