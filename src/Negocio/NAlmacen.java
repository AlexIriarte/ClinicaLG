package Negocio;

import Datos.DAlmacen;
import Datos.DCategoria;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NAlmacen {
    private DAlmacen dAlmacen;
    
    public NAlmacen() throws SQLException {
        dAlmacen = new DAlmacen();
    }
    
    public void addAlmacen(List<String> parametros) throws SQLException, ParseException {
        dAlmacen.save(
                parametros.get(0)
        );
    }
    
    public void editAlmacen(List<String> parametros) throws SQLException, ParseException {
        dAlmacen.update(
                Integer.parseInt(parametros.get(0)),
                parametros.get(1)
        );
    }
    
    public String[] getAlmacenById(List<String> parametros)throws SQLException, ParseException {
        return dAlmacen.getByid(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public void delAlmacen(List<String> parametros) throws SQLException, ParseException {
        dAlmacen.delete(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public ArrayList<String[]> getAlmacenes() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dAlmacen.getAll();
        return usuarios;
    }
}

