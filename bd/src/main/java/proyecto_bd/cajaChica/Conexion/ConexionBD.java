package proyecto_bd.cajaChica.Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {
    //CREENSE UN USUARIO CON ESAS CREDENCIALES O EN SU DEFECTO, CAMBIAN USUARIO, PASSWORD Y BD(SIENDO ESTE EL NOMBRE QUE LE PONGAN A SU BASE DE DATOS EN SQLSERVER)
    Connection connect = null;
    String usuario = "usuario";
    String password = "usersql";
    String bd = "proyectoBD";
    String ip = "localhost";
    String port = "1433";

    String cadena = "jdbc:sqlserver://" + ip + ":" + port + ";databaseName=" + bd + ";encrypt=true;trustServerCertificate=true;";

    public Connection establecerConexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(cadena, usuario, password);
            System.out.println("Conectado correctamente"); // Mensaje de éxito

        } catch (ClassNotFoundException e) {
            // Error al cargar el DRIVER
            JOptionPane.showMessageDialog(null, "ERROR AL CARGAR EL DRIVER: " + e.toString());
            e.printStackTrace(); // Mostrar el rastreo de la pila para depuración
        } catch (SQLException e) {
            // Error de conexión a la base de datos
            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION: " + e.toString());
            e.printStackTrace(); // Mostrar el rastreo de la pila para depuración
        }
        return connect;
    }

    // Método para cerrar la conexión
    public void cerrarConexion() {
        if (connect != null) {
            try {
                connect.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                // Error al cerrar la conexión
                JOptionPane.showMessageDialog(null, "ERROR AL CERRAR LA CONEXIÓN: " + e.toString());
                e.printStackTrace(); // Mostrar el rastreo de la pila para depuración
            }
        }
    }
}


// package proyecto_bd.cajaChica.Conexion;

// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.SQLException;
// import javax.swing.JOptionPane;

// public class ConexionBD {

//     Connection connect = null;
//     String usuario = "postgres"; // Usuario de PostgreSQL
//     String password = "1234"; // Contraseña de PostgreSQL
//     String bd = "proyectoBD"; // Nombre de la base de datos en PostgreSQL
//     String ip = "localhost"; // Dirección IP o nombre del host de PostgreSQL
//     String port = "5432"; // Puerto de PostgreSQL

//     String cadena = "jdbc:postgresql://" + ip + ":" + port + "/" + bd;

//     public Connection establecerConexion() {
//         try {
//             // Cargar el controlador JDBC de PostgreSQL
//             Class.forName("org.postgresql.Driver");
//             // Establecer la conexión
//             connect = DriverManager.getConnection(cadena, usuario, password);
//             System.out.println("Conectado correctamente a PostgreSQL"); // Mensaje de éxito
//         } catch (ClassNotFoundException e) {
//             // Error si no se encuentra el controlador JDBC de PostgreSQL
//             JOptionPane.showMessageDialog(null, "ERROR: No se encontró el controlador JDBC de PostgreSQL.");
//             e.printStackTrace(); // Mostrar el rastreo de la pila para depuración
//         } catch (SQLException e) {
//             // Error de conexión a la base de datos PostgreSQL
//             JOptionPane.showMessageDialog(null, "ERROR DE CONEXION: " + e.toString());
//             e.printStackTrace(); // Mostrar el rastreo de la pila para depuración
//         }
//         return connect;
//     }

//     // Método para cerrar la conexión
//     public void cerrarConexion() {
//         if (connect != null) {
//             try {
//                 connect.close();
//                 System.out.println("Conexión cerrada correctamente");
//             } catch (SQLException e) {
//                 // Error al cerrar la conexión con PostgreSQL
//                 JOptionPane.showMessageDialog(null, "ERROR AL CERRAR LA CONEXIÓN: " + e.toString());
//                 e.printStackTrace(); // Mostrar el rastreo de la pila para depuración
//             }
//         }
//     }
// }
