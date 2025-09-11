import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class ConexionUVRate {
    
    private static final String URL = "jdbc:mysql://localhost:3306/uvrate_db";
    private static final String USER = "root";  
    private static final String PASSWORD = "";  
    
    public static Connection getConnection() {
    Connection conn = null;
    try {
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Conexión a UVRate establecida correctamente.");
    } catch (SQLException e) {
        System.out.println("Error al conectar con la base de datos.");
        e.printStackTrace();
    }
    return conn;
}
