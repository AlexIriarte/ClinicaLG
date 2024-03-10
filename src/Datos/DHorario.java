package Datos;

import DatabaseConnection.Singleton;
import Utils.DateString;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class DHorario {

    private int id;
    private int dia;
    private boolean activo;
    private String turno_manha_inicio;
    private String turno_manha_fin;
    private String turno_tarde_inicio;
    private String turno_tarde_fin;
    private int especialidad_id;
    private String created_at;
    private String updated_at;

    PreparedStatement ps; // para ejecutar la consulta
    ResultSet rs; // para leer los resultados
    Singleton s = Singleton.getInstancia();

    public DHorario() throws SQLException {
        this.id = -1;
        this.dia = 0;
        this.activo = false;
        this.turno_manha_inicio = null;
        this.turno_manha_fin = null;
        this.turno_tarde_inicio = null;
        this.turno_tarde_fin = null;
        this.especialidad_id = -1;
        this.created_at = null;
        this.updated_at = null;
    }

    public DHorario(int dia, boolean activo, String turno_manha_inicio, String turno_manha_fin, String turno_tarde_inicio,
            String turno_tarde_fin, int especialidad_id) throws SQLException {
        this.dia = dia;
        this.activo = activo;
        this.turno_manha_inicio = turno_manha_inicio;
        this.turno_manha_fin = turno_manha_fin;
        this.turno_tarde_inicio = turno_tarde_inicio;
        this.turno_tarde_fin = turno_tarde_fin;
        this.especialidad_id = especialidad_id;
    }

    // CRUD
    public void save(DHorario newHorario) throws SQLException, ParseException {
        String[] horarioActual = this.getByEspecialidadIdAndDia(newHorario.especialidad_id, newHorario.dia);
        if (horarioActual != null) {
            this.update(Integer.parseInt(horarioActual[0]), newHorario);
            return;
        }
        String sql = "INSERT INTO horarios(dia, activo, turno_mananha_inicio, turno_mananha_fin, turno_tarde_inicio, turno_tarde_fin, especialidad_id,created_at, updated_at)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?,CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, newHorario.getDia());
        ps.setBoolean(2, newHorario.isActivo());
        ps.setTime(3, DateString.StringToTimeSQL(newHorario.getTurno_manha_inicio()));
        ps.setTime(4, DateString.StringToTimeSQL(newHorario.getTurno_manha_fin()));
        ps.setTime(5, DateString.StringToTimeSQL(newHorario.getTurno_tarde_inicio()));
        ps.setTime(6, DateString.StringToTimeSQL(newHorario.getTurno_tarde_fin()));
        ps.setInt(7, newHorario.getEspecialidad_id());

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DHorario.save => ");
            throw new SQLException();
        }
    }

    public void update(int idHorario, DHorario editHorario) throws SQLException, ParseException {
        String sql = "UPDATE horarios SET dia = ?, activo = ?, turno_mananha_inicio = ?, turno_mananha_fin = ?, turno_tarde_inicio = ?, turno_tarde_fin = ?, "
                + "especialidad_id = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, editHorario.getDia());
        ps.setBoolean(2, editHorario.isActivo());
        ps.setTime(3, DateString.StringToTimeSQL(editHorario.getTurno_manha_inicio()));
        ps.setTime(4, DateString.StringToTimeSQL(editHorario.getTurno_manha_fin()));
        ps.setTime(5, DateString.StringToTimeSQL(editHorario.getTurno_tarde_inicio()));
        ps.setTime(6, DateString.StringToTimeSQL(editHorario.getTurno_tarde_fin()));
        ps.setInt(7, editHorario.getEspecialidad_id());
        ps.setInt(8, idHorario);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DHorario.update => ");
            throw new SQLException();
        }
    }

    public void delete(int idHorario) throws SQLException {
        String sql = "DELETE FROM horarios WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, idHorario);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DHorario.delete => ");
            throw new SQLException();
        }
    }

    public String[] getByEspecialidadIdAndDia(int especialidadId, int diaABuscar) throws SQLException {
        String[] horario = null;
        String sql = ""
                + "SELECT h.id,"
                + " CASE h.dia"
                + "     WHEN 1 THEN 'LUNES'"
                + "     WHEN 2 THEN 'MARTES'"
                + "     WHEN 3 THEN 'MIÉRCOLES'"
                + "     WHEN 4 THEN 'JUEVES'"
                + "     WHEN 5 THEN 'VIERNES'"
                + "     WHEN 6 THEN 'SÁBADO'"
                + "     WHEN 7 THEN 'DOMINGO'"
                + " END as dia,"
                + " CONCAT(h.turno_mananha_inicio, ' - ', h.turno_mananha_fin) as turno_mañana,"
                + " CONCAT(h.turno_tarde_inicio, ' - ', h.turno_tarde_fin) as turno_tarde,"
                + " e.nombre as especialidad"
                + " FROM horarios h, especialidades e"
                + " WHERE h.especialidad_id = e.id AND e.id = ? AND h.dia = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, especialidadId);
        ps.setInt(2, diaABuscar);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            horario = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("dia"),
                set.getString("turno_mañana"),
                set.getString("turno_tarde"),
                set.getString("especialidad"), 
                //set.getString("created_at"),
                //set.getString("updated_at")
            };
        }
        return horario;
    }

    public ArrayList<String[]> getAll() throws SQLException {
        ArrayList<String[]> horarios = new ArrayList();
        String sql = "SELECT * FROM horarios ORDER BY id ASC";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] horarioActual = new String[]{
                String.valueOf(rs.getInt("id")),
                //String.valueOf(rs.getInt("dia")),
                this.getLabelDiaById(rs.getInt("dia")),
                String.valueOf(rs.getBoolean("activo")),
                rs.getString("turno_mananha_inicio"),
                rs.getString("turno_mananha_fin"),
                rs.getString("turno_tarde_inicio"),
                rs.getString("turno_tarde_fin"),
                String.valueOf(rs.getInt("especialidad_id")), //                rs.getString("created_at"),
            //                rs.getString("updated_at")
            };
            horarios.add(horarioActual);
        }
        return horarios;
    }

    // HELPERS
    @Override
    public String toString() {
        String data
                = "\nHorario:{ \n"
                + "     \"id\": " + this.id + ",\n"
                + "     \"dia\": " + this.dia + ",\n"
                + "     \"activo\": " + this.activo + ",\n"
                + "     \"turno_manha_inicio\": \"" + this.turno_manha_inicio + "\",\n"
                + "     \"turno_manha_fin\": \"" + this.turno_manha_fin + "\",\n"
                + "     \"turno_tarde_inicio\": \"" + this.turno_tarde_inicio + "\",\n"
                + "     \"turno_tarde_fin\": \"" + this.turno_tarde_fin + "\",\n"
                + "     \"especialidad_id\": " + this.especialidad_id + ",\n"
                + "     \"created_at\": \"" + this.created_at + "\",\n"
                + "     \"updated_at\": \"" + this.updated_at + "\"\n"
                + "     }";
        return data;
    }

    public String getLabelDiaById(int dia) {
        String[] dias = {"LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "SABADO", "DOMINGO"};
        return dias[dia - 1];
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getTurno_manha_inicio() {
        return turno_manha_inicio;
    }

    public void setTurno_manha_inicio(String turno_manha_inicio) {
        this.turno_manha_inicio = turno_manha_inicio;
    }

    public String getTurno_manha_fin() {
        return turno_manha_fin;
    }

    public void setTurno_manha_fin(String turno_manha_fin) {
        this.turno_manha_fin = turno_manha_fin;
    }

    public String getTurno_tarde_inicio() {
        return turno_tarde_inicio;
    }

    public void setTurno_tarde_inicio(String turno_tarde_inicio) {
        this.turno_tarde_inicio = turno_tarde_inicio;
    }

    public String getTurno_tarde_fin() {
        return turno_tarde_fin;
    }

    public void setTurno_tarde_fin(String turno_tarde_fin) {
        this.turno_tarde_fin = turno_tarde_fin;
    }

    public int getEspecialidad_id() {
        return especialidad_id;
    }

    public void setEspecialidad_id(int especialidad_id) {
        this.especialidad_id = especialidad_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
