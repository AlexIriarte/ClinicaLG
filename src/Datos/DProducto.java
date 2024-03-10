package Datos;

import DatabaseConnection.Singleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DProducto {

    Singleton s;
    String table;
    String table_categoria;

    public DProducto() throws SQLException {
        s = Singleton.getInstancia();
        this.table = "productos";
        this.table_categoria = "categorias";
    }

    public void save(String nombre, String marca, float precio, int user_id, int categoria_id) throws SQLException, ParseException {
        String query = "INSERT INTO " + this.table + "(nombre, marca, precio, user_id, categoria_id)" + " values(?,?,?,?,?)";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, marca);
        ps.setFloat(3, precio);
        ps.setInt(4, user_id);
        ps.setInt(5, categoria_id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DProducto.java dice: "
                    + "Ocurrio un error al insertar un registro");
            throw new SQLException();
        }
    }

    public void update(int id, String nombre, String marca, float precio, int user_id, int categoria_id) throws SQLException, ParseException {
        if (this.getById(id) == null) {
            throw new SQLException("Error en DProducto.update => id: " + id + " no encontrado!");
        }
        String query = "UPDATE " + this.table + " SET nombre=?, marca=?, precio=?, user_id=?, categoria_id=?" + " WHERE id=?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, marca);
        ps.setFloat(3, precio);
        ps.setInt(4, user_id);
        ps.setInt(5, categoria_id);
        ps.setInt(6, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DProducto.java dice: "
                    + "Ocurrio un error al modificar un registro");
            throw new SQLException();
        }
    }

    public void delete(int id) throws SQLException, ParseException {
        String query = "DELETE FROM " + this.table + " WHERE id=?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setInt(1, id);

        if (ps.executeUpdate() == 0) {
            System.err.println("Class DProducto.java dice: "
                    + "Ocurrio un error al eliminar un registro");
            throw new SQLException();
        }
    }

    public String[] getById(int id) throws SQLException {
        String[] registro = null;
        //String query = "SELECT * FROM " + this.table + "," + this.table_categoria +  " WHERE id=? ";
        String query = String.format("SELECT %s.*, categorias.nombre as categoria FROM %s, %s"
                + " WHERE productos.categoria_id = categorias.id AND productos.id=?", this.table, this.table, this.table_categoria);
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            registro = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("marca"),
                String.valueOf(set.getString("precio")),
                String.valueOf(set.getString("user_id")),
                //String.valueOf(set.getString("categoria_id")),
                set.getString("categoria"),};
        }
        return registro;
    }

    public String[] getByName(String name) throws SQLException {
        String[] registro = null;
        String query = ""
                + " SELECT productos.*, categorias.nombre as categoria"
                + " FROM productos, categorias"
                + " WHERE productos.categoria_id = categorias.id"
                + " AND productos.nombre=?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps.setString(1, name);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            registro = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("marca"),
                String.valueOf(set.getString("precio")),
                String.valueOf(set.getString("user_id")),
                //String.valueOf(set.getString("categoria_id")),
                set.getString("categoria"),};
        }
        return registro;
    }

    public List<String[]> getStock(int id) throws SQLException {
        List<String[]> registros = new ArrayList<>();
        String sql = ""
                + "SELECT "
                + "p.id, "
                + "p.nombre, "
                + "p.marca, "
                + "p.precio, "
                + "c.nombre as categoria, "
                + "a.nombre as almacen, "
                + "pa.stock "
                + "FROM productos p "
                + "JOIN categorias c ON p.categoria_id = c.id "
                + "JOIN productos_almacen pa ON pa.producto_id = p.id "
                + "JOIN almacenes a ON pa.almacen_id = a.id "
                + "WHERE p.id = ?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        while (set.next()) {
            registros.add(new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("marca"),
                String.valueOf(set.getString("precio")),
                set.getString("categoria"),
                set.getString("almacen"),
                String.valueOf(set.getString("stock"))
            });
        }
        return registros;
    }

    public List<String[]> getAll() throws SQLException {
        List<String[]> registros = new ArrayList<>();
        String query = "SELECT * FROM " + this.table;
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ResultSet set = ps.executeQuery();
        while (set.next()) {
            registros.add(new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("nombre"),
                set.getString("marca"),
                String.valueOf(set.getString("precio")),
                String.valueOf(set.getString("user_id")),
                String.valueOf(set.getString("categoria_id")),});
        }
        return registros;
    }

    public boolean exists(String nombre) throws SQLException {
        String query = "SELECT * FROM " + this.table + " WHERE nombre=?";
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ps = s.pgAdmin.prepareStatement(query);
        ps.setString(1, nombre.toUpperCase().trim());
        ResultSet set = ps.executeQuery();
        return set.next();
    }
}
