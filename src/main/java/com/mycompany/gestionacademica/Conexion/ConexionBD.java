/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class ConexionBD {
    public Connection conectar() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=gestion_academica;encrypt=false;trustServerCertificate=true";
        String usuario = "sa";
        String contraseña = "12345";

        Connection conexion = null;

         try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Conectar a la base de datos
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de SQL Server");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        return conexion;
    }   
}
