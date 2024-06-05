package proyecto_bd.cajaChica.Entidades;

public class Cajero {
    private int idCajero;
    private String nombreCajero;
    private String cargoCajero;
    private String turno;
    private String fechaIngreso;
    private String fechaSalida;
    private String estado;
    public Cajero(int idCajero, String nombreCajero, String cargoCajero, String turno, String fechaIngreso, String fechaSalida, String estado) {
        this.idCajero = idCajero;
        this.nombreCajero = nombreCajero;
        this.cargoCajero = cargoCajero;
        this.turno = turno;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
    }
    public int getIdCajero() {
        return idCajero;
    }
    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }
    public String getNombreCajero() {    
        return nombreCajero;
    }                       
    public void setNombreCajero(String nombreCajero) {
        this.nombreCajero = nombreCajero;
    }   
    public String getCargoCajero() {
        return cargoCajero;
    }
    public void setCargoCajero(String cargoCajero) {
        this.cargoCajero = cargoCajero;
    }
    public String getTurno() {
        return turno;
    }
    public void setTurno(String turno) {
        this.turno = turno;
    }
    public String getFechaIngreso() {
        return fechaIngreso;
    }
    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public String getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;       
    }
}
