package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import database.ConexionUVRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegistroController {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private void registrarUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String password = txtPassword.getText();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            System.out.println("Completa todos los campos");
            return;
        }

        try (Connection conn = ConexionUVRate.getConnection()) {
            String sql = "INSERT INTO usuario (nombre, correo, contraseña) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, password);
            stmt.executeUpdate();

            // Obtener ID generado
            ResultSet rs = stmt.getGeneratedKeys();
            int nuevoId = -1;
            if (rs.next()) {
                nuevoId = rs.getInt(1);
            }

            System.out.println("Usuario registrado correctamente");

            // Volver al login automáticamente
            volverLogin(null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void volverLogin(javafx.event.ActionEvent event) {
        System.out.println("Volviendo a login...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/login.fxml"));
            Parent root = loader.load();

            Stage stage;

            if (event != null) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            } else {
                // Si viene desde registrarUsuario
                stage = (Stage) txtNombre.getScene().getWindow();
            }

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/ui/views/styles/common.css");
            scene.getStylesheets().add("/ui/views/styles/light.css");
            scene.getStylesheets().add("/ui/views/styles/login.css");

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
