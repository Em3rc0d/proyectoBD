package proyecto_bd.cajaChica.Entidades;

public class Banco {
    private int idBanco;
    private String nombreBanco;
    private String direccionBanco;
    public Banco(int idBanco, String nombreBanco, String direccionBanco) {
        this.idBanco = idBanco;
        this.nombreBanco = nombreBanco;
        this.direccionBanco = direccionBanco;
    }
    public int getIdBanco() {
        return idBanco;
    }
    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }
    public String getNombreBanco() {
        return nombreBanco;
    }
    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }
    public String getDireccionBanco() {
        return direccionBanco;
    }
    public void setDireccionBanco(String direccionBanco) {
        this.direccionBanco = direccionBanco;
    }

    
}
