package proyecto_bd.cajaChica.Entidades;

import java.sql.Date;

public class CajaBanco {
    private int idCajaBanco;
    private Date fecha;
    private float monto;
    private int idCajero;

    public CajaBanco(Date fecha, float monto, int idCajero) {
        this.fecha = fecha;
        this.monto = monto;
        this.idCajero = idCajero;
    }

    public int getIdCajaBanco() {
        return idCajaBanco;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public void setIdCajaBanco(int idCajaBanco) {
        this.idCajaBanco = idCajaBanco;
    }

}
