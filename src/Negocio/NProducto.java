package Negocio;

import Datos.DProducto;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NProducto {
    private DProducto dProducto;
    
    public NProducto() throws SQLException {
        dProducto = new DProducto();
    }
    
    public void addProducto(List<String> parametros) throws SQLException, ParseException {
        dProducto.save(
                parametros.get(0),
                parametros.get(1),
                Float.parseFloat(parametros.get(2)),
                Integer.parseInt(parametros.get(3)),
                Integer.parseInt(parametros.get(4))
        );
        //String nombre, String marca, float precio, int user_id, int categoria_id
    }
    
    public void editProducto(List<String> parametros) throws SQLException, ParseException {
        dProducto.update(
                Integer.parseInt(parametros.get(0)),
                parametros.get(1),
                parametros.get(2),
                Float.parseFloat(parametros.get(3)),
                Integer.parseInt(parametros.get(4)),
                Integer.parseInt(parametros.get(5))
        );
    }
    
    public String[] getProductoById(List<String> parametros)throws SQLException, ParseException {
        return dProducto.getById(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public String[] getProductoByName(List<String> parametros)throws SQLException, ParseException {
        return dProducto.getByName(
                parametros.get(0)
        );
    }
    
    public ArrayList<String[]> getStock(List<String> parametros)throws SQLException, ParseException {
        ArrayList<String[]> productos = (ArrayList<String[]>) dProducto.getStock(
                Integer.parseInt(parametros.get(0))
        );
        return productos;
    }
    
    public void delProducto(List<String> parametros) throws SQLException, ParseException {
        dProducto.delete(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public ArrayList<String[]> getProductos() throws SQLException {
        ArrayList<String[]> productos = (ArrayList<String[]>) dProducto.getAll();
        return productos;
    }
}

