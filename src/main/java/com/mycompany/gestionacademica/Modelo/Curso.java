/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.Modelo;

/**
 *
 * @author LENOVO
 */
public class Curso {
    private int idCurso;
    private String nombreCurso;
    private int creditos;
    private String docente;

    // Constructor vacío
    public Curso() {
    }

    public Curso(int idCurso, String nombreCurso, int creditos, String docente) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.creditos = creditos;
        this.docente = docente;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    @Override
    public String toString() {
        return "Curso{" + "idCurso=" + idCurso + ", nombreCurso=" + nombreCurso + ", creditos=" + creditos + ", docente=" + docente + '}';
    }
}
