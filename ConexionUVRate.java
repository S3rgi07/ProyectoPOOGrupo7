import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionUVRate {


// Ruta relativa al archivo SQLite dentro del proyecto
private static final String DB_URL = "jdbc:sqlite:database/uvrate_db.sqlite";
