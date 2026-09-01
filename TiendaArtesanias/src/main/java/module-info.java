module uamv.edu.ni.tiendaartesanias {
    requires javafx.controls;
    requires javafx.fxml;


    opens uamv.edu.ni.tiendaartesanias to javafx.fxml;
    exports uamv.edu.ni.tiendaartesanias;
}