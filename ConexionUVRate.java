import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.InputStream;
import java.io.IOException;

public class ConexionUVRate {

    private static final String URL = "jdbc:mysql://localhost:3306/uvrate_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Intentamos conectar directamente a la base de datos
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a UVRate establecida correctamente.");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1049) { // Error que hace alusión a base de datos inexistente
                System.out.println("Base de datos no existe. Creando UVRate...");
                try (Connection connServer = DriverManager.getConnection("jdbc:mysql://localhost:3306/", USER, PASSWORD);
                     Statement stmt = connServer.createStatement()) {

                    // Si no existe la base de datos, se crea
                    stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS uvrate_db");
                    System.out.println("Base de datos UVRate creada correctamente.");

                    // Conexión con la nueva base de datos
                    conn = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("Conexión a UVRate establecida correctamente.");

                    // Si no existen ya las tablas, se crean
                    ejecutarSchema(conn);

                } catch (SQLException | IOException ex) {
                    System.out.println("Error al crear la base de datos o ejecutar el schema.");
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Error al conectar con la base de datos.");
                e.printStackTrace();
            }
        }
        return conn;
    }

    private static void ejecutarSchema(Connection conn) throws IOException, SQLException {
        // Carga el schema.sql desde los recursos del proyecto
        try (InputStream is = ConexionUVRate.class.getResourceAsStream("/schema.sql")) {
            if (is == null) {
                throw new IOException("No se encontró schema.sql en recursos");
            }
            String script = new String(is.readAllBytes());
            String[] instrucciones = script.split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sql : instrucciones) {
                    sql = sql.trim();
                    if (!sql.isEmpty()) {
                        stmt.execute(sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS"));
                    }
                }
            }
            System.out.println("Script schema.sql ejecutado correctamente.");
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
