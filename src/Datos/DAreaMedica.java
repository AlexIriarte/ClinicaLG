package Datos;

import DatabaseConnection.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class DAreaMedica {

    private int id;
    private String nombre;
    private String descripcion;
    private int responsableId;
    private int medicoId;

    PreparedStatement ps;
    ResultSet rs;
    Singleton s = Singleton.getInstancia();

    public DAreaMedica() throws SQLException {
        this.id = -1;
        this.nombre = null;
        this.descripcion = null;
        this.responsableId = -1;
        this.medicoId = -1;
    }

    public DAreaMedica(String nombre, String descripcion, int responsable_id, int medicoId) throws SQLException {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.responsableId = responsable_id;
        this.medicoId = medicoId;
    }

    public DAreaMedica(String nombre, String descripcion, int responsableId) throws SQLException {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.responsableId = responsableId;
        this.medicoId = -1;
    }

    // CRUD
    public void save(DAreaMedica newAreaMedica) throws SQLException {
        if (this.exists(newAreaMedica.nombre.toUpperCase().trim())) {
            String[] areaActual = this.getByName(newAreaMedica.nombre.toUpperCase().trim());
            this.update(Integer.parseInt(areaActual[0]), newAreaMedica);
            return;
        }

        String sql = "INSERT INTO areas_medicas(nombre, descripcion, responsable_id, medico_id) VALUES(?, ?, ?, ?)";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, newAreaMedica.getNombre().toUpperCase().trim());
        ps.setString(2, newAreaMedica.getDescripcion().toUpperCase().trim());
        ps.setInt(3, newAreaMedica.getResponsableId());
        if (newAreaMedica.getMedicoId() != -1) {
            ps.setInt(4, newAreaMedica.getMedicoId());
        } else {
            ps.setNull(4, java.sql.Types.INTEGER);
        }

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DAreaMedica.save => ");
            throw new SQLException();
        }
    }

    public void update(int idAreaMedica, DAreaMedica editAreaMedica) throws SQLException {
        if (this.getById(idAreaMedica) == null) {
            throw new SQLException("Error en DAreaMedica.update => id: " + idAreaMedica + " no encontrado!");
        }

        String sql = "UPDATE areas_medicas SET nombre = ?, descripcion = ?, responsable_id = ?, medico_id = ? WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, editAreaMedica.getNombre().toUpperCase().trim());
        ps.setString(2, editAreaMedica.getDescripcion().toUpperCase().trim());
        ps.setInt(3, editAreaMedica.getResponsableId());
        if (editAreaMedica.getMedicoId() != -1) {
            ps.setInt(4, editAreaMedica.getMedicoId());
        } else {
            ps.setNull(4, java.sql.Types.INTEGER);
        }
        ps.setInt(5, idAreaMedica);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DAreaMedica.update => ");
            throw new SQLException();
        }
    }

    public void delete(int idAreaMedica) throws SQLException {
        if (this.getById(idAreaMedica) == null) {
            throw new SQLException("Error en DAreaMedica.delete => id: " + idAreaMedica + " no encontrado!");
        }
        String sql = "DELETE FROM areas_medicas WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, idAreaMedica);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DAreaMedica.delete => ");
            throw new SQLException();
        }
    }

    public String[] getById(int id) throws SQLException {
        String[] areaMedica = null;
        String sql = ""
                + "SELECT ar.id, ar.nombre, ar.descripcion, responsable.nombre as responsable, COALESCE(medico.nombre, 'SIN MEDICO') as medico"
                + " FROM areas_medicas ar"
                + " JOIN users responsable ON ar.responsable_id = responsable.id"
                + " LEFT JOIN users medico ON ar.medico_id = medico.id"
                + " WHERE ar.id=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            areaMedica = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("descripcion"),
                set.getString("responsable"),
                set.getString("medico")

            };
        }
        return areaMedica;
    }

    public String[] getByName(String nombreABuscar) throws SQLException {
        String[] areaMedica = null;
        String sql = ""
                + "SELECT ar.id, ar.nombre, ar.descripcion, responsable.nombre as responsable, COALESCE(medico.nombre, 'SIN MEDICO') as medico"
                + " FROM areas_medicas ar"
                + " JOIN users responsable ON ar.responsable_id = responsable.id"
                + " LEFT JOIN users medico ON ar.medico_id = medico.id"
                + " WHERE ar.nombre=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, nombreABuscar.toUpperCase().trim());

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            areaMedica = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("descripcion"),
                set.getString("responsable"),
                set.getString("medico")

            };
        }
        return areaMedica;
    }

    public ArrayList<String[]> getAll() throws SQLException {
        ArrayList<String[]> areasMedicas = new ArrayList();
        String sql = ""
                + "SELECT ar.id, ar.nombre, ar.descripcion, responsable.nombre as responsable, COALESCE(medico.nombre, 'SIN MEDICO') as medico"
                + " FROM areas_medicas ar"
                + " JOIN users responsable ON ar.responsable_id = responsable.id"
                + " LEFT JOIN users medico ON ar.medico_id = medico.id"
                + " ORDER BY ar.id ASC";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] areaMedicaActual = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("responsable"),
                rs.getString("medico")
            };
            areasMedicas.add(areaMedicaActual);
        }
        return areasMedicas;
    }

    public void asignar(int id, int responsable_id) throws SQLException, ParseException {
        String sql = "UPDATE areas_medicas SET responsable_id=? WHERE id=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, responsable_id);
        ps.setInt(2, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DAreaMedica.asignar => ");
            throw new SQLException();
        }
    }

    // HELPERS
    @Override
    public String toString() {
        String data
                = "\nAreaMedica:{ \n"
                + "     \"id\": " + this.id + ",\n"
                + "     \"nombre\": \"" + this.nombre + "\",\n"
                + "     \"descripcion\": \"" + this.descripcion + "\"\n"
                + "     \"responsable_id\": \"" + this.responsableId + "\"\n"
                + "     \"medico_id\": \"" + this.medicoId + "\"\n"
                + "     }";
        return data;
    }

    public boolean exists(String nombre) throws SQLException {
        String sql = "SELECT * FROM areas_medicas WHERE nombre=?";
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

    public int getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(int responsableId) {
        this.responsableId = responsableId;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }
}
