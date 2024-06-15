
package proyecto_bd.cajaChica.Dao;

import proyecto_bd.cajaChica.Entidades.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadoDAO {
    private Connection connection;

    public EstadoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Estado> obtenerTodosLosEstados() throws SQLException {
        List<Estado> estados = new ArrayList<>();
        String query = "SELECT * FROM estados";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Estado estado = new Estado(rs.getInt("id"), rs.getString("descripcion"));
                estados.add(estado);
            }
        }
        return estados;
    }

    public Estado obtenerEstadoPorId(int id) throws SQLException {
        Estado estado = null;
        String query = "SELECT * FROM estados WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estado = new Estado(rs.getInt("id"), rs.getString("descripcion"));
                }
            }
        }
        return estado;
    }

    public void agregarEstado(Estado estado) throws SQLException {
        String query = "INSERT INTO estados (descripcion) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, estado.getDescripcion());
            stmt.executeUpdate();
        }
    }

    public void actualizarEstado(Estado estado) throws SQLException {
        String query = "UPDATE estados SET descripcion = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, estado.getDescripcion());
            stmt.setInt(2, estado.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarEstado(int id) throws SQLException {
        String query = "DELETE FROM estados WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

