/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Datos.DComando;
import Datos.TipoTabla;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DMain {

    public static void main(String[] args) throws ParseException, SQLException {
//        EstadoActivo();
//        tipoIngreso();
//        Comando();

    }

    public static void Comando() throws ParseException, SQLException {
        DComando a = new DComando();
        try {
            for (String[] x : a.listar()) {
                System.out.println(Arrays.toString(x));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

    }

//    public static void Fotografia() throws ParseException, SQLException {
//        try {

            //a.save(new DFotografia("http://adsada","2022-05-20",1, TipoTabla.activo));
            //metodo #2 insertar
//            a.setUrl("http://adsada"); 
//            a.setFecha("2022-05-20"); 
//            a.setId_tabla(2); 
//            a.setTipo_tabla(TipoTabla.ambiente);
//            a.save();
            //metodo #3 insertar
//            a.save("http://adsadaxd","2022-05-20",1, TipoTabla.activo);
            //update #1
//            a.setUrl("http://adsadaNuevo"); 
//            a.setFecha("2022-05-20"); 
//            a.setId_tabla(2); 
//            a.setTipo_tabla(TipoTabla.ambiente);
//            a.update(2);
            //update #2            
//            a.update(3, new DFotografia("http://adsadaNuevo", "2022-05-20", 2, 2));
            //update #3  
//            a.update(3, "http://adsadaNuevo", "2022-05-20", 3, 2);
//            delete #1
//        a.delete(1);
//            System.out.println(Arrays.toString(a.getById(3, TipoTabla.activo)));
//            for (String[] r : a.listar()) {
//                System.out.println(Arrays.toString(r));
//            }
//        } catch (SQLException e) {
//            Logger.getLogger(BusinessData.class.getName()).log(Level.SEVERE, null, e);
//            System.err.println(e.getMessage());
//        }
//    }
}
