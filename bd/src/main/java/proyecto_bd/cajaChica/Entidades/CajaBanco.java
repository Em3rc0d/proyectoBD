package proyecto_bd.cajaChica.Entidades;

import java.sql.Date;

public class CajaBanco {
    private int idCajaBanco;
    private Date fecha;
    private double monto;
    private int idCajero;

    public CajaBanco(Date fecha, double monto, int idCajero) {
        this.fecha = fecha;
        this.monto = monto;
        this.idCajero = idCajero;
    }
    
    public CajaBanco(int idCajaBanco, Date fecha, double monto, int idCajero) {
        this.idCajaBanco = idCajaBanco;
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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
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
