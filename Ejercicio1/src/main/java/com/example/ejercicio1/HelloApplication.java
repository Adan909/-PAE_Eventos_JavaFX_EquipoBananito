package com.example.ejercicio1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carga el archivo FXML desde el directorio de recursos (src/main/resources/com/pulperia/)
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("InventarioPulperia.fxml"));

        // Define la escena con el tamaño inicial de la ventana
        Scene scene = new Scene(fxmlLoader.load(), 1100, 850);

        // Configuración de la ventana principal
        stage.setTitle("Pulpería Don Carlos - Sistema de Inventario");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(650);

        // Mostrar la interfaz
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}