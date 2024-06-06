package proyecto_bd.cajaChica.Entidades;

public class Caja {
    private int idCaja;
    private Double saldoCaja;
    public Caja(int idCaja, Double saldoCaja) {
        this.idCaja = idCaja;
        this.saldoCaja = saldoCaja;
    }
    public int getIdCaja() {
        return idCaja;
    }
    public void setIdCaja(int idCaja) {
        this.idCaja = idCaja;
    }
    public Double getSaldoCaja() {
        return saldoCaja;
    }
    public void setSaldoCaja(Double saldoCaja) {
        this.saldoCaja = saldoCaja;
    }
}
