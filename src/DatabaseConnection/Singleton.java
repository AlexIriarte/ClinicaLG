/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Singleton {

    private static Singleton singletonConexion;
    private final PostgresConnection conexion;
    public Connection pgAdmin;

    public Singleton() throws SQLException {
        this.conexion = new PostgresConnection();
        this.pgAdmin = conexion.getConnection();
    }

    public static Singleton getInstancia() throws SQLException {
        if (singletonConexion == null) {
            singletonConexion = new Singleton();
        }

        return singletonConexion;
    }
}
