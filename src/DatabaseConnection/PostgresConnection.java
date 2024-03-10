/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseConnection;

import Config.env;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class PostgresConnection {

    private static final String DRIVER = "jdbc:postgresql://";

    private Connection connection;
    private final String user;
    private final String password; //root
    private final String host;
    private final String port;
    private final String database;
    private final String url;

    public PostgresConnection() {
        this.user = env.USER;
        this.password = env.PASSWORD;
        this.host = env.HOST;
        this.port = env.PORT;
        this.database = env.DATABASE;
        this.url = DRIVER + host + ":" + port + "/" + database;
    }

//    public PostgresConnection(String user, String password, String host, String port, String database) {
//        this.user = user;
//        this.password = password;
//        this.host = host;
//        this.port = port;
//        this.database = database;        
//        this.url = DRIVER + host + ":" + port + "/" + database; //127.0.0.1:5432/tecno_bd
//    }
    public Connection connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.err.println("Class SqlConnection.java dice: "
                    + "Ocurrio un error al momento de establecer una conexion connect()");
        }
        return connection;
    }

    public Connection getConnection() throws SQLException{
        try {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
        }catch(SQLException e){
            throw new SQLException("Error en la conexion a la base de datos");
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println("Class SqlConnection.java dice:"
                    + "Ocurrio un error al momento de cerrar la conexion closeConnection()");
        }
    }
}
