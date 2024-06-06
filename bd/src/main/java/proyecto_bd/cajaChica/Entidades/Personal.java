package proyecto_bd.cajaChica.Entidades;

public class Personal {
    private int idPersonal;
    private String nombrePersonal;
    private String cargoPersonal;
    private Double salario;
    public Personal(int idPersonal, String nombrePersonal, String cargoPersonal, Double salario) {
        this.idPersonal = idPersonal;
        this.nombrePersonal = nombrePersonal;
        this.cargoPersonal = cargoPersonal;
        this.salario = salario;
    }
    public int getIdPersonal() {
        return idPersonal;
    }
    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }
    public String getNombrePersonal() {
        return nombrePersonal;
    }
    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }
    public String getCargoPersonal() {
        return cargoPersonal;
    }
    public void setCargoPersonal(String cargoPersonal) {
        this.cargoPersonal = cargoPersonal;
    }
    public Double getSalario() {
        return salario;
    }
    public void setSalario(Double salario) {
        this.salario = salario;
    }  
}
