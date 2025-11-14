package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @FXML
    private Label lblTipo;

    @FXML
    private Label lblDescripcion;

    @FXML
    private Label lblCompetencias;

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
        lblCodigo.setText("Código: " + curso.getCodigo());
        lblTipo.setText("Tipo: " + (curso.getTipo() == null ? "N/A" : curso.getTipo()));

        lblDescripcion.setText(
                (curso.getDescripcion() == null || curso.getDescripcion().isEmpty())
                        ? "Sin descripción disponible."
                        : curso.getDescripcion());

        lblCompetencias.setText(
                (curso.getCompetencias() == null || curso.getCompetencias().isEmpty())
                        ? "No se especificaron competencias."
                        : curso.getCompetencias());

        cargarRankings();

    }

    private void cargarRankings() {
        boxRankingUpvotes.getChildren().clear();
        boxRankingSemestres.getChildren().clear();

        int idCurso = curso.getCodigo();

        var rankUpvotes = service.obtenerRankingUpvotesPorCurso(idCurso);
        var rankSemestres = service.obtenerRankingSemestresPorCurso(idCurso);

        int pos = 1;
        for (Catedratico c : rankUpvotes) {
            Label lbl = new Label(pos + ". " + c.getNombre() + " (" + c.getCantidadUpvotes() + ")");
            boxRankingUpvotes.getChildren().add(lbl);
            pos++;
        }

        pos = 1;
        for (Catedratico c : rankSemestres) {
            Label lbl = new Label(pos + ". " + c.getNombre() + " (" + c.getSemestres() + ")");
            boxRankingSemestres.getChildren().add(lbl);
            pos++;
        }
    }

    @FXML
    private void volverCursos() {
        dashboard.cargarVista("cursos.fxml");
    }

}
