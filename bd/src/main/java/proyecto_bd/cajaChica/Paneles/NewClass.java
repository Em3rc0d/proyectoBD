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
            System.out.println("Conexión establecida con éxito.");

            EntregaDinero entregaDinero = new EntregaDinero(1, 1, Date.valueOf("2023-06-08"), 900.00, "Entrega de dinero", 1);
            EntregaDineroDAO entregaDineroDAO = new EntregaDineroDAO(conn);
            
            try{
                entregaDineroDAO.insertar(entregaDinero);
                System.out.println("Entrega de dinero insertada correctamente.");

            } catch (SQLException e) {
                System.out.println("Error al insertar la entrega de dinero: " + e.getMessage());
            } finally {
                try {
                    // Cerramos la conexión
                    conn.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Fallo en la conexión.");
        }
        
    }
}