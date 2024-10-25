/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.gestionacademica;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.mycompany.gestionacademica.Importamos.ImportadorCSV;
import com.mycompany.gestionacademica.Vista.Gestion;
import com.opencsv.exceptions.CsvValidationException;
import com.sun.tools.javac.Main;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author LENOVO
 */
public class GestionAcademica {

    public static void main(String[] args) throws CsvValidationException {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf()); // También puedes usar FlatDarkLaf para un tema oscuro
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Error al aplicar FlatLaf: " + ex.getMessage());
        }

        // Inicializar y mostrar la ventana principal
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gestion().setVisible(true); // Cambia 'MiVista' por el nombre de tu JFrame principal
            }
        });
        
        try {
            // Cargar el archivo google-services.json
            InputStream serviceAccount = GestionAcademica.class.getResourceAsStream("/google-services.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://gestion-academica-16b5d-default-rtdb.firebaseio.com/")
                .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase inicializado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al inicializar Firebase: " + e.getMessage());
            e.printStackTrace();
        }
        
        ImportadorCSV importador = new ImportadorCSV();
        importador.importarDatos("C:/Exportamos/archivo.csv");
    }
}
