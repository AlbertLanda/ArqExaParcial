/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.Controlador;

import com.mycompany.gestionacademica.Conexion.ConexionBD;
import com.mycompany.gestionacademica.DAO.CursoDAO;
import com.mycompany.gestionacademica.DAO.EstudianteDAO;
import com.mycompany.gestionacademica.DAO.NotaDAO;
import com.mycompany.gestionacademica.Modelo.Curso;
import com.mycompany.gestionacademica.Modelo.Estudiante;
import com.mycompany.gestionacademica.Modelo.Nota;
import com.mycompany.gestionacademica.Vista.Gestion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO
 */
public class EstudianteController {

    private EstudianteDAO estudianteDAO;
    private Estudiante modeloEstudiante;
    private CursoDAO cursoDAO;
    private Curso modeloCurso;
    private NotaDAO notaDAO;
    private Nota modeloNota;
    private Gestion vistaGestion;
    private Connection conexion;

    public EstudianteController(Estudiante modeloEstudiante, Curso modeloCurso, Nota modeloNota, Gestion vistaGestion) {
        this.conexion = new ConexionBD().conectar();
        this.estudianteDAO = new EstudianteDAO(conexion);
        this.modeloEstudiante = modeloEstudiante;
        this.cursoDAO = new CursoDAO(conexion);
        this.modeloCurso = modeloCurso;
        this.notaDAO = new NotaDAO(conexion);
        this.modeloNota = modeloNota;
        this.vistaGestion = vistaGestion;
    }

    public void agregar() {
        String nombreEstudiante = vistaGestion.getTxtNomEstudiante().getText();
        int edadEstudiante;
        String emailEstudiante = vistaGestion.getTxtEmailEstudiante().getText();

        try {
            edadEstudiante = Integer.parseInt(vistaGestion.getTxtEdaEstudiante().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaGestion, "Por favor, ingrese una edad válida.");
            return;
        }

        if (nombreEstudiante.isEmpty() || edadEstudiante <= 0 || emailEstudiante.isEmpty()) {
            JOptionPane.showMessageDialog(vistaGestion, "Por favor, complete todos los campos.");
            return;
        }

        String nombreCurso = vistaGestion.getTxtNomCurso().getText();
        int creditosCurso;
        String docenteCurso = vistaGestion.getTxtDocente().getText();

        try {
            creditosCurso = Integer.parseInt(vistaGestion.getTxtCreditos().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaGestion, "Por favor, ingrese un número válido para los créditos.");
            return;
        }

        if (nombreCurso.isEmpty() || creditosCurso <= 0 || docenteCurso.isEmpty()) {
            JOptionPane.showMessageDialog(vistaGestion, "Por favor, complete todos los campos.");
            return;
        }

        int notaObtenida;
        Date fechaSeleccionada = vistaGestion.getDatechooserFecha().getDate();

        try {
            notaObtenida = Integer.parseInt(vistaGestion.getTxtNota().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaGestion, "Por favor, ingrese una nota válida.");
            return;
        }

        if (notaObtenida <= 0 || fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(vistaGestion, "Por favor, complete todos los campos.");
            return;
        }

        modeloEstudiante = new Estudiante(0, nombreEstudiante, edadEstudiante, emailEstudiante);
        modeloCurso = new Curso(0, nombreCurso, creditosCurso, docenteCurso);

        int estudianteId = estudianteDAO.agregarEstudiante(modeloEstudiante);
        int cursoId = cursoDAO.agregarCurso(modeloCurso);

        if (estudianteId != -1 && cursoId != -1) {
            modeloNota = new Nota(0, estudianteId, cursoId, notaObtenida, fechaSeleccionada);
            notaDAO.agregarNota(modeloNota);

            DefaultTableModel modeloTabla = (DefaultTableModel) vistaGestion.getTablaDatos().getModel();

            modeloTabla.addRow(new Object[]{
                nombreEstudiante,
                nombreCurso,
                creditosCurso,
                notaObtenida,
                fechaSeleccionada
            });

            limpiarCampos();
        }

    }

    public void verDatos() {
        List<Nota> listaNotas = notaDAO.obtenerNotas();
        DefaultTableModel modeloTabla = (DefaultTableModel) vistaGestion.getTablaDatos().getModel();

        // Limpiar la tabla antes de agregar nuevos datos
        modeloTabla.setRowCount(0);

        for (Nota nota : listaNotas) {
            Estudiante estudiante = estudianteDAO.obtenerEstudiantePorId(nota.getIdEstudiante());
            Curso curso = cursoDAO.obtenerCursoPorId(nota.getIdCurso());

            if (estudiante != null && curso != null) {
                modeloTabla.addRow(new Object[]{
                    estudiante.getNombre(),
                    curso.getNombreCurso(),
                    curso.getCreditos(),
                    nota.getNota(),
                    nota.getFechaRegistro()
                });
            }
        }
    }

    public void actualizar() {
        // Obtener la fila seleccionada en la tabla
        int filaSeleccionada = vistaGestion.getTablaDatos().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaGestion, "Seleccione una fila para actualizar.");
            return;
        }

        // Obtener el nombre del estudiante desde la tabla
        String nombreEstudiante = (String) vistaGestion.getTablaDatos().getValueAt(filaSeleccionada, 0); // Cambia el índice según la columna correcta

        // Obtener los nuevos datos del formulario
        String nuevoNombreEstudiante = vistaGestion.getTxtNomEstudiante().getText();
        String emailEstudiante = vistaGestion.getTxtEmailEstudiante().getText();
        String nombreCurso = vistaGestion.getTxtNomCurso().getText();
        String docenteCurso = vistaGestion.getTxtDocente().getText();
        Date fechaSeleccionada = vistaGestion.getDatechooserFecha().getDate();

        int edadEstudiante;
        int creditosCurso;
        int notaObtenida;

        // Validar los campos numéricos
        try {
            edadEstudiante = Integer.parseInt(vistaGestion.getTxtEdaEstudiante().getText());
            creditosCurso = Integer.parseInt(vistaGestion.getTxtCreditos().getText());
            notaObtenida = Integer.parseInt(vistaGestion.getTxtNota().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaGestion, "Ingrese números válidos.");
            return;
        }

        // Validar campos vacíos
        if (nuevoNombreEstudiante.isEmpty() || emailEstudiante.isEmpty() || nombreCurso.isEmpty() || docenteCurso.isEmpty() || fechaSeleccionada == null) {
            JOptionPane.showMessageDialog(vistaGestion, "Por favor, complete todos los campos.");
            return;
        }

        // Actualizar estudiante en la base de datos
        int idEstudiante = estudianteDAO.obtenerIdEstudiantePorNombre(nombreEstudiante);
        Estudiante actuEstudiante = new Estudiante(idEstudiante, nuevoNombreEstudiante, edadEstudiante, emailEstudiante);
        estudianteDAO.actualizarEstudiantePorNombre(nombreEstudiante, actuEstudiante);

        // Actualizar curso
        int idCurso = cursoDAO.obtenerIdCursoPorNombre(nombreCurso);
        Curso cursoActualizado = new Curso(idCurso, nombreCurso, creditosCurso, docenteCurso);
        cursoDAO.actualizarCursoPorNombre(nombreCurso, cursoActualizado);

        // Actualizar nota
        int idNota = notaDAO.obtenerIdNotaPorEstudianteCurso(nombreEstudiante, nombreCurso);
        Nota notaActualizada = new Nota(idNota, idEstudiante, idCurso, notaObtenida, fechaSeleccionada);
        notaDAO.actualizarNotaPorNombre(nombreEstudiante, nombreCurso, notaActualizada);

        // Formatear la fecha para mostrarla correctamente en la tabla
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = sdf.format(fechaSeleccionada);

        // Actualizar la tabla con los nuevos datos
        vistaGestion.getTablaDatos().setValueAt(nuevoNombreEstudiante, filaSeleccionada, 0);
        vistaGestion.getTablaDatos().setValueAt(nombreCurso, filaSeleccionada, 1);
        vistaGestion.getTablaDatos().setValueAt(creditosCurso, filaSeleccionada, 2);
        vistaGestion.getTablaDatos().setValueAt(notaObtenida, filaSeleccionada, 3);
        vistaGestion.getTablaDatos().setValueAt(fechaFormateada, filaSeleccionada, 4);

        JOptionPane.showMessageDialog(vistaGestion, "Datos actualizados correctamente.");
        limpiarCampos();
    }

    public void eliminar() {
        int filaSeleccionada = vistaGestion.getTablaDatos().getSelectedRow();

        // Verifica si se seleccionó una fila
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaGestion, "Seleccione una fila para eliminar.");
            return;
        }

        // Obtener el modelo de la tabla
        DefaultTableModel modelo = (DefaultTableModel) vistaGestion.getTablaDatos().getModel();

        // Confirmar la eliminación
        int respuesta = JOptionPane.showConfirmDialog(vistaGestion, "¿Está seguro de que desea eliminar este registro?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return; // Salir si el usuario cancela
        }

        // Obtener el ID del estudiante desde la tabla (suponiendo que está en la primera columna)
        String nombreEstudiante = (String) vistaGestion.getTablaDatos().getValueAt(filaSeleccionada, 0); // Ajusta el índice si es necesario

        // Llama al método de eliminación en tu DAO
        estudianteDAO.eliminarEstudiante(nombreEstudiante);

        // Eliminar el registro del modelo y actualizar la tabla
        modelo.removeRow(filaSeleccionada);

        JOptionPane.showMessageDialog(vistaGestion, "Registro eliminado correctamente.");
    }

        public void filtrarEstudiantes(String texto) {
        DefaultTableModel model = (DefaultTableModel) vistaGestion.getTablaDatos().getModel();
        model.setRowCount(0);

        String sql = "SELECT nombre, edad, email FROM ESTUDIANTE WHERE LOWER(nombre) LIKE ? OR LOWER(email) LIKE ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + texto + "%"); // Filtrar por nombre
            stmt.setString(2, "%" + texto + "%"); // Filtrar por email

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Recuperar los datos de cada estudiante
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String email = rs.getString("email");;

                // Agregar la fila filtrada a la tabla
                Object[] row = {nombre, edad, email};
                model.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar estudiantes: " + e.getMessage());
        }
    }
        
    public void limpiarCampos() {
        vistaGestion.getTxtNomEstudiante().setText("");
        vistaGestion.getTxtEdaEstudiante().setText("");
        vistaGestion.getTxtEmailEstudiante().setText("");
        vistaGestion.getTxtNomCurso().setText("");
        vistaGestion.getTxtCreditos().setText("");
        vistaGestion.getTxtDocente().setText("");
        vistaGestion.getTxtNota().setText("");
        vistaGestion.getDatechooserFecha().setDate(null);
    }

}
