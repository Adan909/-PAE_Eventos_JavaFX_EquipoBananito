package uamv.edu.ni.tiendaartesanias;

public class Catalogo {
    private String nombre;
    private String categoria;
    private double precio;
    private String imagen;

    public Catalogo(String nombre, String categoria, double precio, String imagen) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }
    @Override
    public String toString() {
        return nombre;
    }
}
