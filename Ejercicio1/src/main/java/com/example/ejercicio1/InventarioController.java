package com.example.ejercicio1;

import com.example.ejercicio1.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InventarioController {

    // Formulario
    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;
    @FXML private Button btnGuardar;

    // Buscador
    @FXML private TextField txtBuscar;

    // Tabla
    @FXML private TableView<Producto> tablaExistencias;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;
    @FXML private TableColumn<Producto, Double> colSubtotal;

    // Labels Métricas
    @FXML private Label lblTotalProductos;
    @FXML private Label lblUnidadesTotales;
    @FXML private Label lblValorInventario;
    @FXML private Label lblContadorProductos;

    // Colecciones de datos
    private final ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private FilteredList<Producto> productosFiltrados;

    @FXML
    public void initialize() {
        // Mapear columnas con las propiedades de la clase Producto
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        // Configurar lista filtrada para el buscador
        productosFiltrados = new FilteredList<>(listaProductos, p -> true);
        tablaExistencias.setItems(productosFiltrados);

        // Datos iniciales de prueba (opcional)
        listaProductos.addAll(
                new Producto("P001", "Arroz (lb)", 12.50, 50),
                new Producto("P002", "Frijoles negros (lb)", 18.00, 30),
                new Producto("P003", "Aceite vegetal (lt)", 85.00, 15),
                new Producto("P004", "Sal (500g)", 8.50, 40)
        );

        actualizarMetricas();
    }

    // EVENTO 1: ActionEvent para Guardar Producto
    @FXML
    private void handleGuardarProducto(ActionEvent event) {
        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String cantidadStr = txtCantidad.getText().trim();

        // Validar campos vacíos
        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || cantidadStr.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor llene todos los campos requeridos (*).");
            return;
        }

        double precio;
        int cantidad;

        // Validar tipos numéricos
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de validación", "El precio debe ser mayor a 0.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "El precio debe ser un número válido (ej. 950.00).");
            return;
        }

        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad < 0) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de validación", "La cantidad no puede ser negativa.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "La cantidad debe ser un entero válido (ej. 25).");
            return;
        }

        // Agregar nuevo producto a la lista
        Producto nuevoProducto = new Producto(codigo, nombre, precio, cantidad);
        listaProductos.add(nuevoProducto);

        // Limpiar campos y recalcular métricas
        limpiarFormulario();
        actualizarMetricas();
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto registrado correctamente.");
    }

    // EVENTO 2: KeyEvent para Buscar con ENTER
    @FXML
    private void handleBuscarKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String criterio = txtBuscar.getText().trim().toLowerCase();

            productosFiltrados.setPredicate(producto -> {
                if (criterio.isEmpty()) {
                    return true;
                }
                return producto.getCodigo().toLowerCase().contains(criterio) ||
                        producto.getNombre().toLowerCase().contains(criterio);
            });

            lblContadorProductos.setText(productosFiltrados.size() + " productos mostrados");
        }
    }

    // Métodos auxiliares
    private void actualizarMetricas() {
        int totalProductos = listaProductos.size();
        int totalUnidades = listaProductos.stream().mapToInt(Producto::getCantidad).sum();
        double valorTotal = listaProductos.stream().mapToDouble(Producto::getSubtotal).sum();

        lblTotalProductos.setText(String.valueOf(totalProductos));
        lblUnidadesTotales.setText(String.valueOf(totalUnidades));
        lblValorInventario.setText(String.format("₡%.2f", valorTotal));
        lblContadorProductos.setText(totalProductos + " productos");
    }

    private void limpiarFormulario() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCodigo.requestFocus();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}