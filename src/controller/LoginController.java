package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public void initialize() {
        // Aquí podés agregar animaciones o lógica inicial si quieres
    }

    // --------------------------------------------------------------------
    // LOGIN
    // --------------------------------------------------------------------
    @FXML
    private void onLogin() {
        String correo = correoField.getText();
        String pass = passwordField.getText();

        if (correo.isEmpty() || pass.isEmpty()) {
            showMessage("Completa todos los campos.", "error");
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
                        rs.getString("nombre"),
                        0, // carnet no existe en la tabla
                        correo);
                estudiante.setId(rs.getInt("id"));

                abrirDashboard(estudiante);

            } else {
                showMessage("Correo o contraseña incorrectos.", "error");
            }

        } catch (SQLException e) {
            showMessage("Error al conectar con la base de datos.", "error");
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------
    // IR A REGISTRO
    // --------------------------------------------------------------------
    @FXML
    private void onRegistro() {
        try {
            Stage stage = (Stage) correoField.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/registro.fxml"));
            Scene scene = new Scene(loader.load());

            // 🔥 VOLVER A AGREGAR TODOS LOS CSS
            scene.getStylesheets().add("/ui/views/styles/login.css");
            scene.getStylesheets().add("/ui/views/styles/common.css");
            scene.getStylesheets().add("/ui/views/styles/light.css");

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --------------------------------------------------------------------
    // ABRIR DASHBOARD
    // --------------------------------------------------------------------
    private void abrirDashboard(Estudiante estudiante) {
        try {
            Stage stage = (Stage) correoField.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ui/views/dashboard.fxml"));

            Scene scene = new Scene(loader.load());

            // Pasar datos al Dashboard
            DashboardController controller = loader.getController();
            controller.setEstudiante(estudiante);

            // Agregar CSS
            scene.getStylesheets().add(
                    getClass().getResource("/ui/views/styles/common.css")
                            .toExternalForm());
            scene.getStylesheets().add(
                    getClass().getResource("/ui/views/styles/light.css")
                            .toExternalForm());

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("No se pudo cargar el dashboard.", "error");
        }
    }

    // --------------------------------------------------------------------
    // MENSAJES EN PANTALLA
    // --------------------------------------------------------------------
    private void showMessage(String msg, String type) {
        messageLabel.setText(msg);
        messageLabel.getStyleClass().removeAll("success", "error");
        messageLabel.getStyleClass().add(type);
    }
}
