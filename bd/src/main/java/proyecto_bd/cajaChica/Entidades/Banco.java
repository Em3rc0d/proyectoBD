package proyecto_bd.cajaChica.Entidades;
//NOELIMINAR, USAR DE EJEMPLO
public class Banco {
    private int id;
    private String nombre;
    private String numeroCuenta;
    private double saldo;

    public Banco(String nombre, String numeroCuenta, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Banco{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
