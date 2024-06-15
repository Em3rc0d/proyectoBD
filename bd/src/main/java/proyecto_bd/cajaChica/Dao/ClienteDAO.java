package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import proyecto_bd.cajaChica.Entidades.Cliente;

public class ClienteDAO {
    private Connection conexion;

    public ClienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (idUsuario) VALUES (?)";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, cliente.getIdUsuario());
            pst.executeUpdate();
        }
    }

    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE Cliente SET idUsuario = ? WHERE idCliente = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) { 
            pst.setInt(1, cliente.getIdUsuario());
            pst.setInt(2, cliente.getIdCliente());
            pst.executeUpdate();
        }
    }   

    public void eliminar(int idCliente) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE idCliente = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idCliente);
            pst.executeUpdate();
        }
    }   

    public List<Cliente> obtenerClientes() throws SQLException {
        String sql = "SELECT * FROM Cliente";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("idUsuario"));
                cliente.setIdCliente(rs.getInt("idCliente"));
                clientes.add(cliente);
            }
            return clientes;
        }
    }

    public Cliente obtenerCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idCliente);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("idUsuario"));
            } else {
                return null;
            }
        }
    }
}
