package proyecto_bd.cajaChica.Entidades;

public class EntregaDinero {
    private int idEntregaDinero;
    private int idCaja;
    private int idBanco;
    private Double montoEntregado;
    private Double montoCobrado;
    private String fechaEntrega;
    public EntregaDinero(int idEntregaDinero, int idCaja, int idBanco, Double montoEntregado, Double montoCobrado,
            String fechaEntrega) {
        this.idEntregaDinero = idEntregaDinero;
        this.idCaja = idCaja;
        this.idBanco = idBanco;
        this.montoEntregado = montoEntregado;
        this.montoCobrado = montoCobrado;
        this.fechaEntrega = fechaEntrega;
    }
    public int getIdEntregaDinero() {
        return idEntregaDinero;
    }
    public void setIdEntregaDinero(int idEntregaDinero) {
        this.idEntregaDinero = idEntregaDinero;
    }
    public int getIdCaja() {
        return idCaja;
    }
    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }
    public int getIdBanco() {
        return idBanco;
    }
    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }
    public Double getMontoEntregado() {
        return montoEntregado;
    }
    public void setMontoEntregado(Double montoEntregado) {
        this.montoEntregado = montoEntregado;
    }
    public Double getMontoCobrado() {
        return montoCobrado;
    }
    public void setMontoCobrado(Double montoCobrado) {
        this.montoCobrado = montoCobrado;
    }
    public String getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
