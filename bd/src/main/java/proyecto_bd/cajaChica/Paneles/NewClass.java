/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_bd.cajaChica.Paneles;

import java.sql.Connection;
import java.sql.SQLException;

import proyecto_bd.cajaChica.Conexion.ConexionBD;
import proyecto_bd.cajaChica.Dao.BancoDAO;
import proyecto_bd.cajaChica.Entidades.Banco;

/**
 *
 * @author farid
 */
public class NewClass {
    public static void main(String[] args){
        ConexionBD conexionBD = new ConexionBD();
        Connection conn = conexionBD.establecerConexion();

        if (conn != null) {
            System.out.println("Conexión establecida con éxito.");

            // Creamos un objeto Banco
            Banco banco = new Banco("Banco de Prueba", "123456789", 10000.00);

            // Creamos un objeto BancoDAO
            BancoDAO bancoDAO = new BancoDAO(conn);

            try {
                // Insertamos el banco en la base de datos
                bancoDAO.insertar(banco);
                System.out.println("Banco insertado correctamente.");

                // Recuperamos todos los bancos de la base de datos y los mostramos
                System.out.println("Listado de bancos:");
                bancoDAO.obtenerBancos().forEach(System.out::println);

            } catch (SQLException e) {
                System.out.println("Error al insertar el banco: " + e.getMessage());
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
