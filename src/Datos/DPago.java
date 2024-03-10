package Datos;

import DatabaseConnection.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DPago {

    private int id;
    private float monto;
    private String fecha_hora;
    private String tipo;
    private int citaId;

    PreparedStatement ps; // para ejecutar la consulta
    ResultSet rs; // para leer los resultados
    Singleton s = Singleton.getInstancia();

    public DPago() throws SQLException {
        this.id = -1;
        this.monto = 0.0f;
        this.fecha_hora = null;
        this.tipo = null;
        this.citaId = -1;
    }

    public DPago(float monto, String fecha_hora, String tipo, int citaId) throws SQLException {
        this.monto = monto;
        this.fecha_hora = fecha_hora;
        this.tipo = tipo;
        this.citaId = citaId;
    }

    public DPago(float monto, String tipo, int citaId) throws SQLException {
        this.monto = monto;
        this.fecha_hora = null;
        this.tipo = tipo;
        this.citaId = citaId;
    }

    // CRUD
    public void save(DPago newPago) throws SQLException {
        String sql = "INSERT INTO pagos(monto, fecha_hora, tipo, cita_id) VALUES(?, CURRENT_TIMESTAMP, ?, ?)";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setFloat(1, newPago.getMonto());
        ps.setString(2, newPago.getTipo());
        ps.setInt(3, newPago.getCitaId());

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DPago.save => ");
            throw new SQLException();
        }
    }

    public void update(int idPago, DPago editPago) throws SQLException {
        if (this.getById(idPago) == null) {
            throw new SQLException("Error en DPago.update => id: " + idPago + " no encontrado!");
        }

        String sql = "UPDATE pagos SET monto = ?, tipo = ?, cita_id = ? WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setFloat(1, editPago.getMonto());
        //ps.setString(2, editPago.getFecha_hora());
        ps.setString(2, editPago.getTipo());
        ps.setInt(3, editPago.getCitaId());
        ps.setInt(4, idPago);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DPago.update => ");
            throw new SQLException();
        }
    }

    public void delete(int idPago) throws SQLException {
        if (this.getById(idPago) == null) {
            throw new SQLException("Error en DPago.delete => id: " + idPago + " no encontrado!");
        }
        String sql = "DELETE FROM pagos WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, idPago);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DPago.delete => ");
            throw new SQLException();
        }
    }

    public String[] getById(int id) throws SQLException {
        String[] pago = null;
//        String sql = "SELECT pagos.*, citas.* FROM pagos, citas, users"
//                + " WHERE pagos.cita_id=? = citas.id and citas.medico_id = users.id";
        String sql = ""
                + " SELECT"
                + " pa.id,"
                + " pa.monto,"
                + " pa.fecha_hora as fecha_pago,"
                + " pa.tipo,"
                + " c.fecha,"
                + " c.hora,"
                + " c.estado,"
                + " m.nombre as medico,"
                + " p.nombre as paciente,"
                + " e.nombre as especialidad"
                + " FROM pagos pa"
                + " INNER JOIN citas c ON pa.cita_id = c.id"
                + " INNER JOIN users m ON c.medico_id = m.id"
                + " INNER JOIN users p ON c.paciente_id = p.id"
                + " INNER JOIN especialidades e ON c.especialidad_id = e.id"
                + " WHERE pa.id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            pago = new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getFloat("monto")),
                set.getString("fecha_pago"),
                set.getString("tipo"),
                set.getString("fecha"),
                set.getString("hora"),
                set.getString("estado"),
                set.getString("medico"),
                set.getString("paciente"),
                set.getString("especialidad"),
            };
        }
        return pago;
    }

    public String[] getLastPaymentId() throws SQLException {
        String[] lastPaymentId = null;
        String sql = "SELECT MAX(id) FROM pagos";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();

        if (rs.next()) {
            lastPaymentId = new String[]{
                String.valueOf(rs.getInt(1))
            };
            return lastPaymentId;
        } else {
            throw new SQLException("Error al obtener el último ID de pago.");
        }
    }

    public ArrayList<String[]> getAll() throws SQLException {
        ArrayList<String[]> pagos = new ArrayList();
        String sql = "SELECT * FROM pagos ORDER BY id ASC";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] pagoActual = new String[]{
                String.valueOf(rs.getInt("id")),
                String.valueOf(rs.getFloat("monto")),
                rs.getString("fecha_hora"),
                rs.getString("tipo")
            };
            pagos.add(pagoActual);
        }
        return pagos;
    }

    // HELPERS
    @Override
    public String toString() {
        String data
                = "\nPago:{ \n"
                + "     \"id\": " + this.id + ",\n"
                + "     \"monto\": " + this.monto + ",\n"
                + "     \"fecha_hora\": \"" + this.fecha_hora + "\",\n"
                + "     \"tipo\": \"" + this.tipo + "\"\n"
                + "     }";
        return data;
    }

    public boolean exists(String tipo) throws SQLException {
        String sql = "SELECT * FROM pagos WHERE tipo=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, tipo.trim());
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

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCitaId() {
        return citaId;
    }

    public void setCitaId(int cita_id) {
        this.citaId = cita_id;
    }
}
