package com.example.ejercicio2;

import javafx.beans.property.*;

public class Lote {
    private final StringProperty idLote;
    private final StringProperty productor;
    private final StringProperty finca;
    private final StringProperty variedad;
    private final DoubleProperty peso;
    private final StringProperty fechaEntrega;
    private final StringProperty estado;
    private final DoubleProperty humedad;
    private final IntegerProperty altitud;

    public Lote(String idLote, String productor, String finca, String variedad,
                double peso, String fechaEntrega, String estado, double humedad, int altitud) {
        this.idLote = new SimpleStringProperty(idLote);
        this.productor = new SimpleStringProperty(productor);
        this.finca = new SimpleStringProperty(finca);
        this.variedad = new SimpleStringProperty(variedad);
        this.peso = new SimpleDoubleProperty(peso);
        this.fechaEntrega = new SimpleStringProperty(fechaEntrega);
        this.estado = new SimpleStringProperty(estado);
        this.humedad = new SimpleDoubleProperty(humedad);
        this.altitud = new SimpleIntegerProperty(altitud);
    }

    // Getters y Properties
    public String getIdLote() { return idLote.get(); }
    public StringProperty idLoteProperty() { return idLote; }
    public void setIdLote(String idLote) { this.idLote.set(idLote); }

    public String getProductor() { return productor.get(); }
    public StringProperty productorProperty() { return productor; }
    public void setProductor(String productor) { this.productor.set(productor); }

    public String getFinca() { return finca.get(); }
    public StringProperty fincaProperty() { return finca; }
    public void setFinca(String finca) { this.finca.set(finca); }

    public String getVariedad() { return variedad.get(); }
    public StringProperty variedadProperty() { return variedad; }
    public void setVariedad(String variedad) { this.variedad.set(variedad); }

    public double getPeso() { return peso.get(); }
    public DoubleProperty pesoProperty() { return peso; }
    public void setPeso(double peso) { this.peso.set(peso); }

    public String getFechaEntrega() { return fechaEntrega.get(); }
    public StringProperty fechaEntregaProperty() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega.set(fechaEntrega); }

    public String getEstado() { return estado.get(); }
    public StringProperty estadoProperty() { return estado; }
    public void setEstado(String estado) { this.estado.set(estado); }

    public double getHumedad() { return humedad.get(); }
    public DoubleProperty humedadProperty() { return humedad; }
    public void setHumedad(double humedad) { this.humedad.set(humedad); }

    public int getAltitud() { return altitud.get(); }
    public IntegerProperty altitudProperty() { return altitud; }
    public void setAltitud(int altitud) { this.altitud.set(altitud); }
}