package Datos;

import DatabaseConnection.Singleton;
import Utils.DateString;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class DUser {

    private int id;
    private String ci;
    private String nombre;
    private String fecha_nac;
    private String genero;
    private String telefono;
    private String email;
    private String usuario;
    private String password;
    private String permiso;
    private String rol;
    private String created_at;
    private String updated_at;

    PreparedStatement ps; // para ejecutar la consulta
    ResultSet rs; // para leer los resultados
    Singleton s = Singleton.getInstancia();

    public DUser() throws SQLException {
        this.id = -1;
        this.ci = null;
        this.nombre = null;
        this.fecha_nac = null;
        this.genero = null;
        this.telefono = null;
        this.email = null;
        this.usuario = null;
        this.password = null;
        this.permiso = null;
        this.rol = null;
        this.created_at = null;
        this.updated_at = null;
    }

    public DUser(String ci, String nombre, String fecha_nac, String genero, String telefono, String email, String usuario,
            String password, String permiso, String rol) throws SQLException {
        this.ci = ci;
        this.nombre = nombre;
        this.fecha_nac = fecha_nac;
        this.genero = genero;
        this.telefono = telefono;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
        this.permiso = permiso;
        this.rol = rol;
    }

    // CRUD
    public void save(DUser newUser) throws SQLException, ParseException, Exception {
        String sql = "INSERT INTO users(ci, nombre, fecha_nac, genero, telefono, email, usuario, password, permiso, rol, created_at, updated_at) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, newUser.getCi().toUpperCase().trim());
        ps.setString(2, newUser.getNombre().toUpperCase().trim());
        ps.setDate(3, DateString.StringToDateSQL(newUser.getFecha_nac()));
        ps.setString(4, newUser.getGenero().toUpperCase().trim());
        ps.setString(5, newUser.getTelefono().toUpperCase().trim());
        ps.setString(6, newUser.getEmail().trim());
        ps.setString(7, newUser.getUsuario().toUpperCase().trim());
        ps.setString(8, newUser.getPassword().toUpperCase().trim()); // Aquí se guarda la contraseña encriptada
        ps.setString(9, newUser.getPermiso().toUpperCase().trim());
        ps.setString(10, newUser.getRol().toUpperCase().trim());

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DUser.save => ");
            throw new SQLException();
        }
    }

    public void update(int idUser, DUser editUser) throws SQLException, ParseException, Exception {
        String sql = "UPDATE users SET ci = ?, nombre = ?, fecha_nac = ?, genero = ?, telefono = ?, email = ?, usuario = ?, "
                + "password = ?, permiso = ?, rol = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, editUser.getCi().toUpperCase().trim());
        ps.setString(2, editUser.getNombre().toUpperCase().trim());
        ps.setDate(3, DateString.StringToDateSQL(editUser.getFecha_nac()));
        ps.setString(4, editUser.getGenero().toUpperCase().trim());
        ps.setString(5, editUser.getTelefono().toUpperCase().trim());
        ps.setString(6, editUser.getEmail().trim());
        ps.setString(7, editUser.getUsuario().toUpperCase().trim());
        ps.setString(8, editUser.getPassword().toUpperCase().trim()); // Aquí se guarda la contraseña encriptada
        ps.setString(9, editUser.getPermiso().toUpperCase().trim());
        ps.setString(10, editUser.getRol().toUpperCase().trim());
        ps.setInt(11, idUser);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DUser.update => ");
            throw new SQLException();
        }
    }

    public void delete(int idUser) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, idUser);

        if (ps.executeUpdate() == 0) {
            System.err.println("Error en DUser.delete => ");
            throw new SQLException();
        }
    }

    public String[] getById(int id) throws SQLException {
        String[] user = null;
        String sql = "SELECT * FROM users WHERE id=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            user = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("ci"),
                set.getString("nombre"),
                set.getString("fecha_nac"),
                set.getString("genero"),
                set.getString("telefono"),
                set.getString("email"),
                set.getString("usuario"),
//                set.getString("password"),
                set.getString("permiso"),
                set.getString("rol"),
//                set.getString("created_at"),
//                set.getString("updated_at")
            };
        }
        return user;
    }

    public String[] getByName(String name) throws SQLException {
        String[] usuario = null;
        String sql = "SELECT * FROM users WHERE usuario=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, name.toUpperCase().trim());

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            usuario = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("ci"),
                set.getString("nombre"),
                set.getString("fecha_nac"),
                set.getString("genero"),
                set.getString("telefono"),
                set.getString("email"),
                set.getString("usuario"),
//                set.getString("password"),
                set.getString("permiso"),
                set.getString("rol"),
            };
        }
        return usuario;
    }

    public String[] getByEmail(String email) throws SQLException {
        String[] user = null;
        String sql = "SELECT * FROM users WHERE email=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, email.trim());

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            user = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("ci"),
                set.getString("nombre"),
                set.getString("fecha_nac"),
                set.getString("genero"),
                set.getString("telefono"),
                set.getString("email"),
                set.getString("usuario"),
//                set.getString("password"), 
                set.getString("permiso"),
                set.getString("rol"),
//                set.getString("created_at"),
//                set.getString("updated_at")
            };
        }
        return user;
    }

    public String getPermiso(String email) throws SQLException {
        String permisoUser = null;
        String sql = "SELECT * FROM users WHERE email=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, email.trim());

        ResultSet set = ps.executeQuery();
        if (set.next()) {
            System.out.println(email + " PERMISO => " + set.getString("permiso"));
            permisoUser = set.getString("permiso");

        }
        return permisoUser;
    }

    public String[] getByEmailaAndId(int id, String email) throws SQLException {
        String[] usuario = null;
        String sql = "SELECT * FROM users WHERE email=? and id=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, email.trim());
        ps.setInt(2, id);
        ResultSet set = ps.executeQuery();
        if (set.next()) {
            usuario = new String[]{
                String.valueOf(set.getInt("id")),
                set.getString("ci"),
                set.getString("nombre"),
                set.getString("fecha_nac"),
                set.getString("genero"),
                set.getString("telefono"),
                set.getString("email"),
                set.getString("usuario"),
//                set.getString("password"),
                set.getString("permiso"),
                set.getString("rol"),
            };
        }
        return usuario;
    }

    public ArrayList<String[]> getAll() throws SQLException {
        ArrayList<String[]> users = new ArrayList();
        String sql = "SELECT * FROM users ORDER BY id ASC";
        ps = s.pgAdmin.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()) {
            String[] userActual = new String[]{
                String.valueOf(rs.getInt("id")),
                rs.getString("ci"),
                rs.getString("nombre"),
                rs.getString("fecha_nac"),
                rs.getString("genero"),
                rs.getString("telefono"),
                rs.getString("email"),
                rs.getString("usuario"),
//                rs.getString("password"),
                rs.getString("permiso"),
                rs.getString("rol"),
//                rs.getString("created_at"),
//                rs.getString("updated_at")
            };
            users.add(userActual);
        }
        return users;
    }

    // Helpers
    @Override
    public String toString() {
        String data
                = "\nUser:{ \n"
                + "     \"id\": " + this.id + ",\n"
                + "     \"ci\": \"" + this.ci + "\",\n"
                + "     \"nombre\": \"" + this.nombre + "\",\n"
                + "     \"fecha_nac\": \"" + this.fecha_nac + "\",\n"
                + "     \"genero\": \"" + this.genero + "\",\n"
                + "     \"telefono\": \"" + this.telefono + "\",\n"
                + "     \"email\": \"" + this.email + "\",\n"
                + "     \"usuario\": \"" + this.usuario + "\",\n"
                + "     \"password\": \"" + this.password + "\",\n"
                + "     \"permiso\": \"" + this.permiso + "\",\n"
                + "     \"rol\": \"" + this.rol + "\",\n"
//                + "     \"created_at\": \"" + this.created_at + "\",\n"
//                + "     \"updated_at\": \"" + this.updated_at + "\"\n"
                + "     }";
        return data;
    }

    public boolean exists(String usuario) throws SQLException {
        String sql = "SELECT * FROM users WHERE usuario=?";
        ps = s.pgAdmin.prepareStatement(sql);
        ps.setString(1, usuario);
        rs = ps.executeQuery();
        return rs.next();
    }

    private String encrypt(String password) throws Exception {
        String letra = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        String clave = "";
        password = password.toUpperCase();
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            int pos = letra.indexOf(c);
            if (pos == -1) {
                clave = clave + c;
            } else {
                clave = clave + letra.charAt((pos + 3) % letra.length());

            }
        }
        return clave;

    }

    private String decrypt(String encryptedPassword) throws Exception {
        String letra = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";
        String desclave = "";
        encryptedPassword = encryptedPassword.toUpperCase();
        char c;
        for (int i = 0; i < encryptedPassword.length(); i++) {
            c = encryptedPassword.charAt(i);
            int pos = letra.indexOf(c);
            if (pos == -1) {
                desclave += c;
            } else {
                if (pos - 3 < 0) {
                    desclave += letra.charAt(letra.length() + (pos - 3));

                } else {
                    desclave += letra.charAt((pos - 3) % letra.length());

                }

            }
        }
        return desclave;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() throws Exception {
        return decrypt(password);
    }

    public void setPassword(String password) throws Exception {
        this.password = encrypt(password);
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
