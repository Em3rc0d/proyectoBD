package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import proyecto_bd.cajaChica.Entidades.CajaBanco;

public class CajaBancoDAO {
    private Connection conexion;

    public CajaBancoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(CajaBanco cajaBanco) throws SQLException {
        String sql = "INSERT INTO CajaBanco (fecha, monto, idCajero) VALUES (?, ?, ?)";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setDate(1, cajaBanco.getFecha());
            pst.setDouble(2, cajaBanco.getMonto());
            pst.setInt(3, cajaBanco.getIdCajero());
            pst.executeUpdate();
        }
    }

    public void actualizar(CajaBanco cajaBanco) throws SQLException {
        String sql = "UPDATE CajaBanco SET fecha = ?, monto = ?, idCajero = ? WHERE idCajaBanco = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setDate(1, cajaBanco.getFecha());
            pst.setDouble(2, cajaBanco.getMonto());
            pst.setInt(3, cajaBanco.getIdCajero());
            pst.setInt(4, cajaBanco.getIdCajaBanco());
            pst.executeUpdate();
        }
    }

    public void eliminar(Long idCajaBanco) throws SQLException {
        String sql = "DELETE FROM CajaBanco WHERE idCajaBanco = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setLong(1, idCajaBanco);
            pst.executeUpdate();
        }
    }

    public List<CajaBanco> obtenerCajaBancos() throws SQLException {
        String sql = "SELECT * FROM CajaBanco";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<CajaBanco> cajaBancos = new ArrayList<>();
            while (rs.next()) {
                CajaBanco cajaBanco = new CajaBanco(
                        rs.getDate("fecha"),
                        rs.getFloat("monto"),
                        rs.getInt("idCajero")
                );
                cajaBancos.add(cajaBanco);
            }
            return cajaBancos;
        }
    }

    public CajaBanco obtenerCajaBanco(Long idCajaBanco) throws SQLException {
        String sql = "SELECT * FROM CajaBanco WHERE idCajaBanco = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setLong(1, idCajaBanco);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new CajaBanco(
                        rs.getDate("fecha"),
                        rs.getFloat("monto"),
                        rs.getInt("idCajero")
                    );
                }
                return null;
            }
        }
    }
}
