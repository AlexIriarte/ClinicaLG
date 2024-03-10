package Negocio;

import Datos.DAreaMedica;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NAreaMedica {

    private final DAreaMedica dAreaMedica;

    public NAreaMedica() throws SQLException {
        this.dAreaMedica = new DAreaMedica();
    }

    public void addAreaMedica(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }

        if (parametros.size() > 3) {
            dAreaMedica.save(new DAreaMedica(
                    parametros.get(0),
                    parametros.get(1),
                    Integer.parseInt(parametros.get(2)),
                    Integer.parseInt(parametros.get(3))));
        }else{
            dAreaMedica.save(new DAreaMedica(
                    parametros.get(0),
                    parametros.get(1),
                    Integer.parseInt(parametros.get(2))));
        }

    }

    public void editAreaMedica(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dAreaMedica.update(Integer.parseInt(parametros.get(0)), new DAreaMedica(
                parametros.get(1),
                parametros.get(2),
                Integer.parseInt(parametros.get(3)),
                Integer.parseInt(parametros.get(4)))
        );
    }

    public void delAreaMedica(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dAreaMedica.delete(Integer.parseInt(parametros.get(0)));
    }

    public String[] getAreaMedicaById(List<String> parametros) throws SQLException {
        return this.dAreaMedica.getById(Integer.parseInt(parametros.get(0)));
    }

    public ArrayList<String[]> getAreasMedicas() throws SQLException {
        return dAreaMedica.getAll();
    }
    
    public void asignar(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("parametros vacios!");
        }
        // id ambiente, id persona
        dAreaMedica.asignar(Integer.parseInt(parametros.get(0)), Integer.parseInt(parametros.get(1)));
    }
}
