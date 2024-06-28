package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto_bd.cajaChica.Entidades.Cajero;

public class CajeroDAO {
    private Connection conexion;

    public CajeroDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Cajero cajero) throws SQLException {
        String sql = "INSERT INTO Cajero (idUsuario) VALUES (?)";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, cajero.getIdUsuario());
            pst.executeUpdate();
        }
    }

    public void actualizar(Cajero cajero) throws SQLException {
        String sql = "UPDATE Cajero SET idUsuario = ? WHERE idCajero = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) { 
            pst.setInt(1, cajero.getIdUsuario());
            pst.setInt(2, cajero.getIdCajero());
            pst.executeUpdate();
        }
    }   

    public void eliminar(int idCajero) throws SQLException {
        String sql = "DELETE FROM Cajero WHERE idCajero = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idCajero);
            pst.executeUpdate();
        }
    }   

    public List<Cajero> obtenerCajeros() throws SQLException {
        String sql = "SELECT * FROM Cajero";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<Cajero> cajeros = new ArrayList<>();
            while (rs.next()) {
                Cajero cajero = new Cajero(rs.getInt("idUsuario"));
                cajero.setIdCajero(rs.getInt("IdCajero"));
                cajeros.add(cajero);
            }
            return cajeros;
        }
    }

    public Cajero obtenerCajero(int idCajero) throws SQLException {
        String sql = "SELECT * FROM Cajero WHERE idCajero = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idCajero);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Cajero(rs.getInt("idUsuario"));
            } else {
                return null;
            }
        }
    }
}
