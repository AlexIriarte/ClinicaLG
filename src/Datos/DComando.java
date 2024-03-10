/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Config.env;
import DatabaseConnection.Singleton;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DComando {

    private int id;
    private String cu;
    private String accion;
    private String parametro;
    private String ejemplo;
    private String asunto;

    private PreparedStatement ps; //para ejecutar la consulta
    private ResultSet rs;         // para leer los resultados
    private static Singleton s;
    private static String esp = "%20";
    private static String salto = "%0A";
    public static String[] tablas = {
        "almacenes",
        "areas_medicas",
        "categorias",
        "citas",
        "comado",
        "especialidades",
        "horarios",
        "pagos",
        "productos",
        "productos_almacen",
        "users",
        "users_especialidades"
    };

    public DComando(String cu, String accion, String parametro, String ejemplo) throws SQLException {
        this.cu = cu;
        this.accion = accion;
        this.parametro = parametro;
        this.ejemplo = ejemplo;
        this.s = Singleton.getInstancia();
    }

    public DComando() throws SQLException {
        this(null, null, null, null);
    }

    public List<String[]> listar() throws SQLException {
        List<String[]> lista = new ArrayList<>();
        String query = "SELECT * FROM comando ORDER BY id ASC";
        ps = s.pgAdmin.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(new String[]{
                rs.getString("cu"),
                rs.getString("accion"),
                rs.getString("parametro"),
                rs.getString("ejemplo"),});
        }
        return lista;
    }

    public static List<String[]> listarComplete() throws SQLException {
        String mailto, subject, body, newLinea, espacio, titulo, query;
        //PreparedStatement ps; //para ejecutar la consulta
        //ResultSet rs;         // para leer los resultados        
        List<String[]> lista = new ArrayList<>();

        newLinea = "%0A";
        espacio = "%20";
        titulo = "MANUAL DE EMAIL SYSTEM TECNOWEB \"CLINICA LA GUARDIA\"";

        mailto = env.EMAIL_SERVER;
        subject = "ADD" + espacio + "USER";
        body = "hola" + newLinea + "como";

        query = "SELECT * FROM comando ORDER BY id ASC";
        
        PreparedStatement ps = s.pgAdmin.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            String res = getBody(rs.getString("asunto"));
            i++;
//            System.out.println(i + "---listarComplete\n" + res + "\n");
            lista.add(new String[]{
                rs.getString("cu"),
                rs.getString("accion"),
                rs.getString("parametro"),
                rs.getString("ejemplo"),
                "<a  type=\"submit\"  class=\"class-button\" style=\"color: white;\""
                + "href=\"mailto:" + mailto + "?subject=" + rs.getString("asunto") + "&body=" + res + "\">Solicitar Comandos</a>"
            });
        }
        return lista;
    }

    private static String getBody(String asunto) throws SQLException {
        String r;
        int pos, p;
        if (asunto == null) {
            r = "";
        } else {
            pos = asunto.indexOf('[');
            r = "";
            if (pos >= 0) {
                for (String e : DComando.tablas) {
                    p = asunto.indexOf("id_" + e, pos);
                    if (p >= 0) { //existe 
                        r = r + "\n" + e.toUpperCase() + ":[";
                        String datos = getTable(e);
                        if (datos.isEmpty()) {
                            datos = esp + "no" + esp + "hay" + esp + "datos" + esp;
                        } else {
                            r += "\n";
                        }
                        r = r + datos + "]"
                          + salto + "//------------------------------------------------------" + salto;
                    }
                }
            }
        }
        return r;
    }

    private static String getTable(String tabla) {
        String col, query, r;
        PreparedStatement ps; //para ejecutar la consulta
        ResultSet rs;         // para leer los resultados  
        r = "";
        col = "";
        switch (tabla) {
            case "users": {
                col = "id,ci,nombre";
                break;
            }
 
        }
        if (!col.isEmpty()) {
            try {
                query = "SELECT " + col + " FROM " + tabla + " ORDER BY id ASC";
                ps = s.pgAdmin.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
//                    r = r + "\n"+esp+"{\n"
//                            + esp+esp+col.substring(0, 2) + ":"+ esp + rs.getInt(1) + ",\n"
//                            + esp+esp+col.substring(3, col.length()) + ":"+esp + rs.getString(2) + ",\n"
//                            + esp+"},\n";
                    r = r + salto + esp + "{" + salto
                            + esp + esp + esp + esp + col.substring(0, 2) + ":" + esp + rs.getInt(1) + "," + salto
                            + esp + esp + esp + esp + col.substring(3, col.length()) + ":" + esp + rs.getString(2) + "," + salto
                            + esp + "}," + salto;
                }
            } catch (SQLException e) {
                System.err.println("getTable" + e.getMessage());
            }
        }

        return r;
    }
}
