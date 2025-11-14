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

public class PerfilCatedraticoController implements SubControlador {

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblUpvotes;

    @FXML
    private Button btnUpvote;

    @FXML
    private VBox listaCursos;

    @FXML
    private VBox boxRankingCursos;

    @FXML
    private Button btnVolver;

    private Catedratico catedratico;
    private Estudiante estudiante;
    private UVRateService service;
    private DashboardController dashboard; // ← ★ NECESARIO

    // ============================================================
    // Recibir Dashboard desde el cargador de vistas
    // ============================================================
    @Override
    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    // ============================================================
    // Recibir contexto (estudiante + service)
    // ============================================================
    @Override
    public void setContext(Estudiante est, UVRateService serv) {
        this.estudiante = est;
        this.service = serv;

        // Acción del botón VOLVER
        btnVolver.setOnAction(e -> dashboard.cargarVista("catedraticos.fxml"));
    }

    // ============================================================
    // Recibir catedrático
    // ============================================================
    public void setCatedratico(Catedratico cat) {
        this.catedratico = cat;

        lblNombre.setText(cat.getNombre());
        lblUpvotes.setText(String.valueOf(service.contarUpvotes(cat.getId())));

        cargarCursos();
        configurarBotonUpvote();
        cargarRankingsPorCurso();

    }

    // ============================================================
    // Cargar cursos
    // ============================================================
    private void cargarCursos() {
        listaCursos.getChildren().clear();

        if (catedratico.getCursos() == null)
            return;

        for (Curso curso : catedratico.getCursos()) {
            Label lbl = new Label("• " + curso.getNombre() + " (" + curso.getCodigo() + ")");
            lbl.getStyleClass().add("curso-item");
            listaCursos.getChildren().add(lbl);
        }
    }

    // ============================================================
    // Configurar Upvote
    // ============================================================
    private void configurarBotonUpvote() {

        boolean yaVoto = service.yaVoto(estudiante.getId(), catedratico.getId());

        if (yaVoto)
            activarEstiloVotado();
        else
            activarEstiloNormal();

        btnUpvote.setOnAction(event -> toggleUpvote());
    }

    private void toggleUpvote() {

        // 1) Actualizar BD
        service.toggleUpvote(estudiante.getId(), catedratico.getId());

        // 2) RECARGAR el catedrático actualizado desde la BD
        this.catedratico = service.obtenerCatedraticoPorId(catedratico.getId());

        // 3) Actualizar label
        lblUpvotes.setText(String.valueOf(service.contarUpvotes(catedratico.getId())));

        // 4) Actualizar botón
        boolean yaVoto = service.yaVoto(estudiante.getId(), catedratico.getId());
        if (yaVoto)
            activarEstiloVotado();
        else
            activarEstiloNormal();

        // 5) REGENERAR ranking con los datos nuevos
        cargarRankingsPorCurso();
    }

    // ============================================================
    // Estilos
    // ============================================================
    private void activarEstiloNormal() {
        btnUpvote.getStyleClass().remove("btn-upvote");
        btnUpvote.getStyleClass().remove("btn-upvote-voted");

        btnUpvote.getStyleClass().add("btn-upvote");
        btnUpvote.setText("Upvote");
    }

    private void activarEstiloVotado() {
        btnUpvote.getStyleClass().remove("btn-upvote");
        btnUpvote.getStyleClass().remove("btn-upvote-voted");

        btnUpvote.getStyleClass().add("btn-upvote-voted");
        btnUpvote.setText("Quitar Upvote");
    }

    private void cargarRankingsPorCurso() {
        boxRankingCursos.getChildren().clear();

        for (Curso c : catedratico.getCursos()) {

            int pos = service.obtenerPosicionCatedraticoEnCurso(
                    catedratico.getId(),
                    c.getCodigo());

            Label lbl = new Label(
                    c.getNombre() + ": " +
                            (pos == -1 ? "No aparece en ranking" : "Posición #" + pos));

            boxRankingCursos.getChildren().add(lbl);
        }
    }

}
