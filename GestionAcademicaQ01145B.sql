CREATE DATABASE gestion_academica;
USE gestion_academica;
go

CREATE TABLE ESTUDIANTE (
    idEstudiante INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    email VARCHAR(100) NOT NULL,
	UNIQUE (email)
);
go

CREATE TABLE CURSO (
    idCurso INT IDENTITY(1,1) PRIMARY KEY,
    nombreCurso VARCHAR(100) NOT NULL,
    creditos INT NOT NULL,
    docente VARCHAR(100) NOT NULL
);
go

CREATE TABLE NOTA (
    idNota INT IDENTITY(1,1) PRIMARY KEY,
    idEstudiante INT NOT NULL,
    idCurso INT NOT NULL,
    nota DECIMAL(5,2) NOT NULL,
    fechaRegistro DATE NOT NULL,
    
    FOREIGN KEY (idEstudiante) REFERENCES Estudiante(idEstudiante)
    ON DELETE CASCADE,
    
    FOREIGN KEY (idCurso) REFERENCES Curso(idCurso)
    ON DELETE CASCADE
);
go