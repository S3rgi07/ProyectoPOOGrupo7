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

}
