package uamv.edu.ni.tiendaartesanias;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CatalogoApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(
                CatalogoApplication.class.getResource("catalogo-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 800, 500);

        stage.setTitle("Tienda de Artesanías Nicaragüenses");
        stage.setScene(scene);
        stage.show();
    }

}