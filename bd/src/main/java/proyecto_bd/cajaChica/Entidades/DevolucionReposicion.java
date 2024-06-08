package proyecto_bd.cajaChica.Entidades;

import java.sql.Date;

public class DevolucionReposicion {
    int idDevReposicion;
    Date fechaDevolucion;
    double montoDevolucion;
    String motivo;
    String tipoOperacion;
    int idCajero;
    int idCliente;
    public DevolucionReposicion(Date fechaDevolucion, double montoDevolucion, String motivo,
            String tipoOperacion, int idCajero, int idCliente) {
        this.idDevReposicion = idDevReposicion;
        this.fechaDevolucion = fechaDevolucion;
        this.montoDevolucion = montoDevolucion;
        this.motivo = motivo;
        this.tipoOperacion = tipoOperacion;
        this.idCajero = idCajero;
        this.idCliente = idCliente;
    }
    public int getIdDevReposicion() {
        return idDevReposicion;
    }
    public void setIdDevReposicion(int idDevReposicion) {
        this.idDevReposicion = idDevReposicion;
    }
    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }
    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    public double getMontoDevolucion() {
        return montoDevolucion;
    }
    public void setMontoDevolucion(double montoDevolucion) {
        this.montoDevolucion = montoDevolucion;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public String getTipoOperacion() {
        return tipoOperacion;
    }
    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
    public int getIdCajero() {
        return idCajero;
    }
    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
}
// CREATE TABLE DevolucionReposicion (
//   idDevReposicion INT PRIMARY KEY IDENTITY(1, 1),
//   fechaDevolucion DATE,
//   montoDevolucion DECIMAL(10,2),
//   motivo VARCHAR(255),
//   tipoOperacion VARCHAR(50),
//   idCajero INT,
//   idCliente INT,
//   FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero),
//   FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
// );
