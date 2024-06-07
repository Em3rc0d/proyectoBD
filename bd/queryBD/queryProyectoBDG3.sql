CREATE TABLE Usuario (
  idPersonal INT PRIMARY KEY IDENTITY(1, 1),
  nombre VARCHAR(100),
  apellido VARCHAR(100),
  email VARCHAR(100),
  telefono VARCHAR(20),
  fechaIngreso DATE
);

CREATE TABLE Cajero (
  idCajero INT PRIMARY KEY IDENTITY(1, 1),
  idUsuario INT,
  FOREIGN KEY (idUsuario) REFERENCES Usuario (idPersonal)
);

CREATE TABLE Cliente (
  idCliente INT PRIMARY KEY IDENTITY(1, 1),
  idUsuario INT,
  FOREIGN KEY (idUsuario) REFERENCES Usuario (idPersonal)
);

CREATE TABLE CajaBanco (
  idCajaBanco INT PRIMARY KEY IDENTITY(1, 1),
  fecha DATE,
  monto DECIMAL(10,2),
  idCajero INT,
  FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero)
);

CREATE TABLE EntregaDinero (
  idEntregaDinero INT PRIMARY KEY IDENTITY(1, 1),
  idCajero INT,
  idCliente INT,
  fechaEntrega DATE,
  monto DECIMAL(10,2),
  motivo VARCHAR(255),
  idEstado INT,
  FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero),
  FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
);

CREATE TABLE RendicionDocumento (
  idRendDocumento INT PRIMARY KEY IDENTITY(1, 1),
  idCajero INT,
  fechaRendicion DATE,
  montoRendido DECIMAL(10,2),
  porcentajeNoSustentado DECIMAL(5,2),
  idCliente INT,
  FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero),
  FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
);

CREATE TABLE Documento (
  idDocumento INT PRIMARY KEY IDENTITY(1, 1),
  idRendicionDocumento INT,
  descripcion VARCHAR(255),
  FOREIGN KEY (idRendicionDocumento) REFERENCES RendicionDocumento (idRendDocumento)
);

CREATE TABLE DevolucionReposicion (
  idDevReposicion INT PRIMARY KEY IDENTITY(1, 1),
  fechaDevolucion DATE,
  montoDevolucion DECIMAL(10,2),
  motivo VARCHAR(255),
  tipoOperacion VARCHAR(50),
  idCajero INT,
  idCliente INT,
  FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero),
  FOREIGN KEY (idCliente) REFERENCES Cliente (idCliente)
);

CREATE TABLE Estado (
  idEstado INT PRIMARY KEY IDENTITY(1, 1),
  tipoEstado VARCHAR(50)
);

CREATE TABLE AperturaCajaChica (
  idApertura INT PRIMARY KEY IDENTITY(1, 1),
  fecha DATE,
  montoInicial DECIMAL(10,2),
  estado VARCHAR(50),
  idCajero INT,
  FOREIGN KEY (idCajero) REFERENCES Cajero (idCajero)
);
