package Negocio;

import Datos.DHorario;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NHorario {

    private final DHorario dHorario;

    public NHorario() throws SQLException {
        this.dHorario = new DHorario();
    }

    public void addHorario(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dHorario.save(new DHorario(
                Integer.parseInt(parametros.get(0)),
                Boolean.parseBoolean(parametros.get(1)),
                parametros.get(2),
                parametros.get(3),
                parametros.get(4),
                parametros.get(5),
                Integer.parseInt(parametros.get(6))));
    }

    public void editHorario(List<String> parametros) throws SQLException, ParseException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dHorario.update(Integer.parseInt(parametros.get(0)), new DHorario(
                Integer.parseInt(parametros.get(1)),
                Boolean.parseBoolean(parametros.get(2)),
                parametros.get(3),
                parametros.get(4),
                parametros.get(5),
                parametros.get(6),
                Integer.parseInt(parametros.get(7)))
        );
    }

    public void delHorario(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        dHorario.delete(Integer.parseInt(parametros.get(0)));
    }

    public String[] getHorarioByEspecialidadIdAndDia(List<String> parametros) throws SQLException {
        if (parametros.isEmpty()) {
            throw new SQLException("Parámetros vacíos!");
        }
        return this.dHorario.getByEspecialidadIdAndDia(Integer.parseInt(parametros.get(0)), Integer.parseInt(parametros.get(1)));
    }

    public ArrayList<String[]> getHorarios() throws SQLException {
        return dHorario.getAll();
    }
}
