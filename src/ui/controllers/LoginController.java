package ui.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import database.ConexionUVRate;
import model.Estudiante;

import java.sql.*;

public class LoginController {

    @FXML
    private TextField correoField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;
    @FXML
    private AnchorPane root;
    @FXML
    private VBox cardBox;

    // ============================
    // Inicialización
    // ============================
    @FXML
    public void initialize() {

        // Estilos: solo cargar cuando ya haya escena
        root.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().setAll(
                        "/ui/views/styles/common.css",
                        "/ui/views/styles/light.css",
                        "/ui/views/styles/login.css");
            }
        });

        animarEntrada();
    }

    // ============================
    // Animaciones
    // ============================

    private void animarEntrada() {

        // Fade de toda la pantalla
        FadeTransition fadeRoot = new FadeTransition(Duration.millis(450), root);
        fadeRoot.setFromValue(0);
        fadeRoot.setToValue(1);
        fadeRoot.play();

        // Movimiento del card
        TranslateTransition slide = new TranslateTransition(Duration.millis(500), cardBox);
        slide.setFromY(30);
        slide.setToY(0);
        slide.setCycleCount(1);
        slide.play();
    }

    private void animarError() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(70), messageLabel);
        tt.setFromX(-8);
        tt.setToX(8);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

    // ============================
    // Acción: Login
    // ============================

    @FXML
    private void onLogin() {
        String correo = correoField.getText();
        String pass = passwordField.getText();

        if (correo.isEmpty() || pass.isEmpty()) {
            showMessage("Completa todos los campos.", "error");
            animarError();
            return;
        }

        try (Connection conn = ConexionUVRate.getConnection()) {

            String sql = "SELECT * FROM usuario WHERE correo = ? AND contraseña = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, correo);
            stmt.setString(2, pass);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Estudiante estudiante = new Estudiante(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        0,
                        rs.getString("correo"));

                abrirDashboard(estudiante);

            } else {
                showMessage("Correo o contraseña incorrectos.", "error");
                animarError();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Error al conectar con la base de datos.", "error");
            animarError();
        }
    }

    // ============================
    // Acción: Ir a registro
    // ============================

    @FXML
    private void onRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/registro.fxml"));
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().setAll(
                    "/ui/views/styles/common.css",
                    "/ui/views/styles/light.css",
                    "/ui/views/styles/login.css");

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error al abrir el registro.", "error");
            animarError();
        }
    }

    // ============================
    // Abrir Dashboard
    // ============================

    private void abrirDashboard(Estudiante estudiante) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/dashboard.fxml"));
            Scene scene = new Scene(loader.load());

            DashboardController controller = loader.getController();
            controller.setEstudiante(estudiante);

            scene.getStylesheets().setAll(
                    "/ui/views/styles/common.css",
                    "/ui/views/styles/light.css",
                    "/ui/views/styles/dashboard.css");

            Stage stage = (Stage) root.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error al cargar dashboard.", "error");
            animarError();
        }
    }

    // ============================
    // Mostrar mensajes
    // ============================

    private void showMessage(String msg, String type) {
        messageLabel.setText(msg);
        messageLabel.getStyleClass().removeAll("error", "success");
        messageLabel.getStyleClass().add(type);
    }

}
