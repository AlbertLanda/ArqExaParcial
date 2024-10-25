/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.DAO;

import com.mycompany.gestionacademica.Conexion.ConexionBD;
import com.mycompany.gestionacademica.Modelo.Curso;
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
public class CursoDAO {

    private final Connection conexion;

    public CursoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int agregarCurso(Curso curso) {
        String sql = "INSERT INTO CURSO (nombreCurso, creditos, docente) VALUES (?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, curso.getNombreCurso());
            stmt.setInt(2, curso.getCreditos());
            stmt.setString(3, curso.getDocente());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

            System.out.println("Curso agregado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar curso: " + e.getMessage());
        }
        return generatedId;
    }

    public Curso obtenerCursoPorId(int id) {
        Curso curso = null;
        String sql = "SELECT * FROM CURSO WHERE idCurso = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    curso = new Curso(
                            rs.getInt("idCurso"),
                            rs.getString("nombreCurso"),
                            rs.getInt("creditos"),
                            rs.getString("docente")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener curso: " + e.getMessage());
        }

        return curso;
    }

    public void actualizarCursoPorNombre(String nombreActual, Curso cursoActualizado) {
        String sql = "UPDATE CURSO SET nombreCurso = ?, creditos = ?, docente = ? WHERE nombreCurso = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cursoActualizado.getNombreCurso());
            stmt.setInt(2, cursoActualizado.getCreditos());
            stmt.setString(3, cursoActualizado.getDocente());
            stmt.setString(4, nombreActual);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Curso actualizado exitosamente.");
            } else {
                System.out.println("No se encontró ningún curso con el nombre especificado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar curso: " + e.getMessage());
        }
    }

    public int obtenerIdCursoPorNombre(String nombreCurso) {
        String sql = "SELECT idCurso FROM CURSO WHERE nombreCurso = ?";
        int idCurso = -1; // Valor por defecto en caso de no encontrar

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreCurso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idCurso = rs.getInt("idCurso");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ID del curso: " + e.getMessage());
        }

        return idCurso; // Retorna -1 si no se encuentra
    }

    /*

    public List<Curso> obtenerTodosLosCursos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM CURSO";

        try (Connection conexion = conexionBD.conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("idCurso"),
                        rs.getString("nombreCurso"),
                        rs.getInt("creditos"),
                        rs.getString("docente")
                );
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cursos: " + e.getMessage());
        }

        return cursos;
    }

    public void actualizarCurso(Curso curso) {
        String sql = "UPDATE CURSO SET nombreCurso = ?, creditos = ?, docente = ? WHERE idcurso = ?";

        try (Connection conexion = conexionBD.conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, curso.getNombreCurso());
            stmt.setInt(2, curso.getCreditos());
            stmt.setString(3, curso.getDocente());
            stmt.setInt(4, curso.getIdCurso());
            stmt.executeUpdate();
            System.out.println("Curso actualizado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar curso: " + e.getMessage());
        }
    }

    public void eliminarCurso(int idCurso) {
        String sql = "DELETE FROM CURSO WHERE idCurso = ?";

        try (Connection conexion = conexionBD.conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idCurso);
            stmt.executeUpdate();
            System.out.println("Curso eliminado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar curso: " + e.getMessage());
        }
    }
     */
}
