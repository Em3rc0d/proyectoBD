package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import proyecto_bd.cajaChica.Entidades.RendicionDocumento;

public class RendicionDocumentoDAO {
    private Connection connection;

    public RendicionDocumentoDAO(Connection connection) {
        this.connection = connection;
    }

    public void insertar(RendicionDocumento rendicionDocumento) {
        try {
            String sql = "INSERT INTO RendicionDocumento (idCajero, fechaRendicion, montoRendido, porcentajeNoSustentado, idCliente) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql); 
            pst.setInt(1, rendicionDocumento.getIdCajero());
            pst.setDate(2, rendicionDocumento.getFechaRendicion());
            pst.setDouble(3, rendicionDocumento.getMontoRendido());
            pst.setDouble(4, rendicionDocumento.getPorcentajeNoSustentado());
            pst.setInt(5, rendicionDocumento.getIdCliente());
            pst.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(RendicionDocumento rendicionDocumento) {
        try {
            String sql = "UPDATE RendicionDocumento SET idCajero = ?, fechaRendicion = ?, montoRendido = ?, porcentajeNoSustentado = ?, idCliente = ? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, rendicionDocumento.getIdCajero());
            pst.setDate(2, rendicionDocumento.getFechaRendicion());
            pst.setDouble(3, rendicionDocumento.getMontoRendido());
            pst.setDouble(4, rendicionDocumento.getPorcentajeNoSustentado());
            pst.setInt(5, rendicionDocumento.getIdCliente());
            pst.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int idRendicion) {
        try {
            String sql = "DELETE FROM RendicionDocumento WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, idRendicion);
            pst.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RendicionDocumento> obtenerRendiciones() {
        return null;
    }

    public RendicionDocumento obtenerRendicion(int idRendicion) {
        return null;
    } 
}
