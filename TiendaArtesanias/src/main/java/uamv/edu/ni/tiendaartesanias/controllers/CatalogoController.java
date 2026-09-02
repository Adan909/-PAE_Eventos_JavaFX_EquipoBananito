package uamv.edu.ni.tiendaartesanias.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import uamv.edu.ni.tiendaartesanias.Catalogo;
import uamv.edu.ni.tiendaartesanias.CatalogoDAO;

import java.io.IOException;

public class CatalogoController {

    @FXML
    private TableView<Catalogo> tablaCatalogo;

    @FXML
    private TableColumn<Catalogo, String> colNombre;

    @FXML
    private TableColumn<Catalogo, String> colCategoria;

    @FXML
    private TableColumn<Catalogo, String> colPrecio;

    @FXML
    private TableColumn<Catalogo, ImageView> colImagen;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCategoria;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtBuscar;

    private CatalogoDAO catalogoDAO;

    @FXML
    public void initialize() {

        catalogoDAO = new CatalogoDAO();

        colNombre.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getNombre()
                )
        );

        colCategoria.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getCategoria()
                )
        );

        colPrecio.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        String.valueOf(dato.getValue().getPrecio())
                )
        );

        colImagen.setCellValueFactory(dato -> {

            String ruta = dato.getValue().getImagen();

            Image imagen = new Image(
                    getClass().getResourceAsStream(ruta)
            );

            ImageView imageView = new ImageView(imagen);

            imageView.setFitWidth(100);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);

            return new SimpleObjectProperty<>(imageView);
        });
    }

    @FXML
    private void nuevo() {

        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtBuscar.clear();

        txtNombre.requestFocus();
    }

    @FXML
    private void guardar() {

        try {

            String nombre = txtNombre.getText();
            String categoria = txtCategoria.getText();
            double precio = Double.parseDouble(txtPrecio.getText());

            Catalogo producto = new Catalogo(
                    nombre,
                    categoria,
                    precio,
                    "/imagenes/hamaca.jpg"
            );

            catalogoDAO.agregar(producto);

            tablaCatalogo.setItems(
                    FXCollections.observableArrayList(
                            catalogoDAO.obtenerDatos()
                    )
            );

            mostrarMensaje(
                    "Producto guardado",
                    "El producto se agregó correctamente al catálogo."
            );

            nuevo();

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "El precio debe ser un número válido."
            );
        }
    }

    @FXML
    private void buscar() {

        String texto = txtBuscar.getText().toLowerCase();

        ObservableList<Catalogo> resultados =
                FXCollections.observableArrayList();

        for (Catalogo producto : catalogoDAO.obtenerDatos()) {

            if (producto.getNombre().toLowerCase().contains(texto)
                    || producto.getCategoria().toLowerCase().contains(texto)) {

                resultados.add(producto);
            }
        }

        tablaCatalogo.setItems(resultados);
    }

    @FXML
    private void menuCatalogo() {

        tablaCatalogo.setItems(
                FXCollections.observableArrayList(
                        catalogoDAO.obtenerDatos()
                )
        );
    }

    @FXML
    private void menuVentas() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/uamv/edu/ni/tiendaartesanias/ventas-view.fxml"
                    )
            );

            Stage stage = new Stage();

            Scene scene = new Scene(
                    loader.load(),
                    700,
                    500
            );

            stage.setTitle("Ventas");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void menuAyuda() { Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Ayuda");
        alerta.setHeaderText("Instrucciones del catálogo");
        alerta.setContentText( "INSTRUCCIONES DE USO\n\n" + "1. Nuevo:\n" +
                "Utilice el botón 'Nuevo' para limpiar los campos " + "y registrar un nuevo producto.\n\n" +
                "2. Guardar:\n" + "Ingrese el nombre, categoría y precio del producto " +
                "y presione 'Guardar' para agregarlo al catálogo.\n\n" + "3. Buscar:\n" +
                "Escriba el nombre o categoría del producto en el " + "campo de búsqueda y presione 'Buscar'.\n\n"
                + "4. Catálogo:\n" + "Seleccione 'Catálogo' en el menú para visualizar " + "los productos registrados.\n\n"
                + "5. Ventas:\n" + "Seleccione 'Ventas' para acceder al módulo de ventas.\n\n" + "6. Imágenes:\n"
                + "Cada producto puede mostrar una imagen representativa " + "de la artesanía." ); alerta.showAndWait(); }

    private void mostrarMensaje(String titulo, String mensaje) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}