package Datos;

import DatabaseConnection.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DProductoAlmacen {

    Singleton s;
    String table;

    public DProductoAlmacen() throws SQLException {
        s = Singleton.getInstancia();
        this.table = "productos_almacen";
    }

    public void save(int stock, int producto_id, int almacen_id) throws SQLException, ParseException {
        String query = "INSERT INTO " + this.table + "(stock, producto_id, almacen_id)" + " values(?,?,?)";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setInt(1, stock);
        ps.setInt(2, producto_id);
        ps.setInt(3, almacen_id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DProductoAlmacen.java dice: "
                    + "Ocurrio un error al insertar un registro");
            throw new SQLException();
        }
    }

    public void update(int id, int stock, int producto_id, int almacen_id) throws SQLException, ParseException {
        if (this.getById(id) == null) {
            throw new SQLException("Error en DProductoAlmacen.update => id: " + id + " no encontrado!");
        }
        String query = "UPDATE " + this.table + " SET stock=?, producto_id=?, almacen_id=?" + " WHERE id=?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setInt(1, stock);
        ps.setInt(2, producto_id);
        ps.setInt(3, almacen_id);
        ps.setInt(4, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DProductoAlmacen.java dice: "
                    + "Ocurrio un error al modificar un registro");
            throw new SQLException();
        }
    }

    public void delete(int id) throws SQLException, ParseException {
        String query = "DELETE FROM " + this.table + " WHERE id=?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DProductoAlmacen.java dice: "
                    + "Ocurrio un error al eliminar un registro");
            throw new SQLException();
        }
    }

    public String[] getById(int id) throws SQLException {
        String[] registro = null;
        String query = "SELECT * FROM " + this.table + " WHERE id=?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            registro = new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getString("stock")),
                String.valueOf(set.getString("producto_id")),
                String.valueOf(set.getString("almacen_id")),};
        }
        return registro;
    }

    public List<String[]> getAll() throws SQLException {
        List<String[]> registros = new ArrayList<>();
        String query = "SELECT * FROM " + this.table;
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            registros.add(new String[]{
                String.valueOf(set.getInt("id")),
                String.valueOf(set.getString("stock")),
                String.valueOf(set.getString("producto_id")),
                String.valueOf(set.getString("almacen_id")),});
        }
        return registros;
    }

//    public boolean exists(String nombre) throws SQLException {
//        String query = "SELECT * FROM " + this.table + " WHERE nombre=?";
//        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
//        ps = s.pgAdmin.prepareStatement(query);
//        ps.setString(1, nombre.toUpperCase().trim());
//        ResultSet set = ps.executeQuery();
//        return set.next();
//    }
}
