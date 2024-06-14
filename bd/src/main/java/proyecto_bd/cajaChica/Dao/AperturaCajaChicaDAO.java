package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto_bd.cajaChica.Entidades.AperturaCajaChica;

public class AperturaCajaChicaDAO {
    private Connection connection;

    public AperturaCajaChicaDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(AperturaCajaChica aperturaCajaChica) {
        try {
            String sql = "INSERT INTO AperturaCajaChica (fecha, montoInicial, estado, idCajero) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql); 
            pst.setDate(1, aperturaCajaChica.getFecha());
            pst.setDouble(2, aperturaCajaChica.getMontoInicial());
            pst.setString(3, aperturaCajaChica.getEstado());
            pst.setInt(4, aperturaCajaChica.getIdCajero());
            pst.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(AperturaCajaChica aperturaCajaChica) {
        try {   
            String sql = "UPDATE AperturaCajaChica SET fecha = ?, montoInicial = ?, estado = ?, idCajero = ? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setDate(1, aperturaCajaChica.getFecha());
            pst.setDouble(2, aperturaCajaChica.getMontoInicial());
            pst.setString(3, aperturaCajaChica.getEstado());
            pst.setInt(4, aperturaCajaChica.getIdCajero());
            pst.setInt(5, aperturaCajaChica.getIdApertura());
            pst.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idApertura) {
        try {
            String sql = "DELETE FROM AperturaCajaChica WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, idApertura);
            pst.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<AperturaCajaChica> obtenerAperturas() {
        try {
            String sql = "SELECT * FROM AperturaCajaChica";
            PreparedStatement pst = connection.prepareStatement(sql);  
            ResultSet rs = pst.executeQuery();
            List<AperturaCajaChica> aperturas = new ArrayList<>();
            while (rs.next()) {
                AperturaCajaChica aperturaCajaChica = new AperturaCajaChica(rs.getDate("fecha"), rs.getDouble("montoInicial"), rs.getString("estado"), rs.getInt("idCajero"));
                aperturaCajaChica.setIdApertura(rs.getInt("id"));
                aperturas.add(aperturaCajaChica);
            }
            return aperturas;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AperturaCajaChica obtenerApertura(int idApertura) {  
        try {
            String sql = "SELECT * FROM AperturaCajaChica WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, idApertura);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                AperturaCajaChica aperturaCajaChica = new AperturaCajaChica(rs.getDate("fecha"), rs.getDouble("montoicial"), rs.getString("estado"), rs.getInt("idCajero"));
                aperturaCajaChica.setIdApertura(rs.getInt("id"));
                return aperturaCajaChica;
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    } 
}
