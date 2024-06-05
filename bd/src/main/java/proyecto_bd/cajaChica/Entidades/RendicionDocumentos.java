package proyecto_bd.cajaChica.Entidades;

public class RendicionDocumentos {
    private int idRendicion;
    private String fechaRendicion;
    private String estado;
    private int totalDocumentos;
    private int idCaja;
    public RendicionDocumentos(int idRendicion, String fechaRendicion, String estado, int totalDocumentos, int idCaja) {
        this.idRendicion = idRendicion;
        this.fechaRendicion = fechaRendicion;
        this.estado = estado;
        this.totalDocumentos = totalDocumentos;
        this.idCaja = idCaja;
    }
    public int getIdRendicion() {
        return idRendicion;
    }
    public void setIdRendicion(int idRendicion) {
        this.idRendicion = idRendicion;
    }
    public String getFechaRendicion() {
        return fechaRendicion;
    }
    public void setFechaRendicion(String fechaRendicion) {
        this.fechaRendicion = fechaRendicion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getTotalDocumentos() {
        return totalDocumentos;
    }
    public void setTotalDocumentos(int totalDocumentos) {
        this.totalDocumentos = totalDocumentos;
    }
    public int getIdCaja() {
        return idCaja;
    }
    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }
    
}
