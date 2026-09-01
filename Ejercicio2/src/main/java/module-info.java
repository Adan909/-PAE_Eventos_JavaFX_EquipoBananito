module com.example.ejercicio2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ejercicio2 to javafx.fxml;
    exports com.example.ejercicio2;
}