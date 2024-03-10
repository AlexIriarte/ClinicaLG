package Datos;

import DatabaseConnection.Singleton;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DUserEspecialidad {

    private Singleton s = Singleton.getInstancia();
    private PreparedStatement ps;
    private ResultSet rs;

    public DUserEspecialidad() throws SQLException {
    }

    public void save(int userId, int especialidadId) throws SQLException {
        String sql = "INSERT INTO users_especialidades(user_id, especialidad_id) VALUES (?, ?)";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, especialidadId);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DUserEspecialidad.save => ");
            throw new SQLException();
        }
    }

    public void update(int userId, int especialidadId) throws SQLException {
        String sql = "UPDATE users_especialidades SET user_id = ?, especialidad_id = ? WHERE user_id = ? AND especialidad_id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, especialidadId);
        ps.setInt(3, userId);
        ps.setInt(4, especialidadId);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DUserEspecialidad.update => ");
            throw new SQLException();
        }
    }

    public void delete(int userId, int especialidadId) throws SQLException {
        String sql = "DELETE FROM users_especialidades WHERE user_id = ? AND especialidad_id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, especialidadId);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DUserEspecialidad.delete => ");
            throw new SQLException();
        }
    }

    public ArrayList<Integer> getByUserId(int userId) throws SQLException {
        ArrayList<Integer> especialidades = new ArrayList<>();
        String sql = "SELECT especialidad_id FROM users_especialidades WHERE user_id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, userId);

        rs = ps.executeQuery();
        while (rs.next()) {
            especialidades.add(rs.getInt("especialidad_id"));
        }
        return especialidades;
    }

    public ArrayList<Integer> getByEspecialidadId(int especialidadId) throws SQLException {
        ArrayList<Integer> usuarios = new ArrayList<>();
        String sql = "SELECT user_id FROM users_especialidades WHERE especialidad_id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, especialidadId);

        rs = ps.executeQuery();
        while (rs.next()) {
            usuarios.add(rs.getInt("user_id"));
        }
        return usuarios;
    }

    public boolean exists(int userId, int especialidadId) throws SQLException {
        String sql = "SELECT * FROM users_especialidades WHERE user_id = ? AND especialidad_id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, especialidadId);
        rs = ps.executeQuery();
        return rs.next();
    }
}
