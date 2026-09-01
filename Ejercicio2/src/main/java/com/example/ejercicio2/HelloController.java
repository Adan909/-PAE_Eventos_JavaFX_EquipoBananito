package com.example.ejercicio2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class HelloController {

    @FXML private TableView<Lote> tablaLotes;
    @FXML private TableColumn<Lote, String> colId;
    @FXML private TableColumn<Lote, String> colProductor;
    @FXML private TableColumn<Lote, String> colFinca;
    @FXML private TableColumn<Lote, String> colVariedad;
    @FXML private TableColumn<Lote, Double> colPeso;
    @FXML private TableColumn<Lote, String> colFecha;
    @FXML private TableColumn<Lote, String> colEstado;
    @FXML private TableColumn<Lote, Double> colHumedad;
    @FXML private TableColumn<Lote, Integer> colAltitud;

    @FXML private Label lblTotalLotes;
    @FXML private Label lblAprobados;
    @FXML private Label lblPesoTotal;
    @FXML private Label lblEnRevision;

    private final ObservableList<Lote> listaLotes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Mapeo de columnas
        colId.setCellValueFactory(new PropertyValueFactory<>("idLote"));
        colProductor.setCellValueFactory(new PropertyValueFactory<>("productor"));
        colFinca.setCellValueFactory(new PropertyValueFactory<>("finca"));
        colVariedad.setCellValueFactory(new PropertyValueFactory<>("variedad"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colHumedad.setCellValueFactory(new PropertyValueFactory<>("humedad"));
        colAltitud.setCellValueFactory(new PropertyValueFactory<>("altitud"));

        // Personalización de Badges de Estado con los colores requeridos
        colEstado.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label label = new Label(item);
                    label.setStyle("-fx-padding: 3 8 3 8; -fx-background-radius: 10; -fx-font-weight: bold; -fx-font-size: 11px;");

                    switch (item.toLowerCase()) {
                        case "aprobado" -> label.setStyle(label.getStyle() + "-fx-background-color: #81B29A; -fx-text-fill: white;");
                        case "en revisión" -> label.setStyle(label.getStyle() + "-fx-background-color: #F2CC8F; -fx-text-fill: #2C1A1D;");
                        case "rechazado" -> label.setStyle(label.getStyle() + "-fx-background-color: #E07A5F; -fx-text-fill: white;");
                        default -> label.setStyle(label.getStyle() + "-fx-background-color: #DCD5C0; -fx-text-fill: #2C1A1D;");
                    }
                    setGraphic(label);
                    setText(null);
                }
            }
        });

        // Cargar datos de prueba
        cargarDatosPrueba();
        tablaLotes.setItems(listaLotes);
        actualizarMetricas();

        // Eventos MouseEvent y Menú Contextual
        configurarEventosTabla();
    }

    private void configurarEventosTabla() {
        // ContextMenu para Editar y Eliminar
        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemEditar = new MenuItem("✏️ Editar Lote");
        MenuItem itemEliminar = new MenuItem("🗑️ Eliminar Lote");
        itemEliminar.setStyle("-fx-text-fill: #E07A5F; -fx-font-weight: bold;"); // Color de advertencia

        itemEditar.setOnAction(e -> handleEditarLote());
        itemEliminar.setOnAction(e -> handleEliminarLote());

        contextMenu.getItems().addAll(itemEditar, itemEliminar);

        // Asociar ContextMenu y MouseEvent a las filas
        tablaLotes.setRowFactory(tv -> {
            TableRow<Lote> row = new TableRow<>();

            row.setOnMouseClicked((MouseEvent event) -> {
                if (!row.isEmpty()) {
                    // Clic primario -> Mostrar detalles mediante MouseEvent
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        mostrarDetallesLote(row.getItem());
                    }
                }
            });

            // Asignar context menu solo a filas no vacías
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });
    }

    private void mostrarDetallesLote(Lote lote) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Detalles del Lote");
        dialog.setHeaderText("Información completa del Lote: " + lote.getIdLote());
        dialog.setContentText(
                "Productor: " + lote.getProductor() + "\n" +
                        "Finca: " + lote.getFinca() + "\n" +
                        "Variedad: " + lote.getVariedad() + "\n" +
                        "Peso Total: " + lote.getPeso() + " kg\n" +
                        "Fecha de Entrega: " + lote.getFechaEntrega() + "\n" +
                        "Estado: " + lote.getEstado() + "\n" +
                        "Humedad: " + lote.getHumedad() + "%\n" +
                        "Altitud: " + lote.getAltitud() + " msnm"
        );
        dialog.showAndWait();
    }

    @FXML
    private void handleAgregarLote() {
        Lote nuevo = new Lote("L-2026-00" + (listaLotes.size() + 1), "Nuevo Productor", "Finca Demo", "Caturra", 300, "01 sep 2026", "Registrado", 12.0, 1500);
        listaLotes.add(nuevo);
        actualizarMetricas();
    }

    private void handleEditarLote() {
        Lote seleccionado = tablaLotes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        // Crear diálogo personalizado
        Dialog<Lote> dialog = new Dialog<>();
        dialog.setTitle("Editar Lote");
        dialog.setHeaderText("Modificar datos del lote: " + seleccionado.getIdLote());

        ButtonType btnGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnGuardar, ButtonType.CANCEL);

        // Formulario de edición
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        TextField txtProductor = new TextField(seleccionado.getProductor());
        TextField txtFinca = new TextField(seleccionado.getFinca());
        TextField txtVariedad = new TextField(seleccionado.getVariedad());
        TextField txtPeso = new TextField(String.valueOf(seleccionado.getPeso()));

        ComboBox<String> cbEstado = new ComboBox<>(FXCollections.observableArrayList(
                "Aprobado", "En revisión", "Registrado", "Rechazado"
        ));
        cbEstado.setValue(seleccionado.getEstado());

        TextField txtHumedad = new TextField(String.valueOf(seleccionado.getHumedad()));
        TextField txtAltitud = new TextField(String.valueOf(seleccionado.getAltitud()));

        grid.add(new Label("Productor:"), 0, 0);
        grid.add(txtProductor, 1, 0);
        grid.add(new Label("Finca:"), 0, 1);
        grid.add(txtFinca, 1, 1);
        grid.add(new Label("Variedad:"), 0, 2);
        grid.add(txtVariedad, 1, 2);
        grid.add(new Label("Peso (kg):"), 0, 3);
        grid.add(txtPeso, 1, 3);
        grid.add(new Label("Estado:"), 0, 4);
        grid.add(cbEstado, 1, 4);
        grid.add(new Label("Humedad (%):"), 0, 5);
        grid.add(txtHumedad, 1, 5);
        grid.add(new Label("Altitud (msnm):"), 0, 6);
        grid.add(txtAltitud, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Convertir el resultado al presionar Guardar
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnGuardar) {
                try {
                    seleccionado.setProductor(txtProductor.getText());
                    seleccionado.setFinca(txtFinca.getText());
                    seleccionado.setVariedad(txtVariedad.getText());
                    seleccionado.setPeso(Double.parseDouble(txtPeso.getText()));
                    seleccionado.setEstado(cbEstado.getValue());
                    seleccionado.setHumedad(Double.parseDouble(txtHumedad.getText()));
                    seleccionado.setAltitud(Integer.parseInt(txtAltitud.getText()));
                    return seleccionado;
                } catch (NumberFormatException ex) {
                    Alert alertErr = new Alert(Alert.AlertType.ERROR, "Asegúrate de ingresar valores numéricos válidos en Peso, Humedad y Altitud.");
                    alertErr.showAndWait();
                }
            }
            return null;
        });

        Optional<Lote> result = dialog.showAndWait();
        result.ifPresent(loteActualizado -> {
            tablaLotes.refresh();
            actualizarMetricas();
        });
    }

    private void handleEliminarLote() {
        Lote seleccionado = tablaLotes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        // Alerta de Confirmación de eliminación con estilizado del botón
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Está seguro de eliminar el lote " + seleccionado.getIdLote() + "?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            listaLotes.remove(seleccionado);
            actualizarMetricas();
        }
    }

    private void actualizarMetricas() {
        lblTotalLotes.setText(String.valueOf(listaLotes.size()));

        long aprobados = listaLotes.stream().filter(l -> "Aprobado".equalsIgnoreCase(l.getEstado())).count();
        lblAprobados.setText(String.valueOf(aprobados));

        long revision = listaLotes.stream().filter(l -> "En revisión".equalsIgnoreCase(l.getEstado())).count();
        lblEnRevision.setText(String.valueOf(revision));

        double pesoTotal = listaLotes.stream().mapToDouble(Lote::getPeso).sum();
        lblPesoTotal.setText(String.format("%.0f kg", pesoTotal));
    }

    private void cargarDatosPrueba() {
        listaLotes.addAll(
                new Lote("L-2026-001", "María Esperanza Tovar", "El Paraíso", "Caturra", 312, "14 ago 2026", "Aprobado", 11.2, 1780),
                new Lote("L-2026-002", "Hernán Cifuentes Ríos", "La Montañita", "Gesha", 95, "18 ago 2026", "En revisión", 12.8, 2040),
                new Lote("L-2026-003", "Dolores Arias Mena", "Finca Bella Vista", "Bourbon", 480, "20 ago 2026", "Aprobado", 10.9, 1650),
                new Lote("L-2026-004", "Jesús Morales Pacheco", "Los Naranjos", "Typica", 220, "22 ago 2026", "Registrado", 13.5, 1420),
                new Lote("L-2026-005", "Rosa Elena Gutiérrez", "El Centenario", "Castillo", 560, "25 ago 2026", "Rechazado", 15.1, 1380),
                new Lote("L-2026-006", "Álvaro Suárez Vega", "Hacienda San Luis", "Gesha", 78, "28 ago 2026", "Registrado", 11.7, 2100),
                new Lote("L-2026-007", "Carmen Luz Ospina", "Villa Rosa", "Caturra", 390, "30 ago 2026", "En revisión", 12.2, 1900)
        );
    }
}