/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.DAO;

import com.mycompany.gestionacademica.Conexion.ConexionBD;
import com.mycompany.gestionacademica.Modelo.Nota;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class NotaDAO {

    private final Connection conexion;

    public NotaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public int agregarNota(Nota nota) {
        String sql = "INSERT INTO NOTA (idEstudiante, idCurso, nota, fechaRegistro) VALUES (?, ?, ?, ?)";
        int generatedId = -1;

        try (PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, nota.getIdEstudiante());
            stmt.setInt(2, nota.getIdCurso());
            stmt.setDouble(3, nota.getNota());
            stmt.setDate(4, new java.sql.Date(nota.getFechaRegistro().getTime()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

            System.out.println("Nota agregada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar nota: " + e.getMessage());
        }
        return generatedId;
    }

    public List<Nota> obtenerNotas() {
        List<Nota> notas = new ArrayList<>();
        String sql = "SELECT * FROM NOTA";

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("idNota");
                int estudianteId = rs.getInt("idEstudiante");
                int cursoId = rs.getInt("idCurso");
                int notaObtenida = rs.getInt("nota");
                Date fecha = rs.getDate("fechaRegistro");

                Nota nota = new Nota(id, estudianteId, cursoId, notaObtenida, fecha);
                notas.add(nota);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener notas: " + e.getMessage());
        }

        return notas;
    }

    public void actualizarNotaPorNombre(String nombreEstudiante, String nombreCurso, Nota notaActualizada) {
        String sql = "UPDATE NOTA SET nota = ?, fechaRegistro = ? "
                + "WHERE idEstudiante = (SELECT idEstudiante FROM ESTUDIANTE WHERE nombre = ?) "
                + "AND idCurso = (SELECT idCurso FROM CURSO WHERE nombreCurso = ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDouble(1, notaActualizada.getNota());
            stmt.setDate(2, new java.sql.Date(notaActualizada.getFechaRegistro().getTime())); // Asume que getFecha() devuelve un objeto Date
            stmt.setString(3, nombreEstudiante);
            stmt.setString(4, nombreCurso);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Nota actualizada exitosamente.");
            } else {
                System.out.println("No se encontró ninguna nota para el estudiante y curso especificados.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar nota: " + e.getMessage());
        }
    }

    public int obtenerIdNotaPorEstudianteCurso(String nombreEstudiante, String nombreCurso) {
        String sql = "SELECT n.idNota FROM NOTA n "
                + "JOIN ESTUDIANTE e ON n.idEstudiante = e.idEstudiante "
                + "JOIN CURSO c ON n.idCurso = c.idCurso "
                + "WHERE e.nombre = ? AND c.nombreCurso = ?";
        int idNota = -1; // Valor por defecto en caso de no encontrar

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreEstudiante);
            stmt.setString(2, nombreCurso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idNota = rs.getInt("idNota");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ID de la nota: " + e.getMessage());
        }

        return idNota; // Retorna -1 si no se encuentra
    }

    /*

    
    
    
    public void actualizarNota(Nota nota) {
        String sql = "UPDATE NOTA SET idEstudiante = ?, idCurso = ?, nota = ?, fechaRegistro = ? WHERE idNota = ?";
        
        try (Connection conexion = conexionBD.conectar(); 
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
             
            stmt.setInt(1, nota.getIdEstudiante());
            stmt.setInt(2, nota.getIdCurso());
            stmt.setDouble(3, nota.getNota());
            stmt.setDate(4, new java.sql.Date(nota.getFechaRegistro().getTime()));
            stmt.setInt(5, nota.getIdNota());
            stmt.executeUpdate();
            System.out.println("Nota actualizada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar nota: " + e.getMessage());
        }
    }
    
    public void eliminarNota(int idNota) {
        String sql = "DELETE FROM NOTA WHERE idNota = ?";
        
        try (Connection conexion = conexionBD.conectar(); 
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
             
            stmt.setInt(1, idNota);
            stmt.executeUpdate();
            System.out.println("Nota eliminada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar nota: " + e.getMessage());
        }
    }
     */
}
