package proyecto_bd.cajaChica.Dao;

import proyecto_bd.cajaChica.Entidades.usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class usuarioDAO {

    private Connection connection;

    public usuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public List<usuario> obtenerTodosLosUsuarios() throws SQLException {
        List<usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuario usuario = new usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public usuario obtenerUsuarioPorId(int id) throws SQLException {
        usuario usuario = null;
        String query = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        }
        return usuario;
    }

    public void agregarUsuario(usuario usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, apellido, email, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.executeUpdate();
        }
    }

    public void actualizarUsuario(usuario usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, apellido = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setInt(5, usuario.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarUsuario(int id) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
