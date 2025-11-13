package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionUVRate {

    // Ruta relativa al archivo SQLite dentro del proyecto
    private static final String DB_URL = "jdbc:sqlite:database/uvrate_db.sqlite";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Cargar el driver JDBC de SQLite
            Class.forName("org.sqlite.JDBC");

            // Intentar conexión
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("[OK] Conectado a la base de datos SQLite correctamente.");

        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] No se encontró el driver JDBC de SQLite.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("[ERROR] Falló la conexión a la base de datos SQLite.");
            e.printStackTrace();
        }

        return conn;
    }

    public static void probarConexion() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("[TEST] La conexión con la base de datos funciona correctamente.");
            } else {
                System.out.println("[TEST] No se pudo establecer conexión con la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("[TEST ERROR] Error al cerrar la conexión de prueba.");
            e.printStackTrace();
        }
    }

    public static void hacerBackup() {
        try {
            java.nio.file.Path origen = java.nio.file.Paths.get("database/uvrate_db.sqlite");
            java.nio.file.Path destino = java.nio.file.Paths.get("database/uvrate_db_backup.sqlite");

            java.nio.file.Files.copy(origen, destino, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[BACKUP] Copia de seguridad creada correctamente.");
        } catch (Exception e) {
            System.err.println("[BACKUP ERROR] No se pudo crear el respaldo de la base de datos.");
            e.printStackTrace();
        }
    }

}
