package uamv.edu.ni.tiendaartesanias;

import uamv.edu.ni.tiendaartesanias.interfaces.CRUD;

import java.util.ArrayList;
import java.util.List;

public class CatalogoDAO implements CRUD<Catalogo> {

    private static List<Catalogo> productos = new ArrayList<>();

    @Override
    public void agregar(Catalogo entidad) {
        productos.add(entidad);
    }

    @Override
    public List<Catalogo> obtenerDatos() {
        return productos;
    }
}
