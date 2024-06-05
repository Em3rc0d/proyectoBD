/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto_bd.cajaChica.Paneles;

import java.sql.Connection;
import proyecto_bd.cajaChica.Conexion.ConexionBD;

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
        } else {
            System.out.println("Fallo en la conexión.");
        }
        
    }
}
