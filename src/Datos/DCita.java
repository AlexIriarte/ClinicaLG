package Datos;

import DatabaseConnection.Singleton;
import Utils.DateString;
import enums.RolUsuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class DCita {

    private int id;
    private String fecha;
    private String hora;
    private String estado;
    private int medicoId;
    private int pacienteId;
    private int especialidadId;

    PreparedStatement ps; // para ejecutar la consulta
    ResultSet rs; // para leer los resultados
    Singleton s = Singleton.getInstancia();

    public DCita() throws SQLException {
        this.id = -1;
        this.fecha = null;
        this.hora = null;
        this.medicoId = -1;
        this.pacienteId = -1;
        this.especialidadId = -1;
    }

    public DCita(String fecha, String hora, String estado, int medicoId, int pacienteId, int especialidadId) throws SQLException {
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.especialidadId = especialidadId;
    }

    public DCita(String fecha, String hora, int medicoId, int pacienteId, int especialidadId) throws SQLException {
        this.fecha = fecha;
        this.hora = hora;
        this.estado = "PENDIENTE";
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.especialidadId = especialidadId;
    }

    // CRUD
    public void save(DCita newCita) throws SQLException, ParseException {
        String sql = "INSERT INTO citas(fecha, hora, medico_id, paciente_id, especialidad_id) VALUES(?, ?, ?, ?, ?)";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setDate(1, DateString.StringToDateSQL(newCita.getFecha().toUpperCase().trim()));
        ps.setTime(2, DateString.StringToTimeSQL(newCita.getHora().toUpperCase().trim()));
        ps.setInt(3, newCita.getMedicoId());
        ps.setInt(4, newCita.getPacienteId());
        ps.setInt(5, newCita.getEspecialidadId());

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DCitas.save => ");
            throw new SQLException();
        }
    }

    public void update(int idCita, DCita editCita) throws SQLException, ParseException {

        if (this.getById(idCita) == null) {
            throw new SQLException("Error en DCita.update => id: " + idCita + " no encontrado!");
        }

        String sql = "UPDATE citas SET fecha = ?, hora = ?,estado=?, medico_id = ?, paciente_id = ?, especialidad_id = ? WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setDate(1, DateString.StringToDateSQL(editCita.getFecha()));
        ps.setTime(2, DateString.StringToTimeSQL(editCita.getHora()));
        ps.setString(3, editCita.getEstado().toUpperCase().trim());
        ps.setInt(4, editCita.getMedicoId());
        ps.setInt(5, editCita.getPacienteId());
        ps.setInt(6, editCita.getEspecialidadId());
        ps.setInt(7, idCita);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DCitas.update => ");
            throw new SQLException();
        }
    }

    public void delete(int idCita) throws SQLException {
        if (this.getById(idCita) == null) {
            throw new SQLException("Error en DCita.delete => id: " + idCita + " no encontrado!");
        }
        String sql = "DELETE FROM citas WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, idCita);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DCitas.delete => ");
            throw new SQLException();
        }
    }

    public String[] getById(int id) throws SQLException {
        String[] cita = null;
        String sql = ""
                + " SELECT"
                + " c.id,"
                + " c.fecha,"
                + " c.hora,"
                + " c.estado,"
                + " m.nombre as medico,"
                + " p.nombre as paciente,"
                + " e.nombre as especialidad"
                + " FROM citas c"
                + " JOIN users m ON c.medico_id = m.id"
                + " JOIN users p ON c.paciente_id = p.id"
                + " JOIN especialidades e ON c.especialidad_id = e.id"
                + " WHERE c.id = ?"
                + " ORDER BY c.fecha DESC, c.hora DESC";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            cita = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("fecha"),
                set.getString("hora"),
                set.getString("estado"),
                set.getString("medico"),
                set.getString("paciente"),
                set.getString("especialidad")
            };
        }
        return cita;
    }

    public ArrayList<String[]> getByFecha(String fechaABuscar) throws SQLException, ParseException {
        ArrayList<String[]> citas = new ArrayList();
        String sql = ""
                + " SELECT"
                + " c.id,"
                + " c.fecha,"
                + " c.hora,"
                + " c.estado,"
                + " m.nombre as medico,"
                + " p.nombre as paciente,"
                + " e.nombre as especialidad"
                + " FROM citas c"
                + " JOIN users m ON c.medico_id = m.id"
                + " JOIN users p ON c.paciente_id = p.id"
                + " JOIN especialidades e ON c.especialidad_id = e.id"
                + " WHERE c.fecha = ?"
                + " ORDER BY c.fecha DESC, c.hora DESC";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setDate(1, DateString.StringToDateSQL(fechaABuscar));

        ResultSet set = ps.executeQuery();
        while (set.next()) {
            String[] citaActual = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("fecha"),
                set.getString("hora"),
                set.getString("estado"),
                set.getString("medico"),
                set.getString("paciente"),
                set.getString("especialidad")
            };
            citas.add(citaActual);
        }
        return citas;
    }

    public ArrayList<String[]> getByDoctorIdAndFecha(int doctorId, String fechaABuscar) throws SQLException, ParseException {
        ArrayList<String[]> citas = new ArrayList();
        String sql = ""
                + " SELECT"
                + " c.id,"
                + " c.fecha,"
                + " c.hora,"
                + " c.estado,"
                + " m.nombre as medico,"
                + " p.nombre as paciente,"
                + " e.nombre as especialidad"
                + " FROM citas c"
                + " JOIN users m ON c.medico_id = m.id AND m.id = ?"
                + " JOIN users p ON c.paciente_id = p.id"
                + " JOIN especialidades e ON c.especialidad_id = e.id"
                + " WHERE c.fecha = ?"
                + " ORDER BY c.fecha DESC, c.hora DESC";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, doctorId);
        ps.setDate(2, DateString.StringToDateSQL(fechaABuscar));

        ResultSet set = ps.executeQuery();
        while (set.next()) {
            String[] citaActual = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("fecha"),
                set.getString("hora"),
                set.getString("estado"),
                set.getString("medico"),
                set.getString("paciente"),
                set.getString("especialidad")
            };
            citas.add(citaActual);
        }
        return citas;
    }

    public ArrayList<String[]> getByEspecialidadIdAndFecha(int especialidadId, String fechaABuscar) throws SQLException, ParseException {
        ArrayList<String[]> citas = new ArrayList();
        String sql = ""
                + " SELECT"
                + " c.id,"
                + " c.fecha,"
                + " c.hora,"
                + " c.estado,"
                + " m.nombre as medico,"
                + " p.nombre as paciente,"
                + " e.nombre as especialidad"
                + " FROM citas c"
                + " JOIN users m ON c.medico_id = m.id"
                + " JOIN users p ON c.paciente_id = p.id"
                + " JOIN especialidades e ON c.especialidad_id = e.id AND e.id = ?"
                + " WHERE c.fecha = ?"
                + " ORDER BY c.fecha DESC, c.hora DESC";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, especialidadId);
        ps.setDate(2, DateString.StringToDateSQL(fechaABuscar));

        ResultSet set = ps.executeQuery();
        while (set.next()) {
            String[] citaActual = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("fecha"),
                set.getString("hora"),
                set.getString("estado"),
                set.getString("medico"),
                set.getString("paciente"),
                set.getString("especialidad")
            };
            citas.add(citaActual);
        }
        return citas;
    }

    public ArrayList<String[]> getAll() throws SQLException, ParseException {
        ArrayList<String[]> citas = new ArrayList();
        String sql = ""
                + " SELECT"
                + " c.id,"
                + " c.fecha,"
                + " c.hora,"
                + " c.estado,"
                + " m.nombre as medico,"
                + " p.nombre as paciente,"
                + " e.nombre as especialidad"
                + " FROM citas c"
                + " JOIN users m ON c.medico_id = m.id"
                + " JOIN users p ON c.paciente_id = p.id"
                + " JOIN especialidades e ON c.especialidad_id = e.id"
                + " ORDER BY c.fecha DESC, c.hora DESC";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] citaActual = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("fecha"),
                rs.getString("hora"),
                rs.getString("estado"),
                rs.getString("medico"),
                rs.getString("paciente"),
                rs.getString("especialidad")
            };
            citas.add(citaActual);
        }
        return citas;
    }

    public ArrayList<String[]> getIngresosPorEspecialidad(String fechai, String fechaf) throws SQLException, ParseException {
        ArrayList<String[]> citas = new ArrayList();
        String sql = "SELECT "
                + "e.id AS especialidad_id, "
                + "e.nombre AS especialidad, "
                + "SUM(p.monto) AS ingreso_total "
                + "FROM especialidades e "
                + "LEFT JOIN citas c ON e.id = c.especialidad_id "
                + "LEFT JOIN pagos p ON c.id = p.cita_id "
                + "WHERE c.fecha BETWEEN ? AND ? "
                + "GROUP BY e.id, e.nombre "
                + "ORDER BY especialidad_id;";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setDate(1, DateString.StringToDateSQL(fechai));
        ps.setDate(2, DateString.StringToDateSQL(fechaf));

        rs = ps.executeQuery();
        while (rs.next()) {
            String[] citaActual = new String[]{
                String.valueOf(rs.getInt("especialidad_id")),
                rs.getString("especialidad"),
                String.valueOf(rs.getFloat("ingreso_total")),};
            citas.add(citaActual);
        }
        return citas;
    }

    public ArrayList<String[]> getCantidadCitasDoctor(String fechai, String fechaf) throws SQLException, ParseException {
        ArrayList<String[]> citas = new ArrayList();
        String sql = "SELECT "
                + "u.id AS doctor_id, "
                + "u.nombre AS nombre, "
                + "COUNT(c.id) AS cantidad_citas "
                + "FROM users u "
                + "JOIN citas c ON u.id = c.medico_id "
                + "WHERE u.rol = '" + RolUsuario.DOCTOR.getLabel() + "'"
                + "AND c.fecha BETWEEN ? AND ? "
                + "GROUP BY u.id, u.nombre "
                + "ORDER BY doctor_id;";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setDate(1, DateString.StringToDateSQL(fechai));
        ps.setDate(2, DateString.StringToDateSQL(fechaf));

        rs = ps.executeQuery();
        while (rs.next()) {
            String[] citaActual = new String[]{
                String.valueOf(rs.getInt("doctor_id")),
                rs.getString("nombre"),
                String.valueOf(rs.getString("cantidad_citas"))
            };
            citas.add(citaActual);
        }
        return citas;
    }

    public ArrayList<String[]> getIngresosDoctor(String fechai, String fechaf) throws SQLException, ParseException {
        ArrayList<String[]> citas = new ArrayList();
        String sql = "SELECT "
                + "u.id AS doctor_id, "
                + "u.nombre AS nombre, "
                + "SUM(p.monto) AS ingreso_total "
                + "FROM users u "
                + "JOIN citas c ON u.id = c.medico_id "
                + "JOIN pagos p ON c.id = p.cita_id "
                + "WHERE u.rol = '" + RolUsuario.DOCTOR.getLabel() + "'"
                + "AND c.fecha BETWEEN ? AND ? "
                + "GROUP BY u.id, u.nombre "
                + "ORDER BY doctor_id;";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setDate(1, DateString.StringToDateSQL(fechai));
        ps.setDate(2, DateString.StringToDateSQL(fechaf));

        rs = ps.executeQuery();
        while (rs.next()) {
            String[] citaActual = new String[]{
                String.valueOf(rs.getInt("doctor_id")),
                rs.getString("nombre"),
                String.valueOf(rs.getFloat("ingreso_total"))
            };
            citas.add(citaActual);
        }
        return citas;
    }

    // Helpers
    @Override
    public String toString() {
        String data = "\nCita:{ \n"
                + "     \"id\": " + this.id + ",\n"
                + "     \"fecha\": \"" + this.fecha + "\",\n"
                + "     \"hora\": \"" + this.hora + "\",\n"
                + "     \"estado\": \"" + this.estado + "\",\n"
                + "     \"medico_id\": " + this.medicoId + ",\n"
                + "     \"paciente_id\": " + this.pacienteId + ",\n"
                + "     \"especialidad_id\": " + this.especialidadId + "\n"
                + "     }";
        return data;
    }

    public boolean exists(int userId, String fecha, String hora) throws SQLException {
        String sql = "SELECT * FROM citas WHERE medico_id=? AND fecha=? AND hora=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setString(2, fecha.toUpperCase().trim());
        ps.setString(3, hora.toUpperCase().trim());
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(int especialidadId) {
        this.especialidadId = especialidadId;
    }
}
