package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto_bd.cajaChica.Entidades.DevolucionReposicion;

public class DevolucionReposicionDAO {
    private Connection conexion;

    public DevolucionReposicionDAO(Connection conexion) {
        this.conexion = conexion;
    }
    
    public void insertar(DevolucionReposicion devolucionReposicion) throws SQLException {
        String sql = "INSERT INTO DevolucionReposicion (fechaDevolucion, montoDevolucion, motivo, tipoOperacion, idCajero, idCliente) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setDate(1, devolucionReposicion.getFechaDevolucion());
            pst.setDouble(2, devolucionReposicion.getMontoDevolucion());
            pst.setString(3, devolucionReposicion.getMotivo());
            pst.setString(4, devolucionReposicion.getTipoOperacion());
            pst.setInt(5, devolucionReposicion.getIdCajero());
            pst.setInt(6, devolucionReposicion.getIdCliente());
            pst.executeUpdate();
        }
    }

    public void actualizar(DevolucionReposicion devolucionReposicion) throws SQLException {
        String sql = "UPDATE DevolucionReposicion SET fechaDevolucion = ?, montoDevolucion = ?, motivo = ?, tipoOperacion = ?, idCajero = ?, idCliente = ? WHERE idDevReposicion = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setDate(1, devolucionReposicion.getFechaDevolucion());
            pst.setDouble(2, devolucionReposicion.getMontoDevolucion());
            pst.setString(3, devolucionReposicion.getMotivo());
            pst.setString(4, devolucionReposicion.getTipoOperacion());
            pst.setInt(5, devolucionReposicion.getIdCajero());
            pst.setInt(6, devolucionReposicion.getIdCliente());
            pst.setInt(7, devolucionReposicion.getIdDevReposicion());
            pst.executeUpdate();
        }
    }

    public void eliminar(int idDevReposicion) throws SQLException {
        String sql = "DELETE FROM DevolucionReposicion WHERE idDevReposicion = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idDevReposicion);
            pst.executeUpdate();
        }
    }
    public void eliminar(Date fechaDevolucion) throws SQLException {
        String sql = "DELETE FROM DevolucionReposicion WHERE fechaDevolucion = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setDate(1, fechaDevolucion);
            pst.executeUpdate();
        }
    }
    
    public List<DevolucionReposicion> obtenerDevolucionesReposicion() throws SQLException {
        String sql = "SELECT * FROM DevolucionReposicion";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<DevolucionReposicion> devolucionesReposicion = new ArrayList<>();
            while (rs.next()) {
                DevolucionReposicion devolucionReposicion = new DevolucionReposicion(rs.getInt("idDevReposicion"), rs.getDate("fechaDevolucion"),
                        rs.getDouble("montoDevolucion"), rs.getString("motivo"), rs.getString("tipoOperacion"),
                        rs.getInt("idCajero"), rs.getInt("idCliente"));
                devolucionesReposicion.add(devolucionReposicion);
            }
            return devolucionesReposicion;
        }
    }

    public DevolucionReposicion obtenerDevolucionReposicion(int idDevReposicion) throws SQLException {
        String sql = "SELECT * FROM DevolucionReposicion WHERE idDevReposicion = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idDevReposicion);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new DevolucionReposicion(rs.getDate("fechaDevolucion"),
                            rs.getDouble("montoDevolucion"), rs.getString("motivo"), rs.getString("tipoOperacion"),
                            rs.getInt("idCajero"), rs.getInt("idCliente"));
                }
                return null;
            }
        }
    }
}
