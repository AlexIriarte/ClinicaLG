package Negocio;

import Datos.DCategoria;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NCategoria {
    private DCategoria dCategoria;
    
    public NCategoria() throws SQLException {
        dCategoria = new DCategoria();
    }
    
    public void addCategoria(List<String> parametros) throws SQLException, ParseException {
        dCategoria.save(
                parametros.get(0)
        );
    }
    
    public void editCategoria(List<String> parametros) throws SQLException, ParseException {
        dCategoria.update(
                Integer.parseInt(parametros.get(0)),
                parametros.get(1)
        );
    }
    
    public String[] verCategoria(List<String> parametros)throws SQLException, ParseException {
        return dCategoria.getById(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public void delCategoria(List<String> parametros) throws SQLException, ParseException {
        dCategoria.delete(
                Integer.parseInt(parametros.get(0))
        );
    }
    
    public ArrayList<String[]> listar() throws SQLException {
        ArrayList<String[]> usuarios = (ArrayList<String[]>) dCategoria.getAll();
        return usuarios;
    }
}

