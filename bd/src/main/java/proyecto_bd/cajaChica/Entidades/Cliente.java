package proyecto_bd.cajaChica.Entidades;

public class Cliente {
    int idCliente;
    int idUsuario;

    public Cliente(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    
}
