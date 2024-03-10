package Negocio;

import Datos.DCita;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NCita {

    private final DCita dCitas;

    public NCita() throws SQLException {
        this.dCitas = new DCita();
    }

    public void addCita(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos");
        }

        dCitas.save(new DCita(
                parametros.get(0),
                parametros.get(1),
                Integer.parseInt(parametros.get(2)),
                Integer.parseInt(parametros.get(3)),
                Integer.parseInt(parametros.get(4))));
    }

    public void editCita(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos");
        }

        dCitas.update(Integer.parseInt(parametros.get(0)), new DCita(
                parametros.get(1),
                parametros.get(2),
                parametros.get(3),
                Integer.parseInt(parametros.get(4)),
                Integer.parseInt(parametros.get(5)),
                Integer.parseInt(parametros.get(6))));
    }

    public void delCita(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos");
        }

        dCitas.delete(Integer.parseInt(parametros.get(0)));
    }

    public String[] getCitaById(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos");
        }
        return dCitas.getById(Integer.parseInt(parametros.get(0)));
    }

    public ArrayList<String[]> getCitaByFecha(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos");
        }
        return dCitas.getByFecha(parametros.get(0));
    }

    public ArrayList<String[]> getCitaByDoctorIdAndFecha(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos");
        }
        return dCitas.getByDoctorIdAndFecha(
                Integer.parseInt(parametros.get(0)),
                parametros.get(1)
        );
    }

    public ArrayList<String[]> getCitaByEspecialidadIdAndFecha(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos");
        }
        return dCitas.getByEspecialidadIdAndFecha(
                Integer.parseInt(parametros.get(0)),
                parametros.get(1)
        );
    }

    public ArrayList<String[]> getCitas() throws SQLException, ParseException {
        return dCitas.getAll();
    }
    
    public ArrayList<String[]> getReporte(List<String> parametros) throws SQLException, ParseException {
        int type = Integer.parseInt(parametros.get(0));
        
        if(type == 1){
            return dCitas.getIngresosPorEspecialidad(parametros.get(1), parametros.get(2));
        } 
        
        if(type == 2){
            return dCitas.getCantidadCitasDoctor(parametros.get(1), parametros.get(2));
        } 
        
        if (type == 3){
            return dCitas.getIngresosDoctor(parametros.get(1), parametros.get(2));
        } 
        
        throw new SQLException("tipo de reporte no válido.");
        
        // type
        // 1: mostrar las especialidades junto con el ingreso de cada una entre 2 fechas
        // 2: mostrar la cantidad de citas por doctor en un rango de 2 fechas
        // 3: mostrar la cantidad de dinero que recaudó cada doctor en un rango de 2 fechas
    }
}
