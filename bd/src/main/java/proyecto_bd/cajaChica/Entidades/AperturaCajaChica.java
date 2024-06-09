package proyecto_bd.cajaChica.Entidades;

import java.sql.Date;

public class AperturaCajaChica {
    int idApertura;
    Date fecha;
    double montoInicial;
    String estado;
    int idCajero;

    public AperturaCajaChica(Date fecha, double montoInicial, String estado, int idCajero) {
        this.fecha = fecha;
        this.montoInicial = montoInicial;
        this.estado = estado;
        this.idCajero = idCajero;
    }

    public int getIdApertura() {
        return idApertura;
    }

    public void setIdApertura(int idApertura) {
        this.idApertura = idApertura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(double montoInicial) {
        this.montoInicial = montoInicial;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
