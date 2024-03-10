package Negocio;

import Datos.DEspecialidad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NEspecialidad {

    private final DEspecialidad dEspecialidad;

    public NEspecialidad() throws SQLException {
        this.dEspecialidad = new DEspecialidad();
    }

    public void addEspecialidad(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dEspecialidad.save(new DEspecialidad(
                parametros.get(0),
                parametros.get(1))
        );
    }

    public void editEspecialidad(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dEspecialidad.update(Integer.parseInt(parametros.get(0)), new DEspecialidad(
                parametros.get(1),
                parametros.get(2))
        );
    }

    public void delEspecialidad(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dEspecialidad.delete(Integer.parseInt(parametros.get(0)));
    }

    public String[] getEspecialidadById(List<String> parametros) throws SQLException {
        return this.dEspecialidad.getById(Integer.parseInt(parametros.get(0)));
    }

    public ArrayList<String[]> getEspecialidades() throws SQLException {
        return dEspecialidad.getAll();
    }
        public ArrayList<String[]> getEspecialidadesConHorarios() throws SQLException {
        return dEspecialidad.getAllWithHorarios();
    }
}
