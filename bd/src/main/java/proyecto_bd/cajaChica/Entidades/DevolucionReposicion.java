package proyecto_bd.cajaChica.Entidades;

public class DevolucionReposicion {
    private int idDevolucion;
    private String motivo;
    private Double cantidad;
    private int rendicionDocumentos;
    public DevolucionReposicion(int idDevolucion, String motivo, Double cantidad, int rendicionDocumentos) {
        this.idDevolucion = idDevolucion;
        this.motivo = motivo;
        this.cantidad = cantidad;
        this.rendicionDocumentos = rendicionDocumentos;
    }
    public int getIdDevolucion() {
        return idDevolucion;
    }
    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public Double getCantidad() {
        return cantidad;
    }
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }
    public int getRendicionDocumentos() {
        return rendicionDocumentos;
    }
    public void setRendicionDocumentos(int rendicionDocumentos) {
        this.rendicionDocumentos = rendicionDocumentos;
    }
}
