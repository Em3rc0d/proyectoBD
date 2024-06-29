package proyecto_bd.cajaChica.Entidades;

public class Cajero {
    int idCajero;
    int idUsuario;
    public Cajero(int idUsuario) {
        this.idCajero = idCajero;
        this.idUsuario = idUsuario;
    }
    public int getIdCajero() {
        return idCajero;
    }
    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}