package proyecto_bd.cajaChica.Entidades;

public class Documento {
    private int idDocumento;
    private int idRendicionDocumento;
    private String descripcion;

    public Documento(int idRendicionDocumento, String descripcion) {
        this.idRendicionDocumento = idRendicionDocumento;
        this.descripcion = descripcion;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public int getIdRendicionDocumento() {
        return idRendicionDocumento;
    }

    public void setIdRendicionDocumento(int idRendicionDocumento) {
        this.idRendicionDocumento = idRendicionDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

}
