package controller;

import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Catedratico;
import model.Estudiante;
import service.UVRateService;

import java.util.List;

public class CatedraticosController implements SubControlador {

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

    private void cargarCatedraticos() {
        listaCatedraticos.getChildren().clear();
        String filtro = searchField.getText().toLowerCase();

        List<Catedratico> cats = service.obtenerTodosCatedraticos();

        for (Catedratico c : cats) {
            if (!c.getNombre().toLowerCase().contains(filtro))
                continue;

            HBox card = crearCard(c);
            listaCatedraticos.getChildren().add(card);
        }

        searchField.textProperty().addListener((obs, o, n) -> cargarCatedraticos());
    }

    private HBox crearCard(Catedratico c) {
        HBox box = new HBox();
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
        // más adelante generamos el perfil en FXML
    }
}
