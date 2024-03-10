package Negocio;

import Datos.DUserEspecialidad;

import java.sql.SQLException;
import java.util.ArrayList;

public class NUserEspecialidad {

    private final DUserEspecialidad dUserEspecialidad;

    public NUserEspecialidad() throws SQLException {
        this.dUserEspecialidad = new DUserEspecialidad();
    }

    public void addUserEspecialidad(int userId, int especialidadId) throws SQLException {
        dUserEspecialidad.save(userId, especialidadId);
    }

    public void editUserEspecialidad(int userId, int especialidadId) throws SQLException {
        dUserEspecialidad.update(userId, especialidadId);
    }

    public void delUserEspecialidad(int userId, int especialidadId) throws SQLException {
        dUserEspecialidad.delete(userId, especialidadId);
    }

    public ArrayList<Integer> getUserEspecialidadById(int userId) throws SQLException {
        return dUserEspecialidad.getByUserId(userId);
    }

    public ArrayList<Integer> getUsersByEspecialidadId(int especialidadId) throws SQLException {
        return dUserEspecialidad.getByEspecialidadId(especialidadId);
    }
}
