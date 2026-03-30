
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionPostgres {
   static Connection conexion = null;
    
    static final String URL = "jdbc:postgresql://localhost:5432/tu_base_de_datos";
    static final String USER = "postgres";
    static final String PASSWORD = "tu_password";

    private ConexionPostgres() {}
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Registrar el driver (opcional en versiones modernas de JDBC)
                Class.forName("org.postgresql.Driver");
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión establecida con éxito.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }
    
    //OJO - usa VALUES (?,?,?)
    public void DoQuery(String QUERY, Object VALUES[]) throws SQLException{
        if (ConexionPostgres.conexion == null || ConexionPostgres.conexion.isClosed()) {getConexion();}
        
        try (PreparedStatement pstmt = conexion.prepareStatement(QUERY)) {
            for(int i=0; i<VALUES.length; i++){
                if(VALUES[i] instanceof String){pstmt.setString(i+1, (String) VALUES[i]);}
                if(VALUES[i] instanceof Boolean){pstmt.setBoolean(i+1, (boolean) VALUES[i]);}
                if(VALUES[i] instanceof Number){pstmt.setInt(i+1, (int) VALUES[i]);}
            }
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {System.out.println("¡Query hecho con éxito!");}
        } catch(SQLException SQLE) {
            System.out.println("Error del Query: " + SQLE.getMessage());
            throw SQLE;
        }
    }
}