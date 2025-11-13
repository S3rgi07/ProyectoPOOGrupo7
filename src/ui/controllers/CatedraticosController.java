package ui.controllers;

import javafx.fxml.*;
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
    }

    private HBox crearCard(Catedratico c) {
        HBox box = new HBox(10);
        box.getStyleClass().add("card");

        Label nombre = new Label(c.getNombre());
        nombre.getStyleClass().add("card-title");

        Label upv = new Label("❤️ " + c.getUpvotes().getUpvotes());
        upv.getStyleClass().add("card-upvotes");

        box.getChildren().addAll(nombre, upv);

        box.setOnMouseClicked(e -> abrirPerfil(c));

        return box;

    }

    private void abrirPerfil(Catedratico c) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/views/perfil_catedratico.fxml"));
            Pane vista = loader.load();

            // Pasar el catedrático al controlador del perfil
            PerfilCatedraticoController controller = loader.getController();
            controller.setCatedratico(c);

            // Obtener el DashboardController
            DashboardController dash = (DashboardController) listaCatedraticos.getScene().getRoot().getUserData();

            // Mostrar la vista dentro del dashboard
            dash.cargarVistaDirecta(vista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
