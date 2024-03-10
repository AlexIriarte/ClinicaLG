package Negocio;

import Datos.DComando;
import Datos.DUser;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class NUser {

    private final DUser dUser;

    public NUser() throws SQLException {
        this.dUser = new DUser();

    }

    public void addUser(List<String> userData) throws SQLException, ParseException, Exception {
        if (userData.isEmpty()) {
            throw new SQLException("¡Datos del usuario vacíos!");
        }

        dUser.save(new DUser(
                userData.get(0), // CI
                userData.get(1), // Nombre
                userData.get(2), // Fecha de nacimiento
                userData.get(3), // Género
                userData.get(4), // Teléfono
                userData.get(5), // Email
                userData.get(6), // Usuario
                userData.get(7), // Contraseña
                userData.get(8), // Permiso
                userData.get(9) // Rol
        ));
    }

    public void editUser(List<String> userData) throws SQLException, ParseException, Exception {
        if (userData.isEmpty()) {
            throw new SQLException("¡Datos del usuario vacíos!");
        }

        dUser.update(Integer.parseInt(userData.get(0)), new DUser(
                userData.get(1), // CI
                userData.get(2), // Nombre
                userData.get(3), // Fecha de nacimiento
                userData.get(4), // Género
                userData.get(5), // Teléfono
                userData.get(6), // Email
                userData.get(7), // Usuario
                userData.get(8), // Contraseña
                userData.get(9), // Permiso
                userData.get(10) // Rol
        ));
    }

    public void delUser(List<String> userData) throws SQLException {
        if (userData.isEmpty()) {
            throw new SQLException("¡Datos del usuario vacíos!");
        }

        dUser.delete(Integer.parseInt(userData.get(0)));
    }

    public String[] getUserById(List<String> userData) throws SQLException {
        return dUser.getById(Integer.parseInt(userData.get(0)));
    }

    public String[] getUserByName(List<String> parametros) throws SQLException, ParseException {
        return this.dUser.getByName(parametros.get(0));
    }

    public String[] getUsuarioByEmail(List<String> userData) throws SQLException {
        return dUser.getByEmail(userData.get(0));
    }

    public String getPermiso(String email) throws SQLException {
        return dUser.getPermiso(email);
    }

    public ArrayList<String[]> getUsers() throws SQLException {
        return dUser.getAll();
    }

    public boolean esMismoUser(int id, String email) throws SQLException, ParseException {
        String[] user = dUser.getByEmailaAndId(id, email);
        boolean res = user != null && user.length > 0;
        System.out.println("MISMO USUARIO => " + res);
        return user != null && user.length > 0;
    }

    public String getRol() {
        return dUser.getRol();
    }

    public ArrayList<String[]> getComandos(List<String> parametros) throws SQLException, ParseException {
        DComando dComando = new DComando();
        return (ArrayList<String[]>) dComando.listarComplete();
    }

    // Resto de los métodos
}
