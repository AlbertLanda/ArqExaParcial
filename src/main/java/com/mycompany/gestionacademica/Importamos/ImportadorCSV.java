/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestionacademica.Importamos;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mycompany.gestionacademica.Modelo.Estudiante;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public class ImportadorCSV {

    private final DatabaseReference database;

    public ImportadorCSV() {
        database = FirebaseDatabase.getInstance().getReference("estudiantes");
    }

    public void importarDatos(String archivoCSV) throws CsvValidationException {
        try (CSVReader csvReader = new CSVReader(new FileReader(archivoCSV))) {
            String[] valores;
            csvReader.readNext();

            while ((valores = csvReader.readNext()) != null) {
                // Asegúrate de que los índices coincidan con tu archivo CSV
                String nombre = valores[1];
                int edad = Integer.parseInt(valores[2]);
                String email = valores[3];

                Estudiante estudiante = new Estudiante(-1, nombre, edad, email);

                Map<String, Object> estudianteMap = new HashMap<>();
                estudianteMap.put("idEstudiante", estudiante.getIdEstudiante());
                estudianteMap.put("nombre", estudiante.getNombre());
                estudianteMap.put("edad", estudiante.getEdad());
                estudianteMap.put("email", estudiante.getEmail());
                // Generar un ID único para cada estudiante en Firebase
                String id = database.push().getKey(); // Genera un ID
                database.child(id).setValue(estudianteMap, (DatabaseError databaseError, DatabaseReference databaseReference) -> {
                    if (databaseError != null) {
                        System.out.println("Error al guardar los datos: " + databaseError.getMessage());
                    } else {
                        System.out.println("Datos guardados exitosamente en Firebase.");
                    }
                });
            }
            System.out.println("Datos importados exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }
}
