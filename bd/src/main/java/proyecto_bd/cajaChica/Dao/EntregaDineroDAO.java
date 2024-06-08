package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import proyecto_bd.cajaChica.Entidades.EntregaDinero;

public class EntregaDineroDAO {
    private Connection conexion;

    public EntregaDineroDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(EntregaDinero entregaDinero) throws SQLException {
        String sql = "INSERT INTO EntregaDinero (idCajero, idCliente, fechaEntrega, monto, motivo, idEstado) VALUES (?, ?, ?,?,?,?)";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, entregaDinero.getIdCajero());
            pst.setInt(2, entregaDinero.getIdCliente());
            pst.setDate(3, entregaDinero.getFechaEntrega());
            pst.setDouble(4, entregaDinero.getMonto());
            pst.setString(5, entregaDinero.getMotivo());
            pst.setInt(6, entregaDinero.getIdEstado());
            pst.executeUpdate();
        }
    }
}
