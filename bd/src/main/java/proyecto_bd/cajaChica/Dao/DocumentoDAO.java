package proyecto_bd.cajaChica.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import proyecto_bd.cajaChica.Entidades.Documento;

public class DocumentoDAO {
    private Connection conexion;

    public DocumentoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertar(Documento documento) throws SQLException {
        String sql = "INSERT INTO Documento (idRendicionDocumento, descripcion) VALUES (?, ?)";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, documento.getIdRendicionDocumento());
            pst.setString(2, documento.getDescripcion());
            pst.executeUpdate();
        }
    }

    public void actualizar(Documento documento) throws SQLException {
        String sql = "UPDATE Documento SET idRendicionDocumento = ?, descripcion = ? WHERE idDocumento = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, documento.getIdRendicionDocumento());
            pst.setString(2, documento.getDescripcion());
            pst.setInt(3, documento.getIdDocumento());
            pst.executeUpdate();
        }
    }

    public void eliminar(int idDocumento) throws SQLException {
        String sql = "DELETE FROM Documento WHERE idDocumento = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idDocumento);
            pst.executeUpdate();
        }
    }

    public List<Documento> obtenerDocumentos() throws SQLException {
        String sql = "SELECT * FROM Documento";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            List<Documento> documentos = new ArrayList<>();
            while (rs.next()) {
                Documento documento = new Documento(
                        rs.getInt("idRendicionDocumento"),
                        rs.getString("descripcion")
                );
                //documento.setIdDocumento(rs.getInt("idDocumento")); no seguro de eso
                documentos.add(documento);
            }
            return documentos;
        }
    }

    public Documento obtenerDocumento(int idDocumento) throws SQLException {
        String sql = "SELECT * FROM Documento WHERE idDocumento = ?";
        try (PreparedStatement pst = this.conexion.prepareStatement(sql)) {
            pst.setInt(1, idDocumento);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Documento documento = new Documento(
                        rs.getInt("idRendicionDocumento"),
                        rs.getString("descripcion")
                    );
                    //documento.setIdDocumento(rs.getInt("idDocumento"));
                    return documento;
                }
                return null;
            }
        }
    }
}
