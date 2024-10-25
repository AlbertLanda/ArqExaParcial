/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.Modelo;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Nota {
    private int idNota;
    private int idEstudiante;
    private int idCurso;
    private double nota;
    private Date fechaRegistro;

    public Nota() {
    }

    public Nota(int idNota, int idEstudiante, int idCurso, double nota, Date fechaRegistro) {
        this.idNota = idNota;
        this.idEstudiante = idEstudiante;
        this.idCurso = idCurso;
        this.nota = nota;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Nota{" + "idNota=" + idNota + ", idEstudiante=" + idEstudiante + ", idCurso=" + idCurso + ", nota=" + nota + ", fechaRegistro=" + fechaRegistro + '}';
    }
}
