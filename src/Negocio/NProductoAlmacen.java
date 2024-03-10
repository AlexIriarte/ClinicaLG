package Negocio;

import Datos.DProductoAlmacen;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NProductoAlmacen {
    private DProductoAlmacen dProductoAlmacen;
    
    public NProductoAlmacen() throws SQLException {
        dProductoAlmacen = new DProductoAlmacen();
    }
    
    public void addProductoAlmacen(List<String> parametros) throws SQLException, ParseException {
        dProductoAlmacen.save(
                Integer.parseInt(parametros.get(0)),
                Integer.parseInt(parametros.get(1)),
                Integer.parseInt(parametros.get(2))
        );
        //int stock, int producto_id, int almacen_id
    }
    
    public void editProductoAlmacen(List<String> parametros) throws SQLException, ParseException {
        dProductoAlmacen.update(
                Integer.parseInt(parametros.get(0)),
                Integer.parseInt(parametros.get(1)),
                Integer.parseInt(parametros.get(2)),
                Integer.parseInt(parametros.get(3))
        );
    }
    
    public String[] verProductoAlmacen(List<String> parametros)throws SQLException, ParseException {
        return dProductoAlmacen.getById(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public void delProductoAlmacen(List<String> parametros) throws SQLException, ParseException {
        dProductoAlmacen.delete(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public ArrayList<String[]> listar() throws SQLException {
        ArrayList<String[]> productos = (ArrayList<String[]>) dProductoAlmacen.getAll();
        return productos;
    }
}

