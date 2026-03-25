
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgres {

    // Configuración de la base de datos
    // El puerto por defecto de PostgreSQL es 5432
    private static final String URL = "jdbc:postgresql://localhost:5432/tu_base_de_datos";
    private static final String USUARIO = "tu_usuario";
    private static final String PASSWORD = "tu_password";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Intentar establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión establecida con éxito a PostgreSQL.");
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }   
}