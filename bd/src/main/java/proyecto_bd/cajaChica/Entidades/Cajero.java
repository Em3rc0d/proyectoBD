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
// CREATE TABLE Usuario (
//   idPersonal INT PRIMARY KEY IDENTITY(1, 1),
//   nombre VARCHAR(100),
//   apellido VARCHAR(100),
//   email VARCHAR(100),
//   telefono VARCHAR(20),
//   fechaIngreso DATE
// );

// CREATE TABLE Cajero (
//   idCajero INT PRIMARY KEY IDENTITY(1, 1),
//   idUsuario INT,
//   FOREIGN KEY (idUsuario) REFERENCES Usuario (idPersonal)
// );