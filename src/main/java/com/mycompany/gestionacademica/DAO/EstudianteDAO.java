/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.DAO;

import com.mycompany.gestionacademica.Conexion.ConexionBD;
import com.mycompany.gestionacademica.Modelo.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class EstudianteDAO {

    private final Connection conexion;

    public EstudianteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int agregarEstudiante(Estudiante estudiante) {
        String sql = "INSERT INTO ESTUDIANTE (nombre, edad, email) VALUES (?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) { // Agregar RETURN_GENERATED_KEYS
            stmt.setString(1, estudiante.getNombre());
            stmt.setInt(2, estudiante.getEdad());
            stmt.setString(3, estudiante.getEmail());
            stmt.executeUpdate(); // Ejecutar la inserción

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Obtener el primer ID generado
                }
            }

            System.out.println("Estudiante agregado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar estudiante: " + e.getMessage());
        }
        return generatedId;
    }

    public Estudiante obtenerEstudiantePorId(int id) {
        Estudiante estudiante = null;
        String sql = "SELECT * FROM ESTUDIANTE WHERE idEstudiante = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    estudiante = new Estudiante(
                            rs.getInt("idEstudiante"),
                            rs.getString("nombre"),
                            rs.getInt("edad"),
                            rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener estudiante: " + e.getMessage());
        }

        return estudiante;
    }

    public void actualizarEstudiantePorNombre(String nombreActual, Estudiante actuEstudiante) {
        String sql = "UPDATE ESTUDIANTE SET nombre = ?, edad = ?, email = ? WHERE nombre = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, actuEstudiante.getNombre());
            stmt.setInt(2, actuEstudiante.getEdad());
            stmt.setString(3, actuEstudiante.getEmail());
            stmt.setString(4, nombreActual);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Estudiante actualizado exitosamente.");
            } else {
                System.out.println("No se encontró ningún estudiante con el nombre especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar estudiante: " + e.getMessage());
        }
    }

    public int obtenerIdEstudiantePorNombre(String nombreEstudiante) {
        String sql = "SELECT idEstudiante FROM ESTUDIANTE WHERE nombre = ?";
        int idEstudiante = -1; // Valor por defecto en caso de no encontrar

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreEstudiante);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idEstudiante = rs.getInt("idEstudiante");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ID del estudiante: " + e.getMessage());
        }

        return idEstudiante; // Retorna -1 si no se encuentra
    }

    public void eliminarEstudiante(String nombre) {
        String sql = "DELETE FROM ESTUDIANTE WHERE nombre = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Estudiante eliminado con éxito.");
            } else {
                System.out.println("No se encontró ningún estudiante con el ID especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
        }
    }
}
