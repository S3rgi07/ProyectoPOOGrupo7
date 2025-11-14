package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    }
}
