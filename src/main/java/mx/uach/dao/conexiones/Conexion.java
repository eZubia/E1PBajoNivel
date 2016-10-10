package mx.uach.dao.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Conexión a base de datos MYSQL.
 * 
 * @author Erik David Zubia Hernández.
 * @version 1.0
 */
public class Conexion {

    private static final String USUARIO = "root";
    private static final String PASSWORD = "Miserable33!)(";
    private static final String CONEXION = "jdbc:mysql://localhost:3306/video_club?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
;
    private static Conexion INSTANCE;
    private Connection con;
    
    
    private Conexion() {
        initConnection();
    }
    
    private void initConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONEXION, USUARIO, PASSWORD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Conexion getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Conexion();
        }
        return INSTANCE;
    }

    public Connection getCon() {
        return con;
    }
   
    
}
