module uamv.edu.ni.tiendaartesanias {

    requires javafx.controls;
    requires javafx.fxml;

    exports uamv.edu.ni.tiendaartesanias;
    exports uamv.edu.ni.tiendaartesanias.controllers;

    opens uamv.edu.ni.tiendaartesanias.controllers to javafx.fxml;
}