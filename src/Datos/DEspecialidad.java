package Datos;

import DatabaseConnection.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DEspecialidad {

    private int id;
    private String nombre;
    private String descripcion;

    PreparedStatement ps; // para ejecutar la consulta
    ResultSet rs; // para leer los resultados
    Singleton s = Singleton.getInstancia();

    public DEspecialidad() throws SQLException {
        this.id = -1;
        this.nombre = null;
        this.descripcion = null;
    }

    public DEspecialidad(String nombre, String descripcion) throws SQLException {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // CRUD
    public void save(DEspecialidad newEspecialidad) throws SQLException {
        if (this.exists(newEspecialidad.nombre)) {
            String[] especialidadActual = this.getByName(newEspecialidad.nombre);
            this.update(Integer.parseInt(especialidadActual[0]), newEspecialidad);
            return;
        }
        String sql = "INSERT INTO especialidades(nombre, descripcion) VALUES(?, ?)";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, newEspecialidad.getNombre().toUpperCase().trim());
        ps.setString(2, newEspecialidad.getDescripcion().toUpperCase().trim());

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DEspecialidad.save => ");
            throw new SQLException();
        }
    }

    public void update(int idEspecialidad, DEspecialidad editEspecialidad) throws SQLException {

        if (this.getById(idEspecialidad) == null) {
            throw new SQLException("Error en DEspecialidad.update => id: " + idEspecialidad + " no encontrado!");
        }

        String sql = "UPDATE especialidades SET nombre = ?, descripcion = ? WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, editEspecialidad.getNombre().toUpperCase().trim());
        ps.setString(2, editEspecialidad.getDescripcion().toUpperCase().trim());
        ps.setInt(3, idEspecialidad);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DEspecialidad.update => ");
            throw new SQLException();
        }
    }

    public void delete(int idEspecialidad) throws SQLException {

        if (this.getById(idEspecialidad) == null) {
            throw new SQLException("Error en DEspecialidad.update => id: " + idEspecialidad + " no encontrado!");
        }

        String sql = "DELETE FROM especialidades WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, idEspecialidad);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DEspecialidad.delete => ");
            throw new SQLException();
        }
    }

    public String[] getById(int id) throws SQLException {
        String[] especialidad = null;
        String sql = "SELECT * FROM especialidades WHERE id=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            especialidad = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("descripcion")
            };
        }
        return especialidad;
    }

    public String[] getByName(String nombreABuscar) throws SQLException {
        String[] especialidad = null;
        String sql = "SELECT * FROM especialidades WHERE nombre=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, nombreABuscar.toUpperCase().trim());

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            especialidad = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("descripcion")
            };
        }
        return especialidad;
    }

    public ArrayList<String[]> getAll() throws SQLException {
        ArrayList<String[]> especialidades = new ArrayList();
        String sql = "SELECT * FROM especialidades ORDER BY id ASC";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] especialidadActual = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion")
            };
            especialidades.add(especialidadActual);
        }
        return especialidades;
    }

    public ArrayList<String[]> getAllWithHorarios() throws SQLException {
        ArrayList<String[]> especialidades = new ArrayList();
        String sql = ""
                + " SELECT"
                + " e.id,"
                + " e.nombre,"
                //+ " e.descripcion as especialidad_descripcion,"
                + " CASE h.dia"
                + "     WHEN 1 THEN 'LUNES'"
                + "     WHEN 2 THEN 'MARTES'"
                + "     WHEN 3 THEN 'MIÉRCOLES'"
                + "     WHEN 4 THEN 'JUEVES'"
                + "     WHEN 5 THEN 'VIERNES'"
                + "     WHEN 6 THEN 'SÁBADO'"
                + "     WHEN 7 THEN 'DOMINGO'"
                + " END as dia,"
                //+ " h.activo,"
                + " CONCAT(h.turno_mananha_inicio, ' - ', h.turno_mananha_fin) as turno_mañana,"
                + " CONCAT(h.turno_tarde_inicio, ' - ', h.turno_tarde_fin) as turno_tarde"
                + " FROM especialidades e"
                + " LEFT JOIN horarios h ON e.id = h.especialidad_id AND h.activo = true"
                //                + " WHERE"
                //                + "     h.activo = true"
                + " ORDER BY e.id ASC, h.dia ASC";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] especialidadActual = null;
            if (rs.getString("dia") == null) {
                especialidadActual = new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    "SIN HORARIO"
                };
            } else {
                especialidadActual = new String[]{
                    String.valueOf(rs.getInt("id")),
                    rs.getString("nombre"),
                    rs.getString("dia"),
                    rs.getString("turno_mañana"),
                    rs.getString("turno_tarde")
                };
            }

            especialidades.add(especialidadActual);
        }
        return especialidades;
    }

    // HELPERS
    @Override
    public String toString() {
        String data = "\nEspecialidad:{ \n"
                + "     \"id\": " + this.id + ",\n"
                + "     \"nombre\": \"" + this.nombre + "\",\n"
                + "     \"descripcion\": \"" + this.descripcion + "\"\n"
                + "     }";
        return data;
    }

    public boolean exists(String nombre) throws SQLException {
        String sql = "SELECT * FROM especialidades WHERE nombre=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, nombre.toUpperCase().trim());
        rs = ps.executeQuery();
        return rs.next();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
