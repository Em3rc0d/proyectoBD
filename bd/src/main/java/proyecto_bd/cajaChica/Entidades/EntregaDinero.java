package proyecto_bd.cajaChica.Entidades;

import java.sql.Date;

public class EntregaDinero {
    int idEntregaDinero;
    int idCajero;
    int idCliente;
    Date fechaEntrega;
    double monto;
    String motivo;
    int idEstado;
    public EntregaDinero(int idCajero, int idCliente, Date fechaEntrega, double monto,
            String motivo, int idEstado) {
        this.idEntregaDinero = idEntregaDinero;
        this.idCajero = idCajero;
        this.idCliente = idCliente;
        this.fechaEntrega = fechaEntrega;
        this.monto = monto;
        this.motivo = motivo;
        this.idEstado = idEstado;
    }
    public int getIdEntregaDinero() {
        return idEntregaDinero;
    }
    public void setIdEntregaDinero(int idEntregaDinero) {
        this.idEntregaDinero = idEntregaDinero;
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
    public Date getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public int getIdEstado() {
        return idEstado;
    }
    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    
}
// CREATE TABLE EntregaDinero (
//   idEntregaDinero INT PRIMARY KEY IDENTITY(1, 1),
//   idCajero INT,
//   idCliente INT,
//   fechaEntrega DATE,
//   monto DECIMAL(10,2),
//   motivo VARCHAR(255),
//   idEstado INT,
//   FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero),
//   FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
// );