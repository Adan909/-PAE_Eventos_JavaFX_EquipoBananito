package uamv.edu.ni.tiendaartesanias;

public class Venta {

    private String producto;
    private int cantidad;
    private double precio;
    private double total;

    public Venta(String producto, int cantidad, double precio, double total) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public double getTotal() {
        return total;
    }
}

