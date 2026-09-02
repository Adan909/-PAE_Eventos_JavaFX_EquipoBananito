package uamv.edu.ni.tiendaartesanias.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import uamv.edu.ni.tiendaartesanias.Catalogo;
import uamv.edu.ni.tiendaartesanias.CatalogoDAO;
import uamv.edu.ni.tiendaartesanias.Venta;

public class VentasController {

    @FXML
    private ComboBox<Catalogo> cbProducto;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtPrecio;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<Venta> tablaVentas;

    @FXML
    private TableColumn<Venta, String> colProducto;

    @FXML
    private TableColumn<Venta, Integer> colCantidad;

    @FXML
    private TableColumn<Venta, Double> colPrecio;

    @FXML
    private TableColumn<Venta, Double> colTotal;

    private CatalogoDAO catalogoDAO;

    @FXML
    public void initialize() {

        catalogoDAO = new CatalogoDAO();

        cbProducto.getItems().addAll(
                catalogoDAO.obtenerDatos()
        );

        colProducto.setCellValueFactory(
                dato -> new SimpleStringProperty(
                        dato.getValue().getProducto()
                )
        );

        colCantidad.setCellValueFactory(
                dato -> new SimpleIntegerProperty(
                        dato.getValue().getCantidad()
                ).asObject()
        );

        colPrecio.setCellValueFactory(
                dato -> new SimpleDoubleProperty(
                        dato.getValue().getPrecio()
                ).asObject()
        );

        colTotal.setCellValueFactory(
                dato -> new SimpleDoubleProperty(
                        dato.getValue().getTotal()
                ).asObject()
        );

        cbProducto.setOnAction(event -> {

            Catalogo producto = cbProducto.getValue();

            if (producto != null) {

                txtPrecio.setText(
                        String.valueOf(producto.getPrecio())
                );
            }
        });
    }

    @FXML
    private void calcularTotal() {

        try {

            Catalogo producto = cbProducto.getValue();

            if (producto == null) {
                mostrarMensaje(
                        "Error",
                        "Seleccione un producto."
                );
                return;
            }

            int cantidad = Integer.parseInt(
                    txtCantidad.getText()
            );

            double total =
                    producto.getPrecio() * cantidad;

            lblTotal.setText(
                    String.format("$%.2f", total)
            );

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "Ingrese una cantidad válida."
            );
        }
    }

    @FXML
    private void registrarVenta() {

        try {

            Catalogo producto = cbProducto.getValue();

            if (producto == null) {

                mostrarMensaje(
                        "Error",
                        "Seleccione un producto."
                );

                return;
            }

            int cantidad = Integer.parseInt(
                    txtCantidad.getText()
            );

            if (cantidad <= 0) {

                mostrarMensaje(
                        "Error",
                        "La cantidad debe ser mayor que cero."
                );

                return;
            }

            double precio = producto.getPrecio();

            double total = precio * cantidad;

            Venta venta = new Venta(
                    producto.getNombre(),
                    cantidad,
                    precio,
                    total
            );

            tablaVentas.getItems().add(venta);

            lblTotal.setText(
                    String.format("$%.2f", total)
            );

            mostrarMensaje(
                    "Venta registrada",
                    "La venta se registró correctamente."
            );

            cbProducto.getSelectionModel().clearSelection();
            txtCantidad.clear();
            txtPrecio.clear();

        } catch (NumberFormatException e) {

            mostrarMensaje(
                    "Error",
                    "Ingrese una cantidad válida."
            );
        }
    }

    private void mostrarMensaje(
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(
                Alert.AlertType.INFORMATION
        );

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

