package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import proyecto_bd.cajaChica.Entidades.Banco;
//NOELIMINAR, USAR DE EJEMPLO
public class BancoDAO {
    private Connection conexion;

    public BancoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Banco banco) throws SQLException {
        String sql = "INSERT INTO Banco (nombre, numero_cuenta, saldo) VALUES (?, ?, ?)";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setString(1, banco.getNombre());
            pst.setString(2, banco.getNumeroCuenta());
            pst.setDouble(3, banco.getSaldo());
            pst.executeUpdate();
        }
    }

    public void actualizar(Banco banco) throws SQLException {
        String sql = "UPDATE Banco SET nombre = ?, numero_cuenta = ?, saldo = ? WHERE id = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setString(1, banco.getNombre());
            pst.setString(2, banco.getNumeroCuenta());
            pst.setDouble(3, banco.getSaldo());
            pst.setInt(4, banco.getId());
            pst.executeUpdate();
        }
    }

    public void eliminar(int idBanco) throws SQLException {
        String sql = "DELETE FROM Banco WHERE id = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idBanco);
            pst.executeUpdate();
        }
    }

    public List<Banco> obtenerBancos() throws SQLException {
        String sql = "SELECT * FROM Banco";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<Banco> bancos = new ArrayList<>();
            while (rs.next()) {
                Banco banco = new Banco(rs.getString("nombre"), rs.getString("numero_cuenta"), rs.getDouble("saldo"));
                bancos.add(banco);
            }
            return bancos;
        }
    }

    public Banco obtenerBanco(int idBanco) throws SQLException {
        String sql = "SELECT * FROM Banco WHERE id = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idBanco);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Banco(rs.getString("nombre"), rs.getString("numero_cuenta"), rs.getDouble("saldo"));
                }
                return null;
            }
        }
    }
}
