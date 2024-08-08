package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SQLiteDataHelper {
    private static String DBPathConnection = "jdbc:sqlite:DataBase//EXOBOT.sqlite"; 
    private static Connection conn = null;
    // protected SQLiteDataHelper(){}
    
    /*
     * Descripcion: Abre la conexión a la base de datos si está abierta.
     * @throws Exception Si ocurre un error durante el cierre de la conexión.
     * autor: Katherine Sanchez
     */
    
    protected static synchronized Connection openConnection() throws Exception{
        try {
            if(conn == null)
                conn = DriverManager.getConnection(DBPathConnection);
        } catch (SQLException e) {
             throw e;   //new Exception(e,"SQLiteDataHelper","Fallo la coneccion a la base de datos");
        } 
        return conn;
    }

     /*
     * Descripcion: Cierra la conexión a la base de datos si está abierta.
     * @throws Exception Si ocurre un error durante el cierre de la conexión.
     * autor: Katherine Sanchez
     */
    protected static void closeConnection() throws Exception{
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            throw e;    //new Exception(e,"SQLiteDataHelper", "Fallo la conección con la base de datos");
        }
    }
}

