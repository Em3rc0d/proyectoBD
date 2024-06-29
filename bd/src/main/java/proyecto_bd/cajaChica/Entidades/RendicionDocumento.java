package proyecto_bd.cajaChica.Entidades;

import java.sql.Date;

public class RendicionDocumento {
    int idRendicionDocumento;
    int idCajero;
    Date fechaRendicion;
    double montoRendido;
    double porcentajeNoSustentado;
    int idCliente;



    public RendicionDocumento(int idCajero, Date fechaRendicion, double montoRendido, double porcentajeNoSustentado, int idCliente) {
        this.idCajero = idCajero;
        this.fechaRendicion = fechaRendicion;
        this.montoRendido = montoRendido;
        this.porcentajeNoSustentado = porcentajeNoSustentado;
        this.idCliente = idCliente;
    }

    public int getIdRendicionDocumento() {
        return idRendicionDocumento;
    }

    public void setIdRendicionDocumento(int idRendicionDocumento) {
        this.idRendicionDocumento = idRendicionDocumento;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaRendicion() {
        return fechaRendicion;
    }

    public void setFechaRendicion(Date fechaRendicion) {
        this.fechaRendicion = fechaRendicion;
    }

    public double getMontoRendido() {
        return montoRendido;
    }

    public void setMontoRendido(double montoRendido) {
        this.montoRendido = montoRendido;
    }

    public double getPorcentajeNoSustentado() {
        return porcentajeNoSustentado;
    }

    public void setPorcentajeNoSustentado(double porcentajeNoSustentado) {
        this.porcentajeNoSustentado = porcentajeNoSustentado;
    }

    @Override
    public String toString() {
        return "RendicionDocumento{" +
                "idRendicionDocumento=" + idRendicionDocumento +
                ", idCajero=" + idCajero +
                ", fechaRendicion=" + fechaRendicion +
                ", montoRendido=" + montoRendido +
                ", porcentajeNoSustentado=" + porcentajeNoSustentado +
                ", idCliente=" + idCliente +
                '}';
    }
}
