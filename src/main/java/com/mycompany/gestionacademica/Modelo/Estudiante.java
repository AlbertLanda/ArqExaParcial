/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.Modelo;

/**
 *
 * @author LENOVO
 */
public class Estudiante {
    private int idEstudiante;
    private String nombre;
    private int edad;
    private String email;
    
    public Estudiante() {
    }

    public Estudiante(int idEstudiante, String nombre, int edad, String email) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.edad = edad;
        this.email = email;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "idEstudiante=" + idEstudiante + ", nombre=" + nombre + ", edad=" + edad + ", email=" + email + '}';
    }
    
}
