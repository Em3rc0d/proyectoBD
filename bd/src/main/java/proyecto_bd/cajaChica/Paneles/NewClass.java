package proyecto_bd.cajaChica.Paneles;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.EntregaDineroDAO;
import proyecto_bd.cajaChica.Entidades.EntregaDinero;

public class NewClass {
    public static void main(String[] args){
        ConexionBD conexionBD = new ConexionBD();
        Connection conn = conexionBD.establecerConexion();

        if (conn != null) {
            System.out.println("Conexi�n establecida con �xito.");

<<<<<<< HEAD
            EntregaDinero entregaDinero = new EntregaDinero(1, 1, Date.valueOf("2023-05-01"), 500.00, "Entrega de dinero", 1);
=======
            EntregaDinero entregaDinero = new EntregaDinero(1, 1, Date.valueOf("2023-06-08"), 900.00, "Entrega de dinero", 1);
>>>>>>> 558abc28caf34bf68abf3de7feab646c91af29a9
            EntregaDineroDAO entregaDineroDAO = new EntregaDineroDAO(conn);
            
            try{
                entregaDineroDAO.insertar(entregaDinero);
                System.out.println("Entrega de dinero insertada correctamente.");

            } catch (SQLException e) {
                System.out.println("Error al insertar la entrega de dinero: " + e.getMessage());
            } finally {
                try {
                    // Cerramos la conexi�n
                    conn.close();
                    System.out.println("Conexi�n cerrada.");
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexi�n: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Fallo en la conexi�n.");
        }
        
    }
}