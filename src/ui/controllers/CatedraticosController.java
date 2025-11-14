package ui.controllers;

import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Catedratico;
import model.Estudiante;
import service.UVRateService;

import java.util.List;

public class CatedraticosController implements SubControlador {

    private DashboardController dashboard;

    @Override
    public void setDashboard(DashboardController dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    private TextField searchField;
    @FXML
    private VBox listaCatedraticos;

    private UVRateService service;
    private Estudiante estudiante;

    @Override
    public void setContext(Estudiante estudiante, UVRateService service) {
        this.estudiante = estudiante;
        this.service = service;
        cargarCatedraticos();
    }

    @FXML
    public void initialize() {
        searchField.textProperty().addListener((obs, oldValue, newValue) -> cargarCatedraticos());
    }

    private void cargarCatedraticos() {
        listaCatedraticos.getChildren().clear();

        String filtro = searchField.getText() == null
                ? ""
                : searchField.getText().toLowerCase();

        List<Catedratico> cats = service.obtenerTodosCatedraticos();
        if (cats == null || cats.isEmpty())
            return;

        for (Catedratico c : cats) {
            if (!c.getNombre().toLowerCase().contains(filtro))
                continue;

            listaCatedraticos.getChildren().add(crearCard(c));
        }

        if (listaCatedraticos.getChildren().isEmpty()) {
            Label empty = new Label("No se encontraron coincidencias");
            empty.getStyleClass().add("section-text");
            listaCatedraticos.getChildren().add(empty);
        }

    }

    private HBox crearCard(Catedratico c) {
        HBox box = new HBox(10);
        box.getStyleClass().add("card");

        Label nombre = new Label(c.getNombre());
        nombre.getStyleClass().add("card-title");

        Label upv = new Label("" + c.getUpvotes().getUpvotes());
        upv.getStyleClass().add("card-upvotes");

        box.getChildren().addAll(nombre, upv);

        box.setOnMouseClicked(e -> abrirPerfil(c));

        return box;

    }

    public void abrirPerfil(Catedratico cat) {
        try {
            // 1) Cargar vista desde Dashboard (UNA SOLA VEZ)
            dashboard.cargarVista("perfil_catedratico.fxml");

            // 2) Obtener el controlador que el Dashboard acaba de cargar
            PerfilCatedraticoController controller = (PerfilCatedraticoController) dashboard.getControladorActual();

            // 3) Pasar contexto primero
            controller.setContext(estudiante, service);

            // 4) Luego pasar catedrático
            controller.setCatedratico(cat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
