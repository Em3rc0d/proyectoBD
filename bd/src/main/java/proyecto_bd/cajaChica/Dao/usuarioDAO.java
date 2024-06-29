package proyecto_bd.cajaChica.Dao;

import proyecto_bd.cajaChica.Entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Usuario> obtenerTodosLosUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fechaIngreso")
                );
                usuario.setId(rs.getInt("idPersonal"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE idPersonal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getString("email"),
                            rs.getString("telefono"),
                            rs.getDate("fechaIngreso")
                    );
                    usuario.setId(rs.getInt("idPersonal"));
                }
            }
        }
        return usuario;
    }

    public void agregarUsuario(Usuario usuario) throws SQLException {
        String query = "INSERT INTO Usuario (nombre, apellido, email, telefono, fechaIngreso) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefono());
            stmt.setDate(5, usuario.getFechaIngreso());
            stmt.executeUpdate();
        }
    }

    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String query = "UPDATE Usuario SET nombre = ?, apellido = ?, email = ?, telefono = ?, fechaIngreso = ? WHERE idPersonal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefono());
            stmt.setDate(5, usuario.getFechaIngreso());
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminarUsuario(int id) throws SQLException {
        String query = "DELETE FROM Usuario WHERE idPersonal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Usuario> obtenerUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Usuario"; // Asegúrate de que la tabla sea la correcta
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("idPersonal")); // Cambiar a idPersonal
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
    public List<Usuario> obtenerUsuariosNoAsignadosCajero() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.idPersonal, u.nombre, u.apellido, u.email, u.telefono FROM Usuario u " +
                     "LEFT JOIN Cajero c ON u.idPersonal = c.idUsuario WHERE c.idUsuario IS NULL";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("idPersonal"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
    public List<Usuario> obtenerUsuariosNoAsignadosCliente() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT u.idPersonal, u.nombre, u.apellido, u.email, u.telefono FROM Usuario u " +
                     "LEFT JOIN Cliente c ON u.idPersonal = c.idUsuario WHERE c.idUsuario IS NULL";
        try (PreparedStatement pst = this.connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("idPersonal"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }
    
}
