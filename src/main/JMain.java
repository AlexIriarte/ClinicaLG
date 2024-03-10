package main;

import Negocio.NAlmacen;
import Negocio.NCategoria;
import Negocio.NProducto;
import Negocio.NProductoAlmacen;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JMain {

    public static void main(String[] args) throws ParseException, SQLException {
        try {
//            Categoria();
//            Almacen();
            Producto();
//            ProductoAlmacen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void Categoria() throws ParseException, SQLException {
        NCategoria n = new NCategoria();
//        add
//        List<String> params = new ArrayList<String>();
//        params.add("nueva categoria");
//        n.addCategoria(params);
//        
//        edit
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        params.add("nuevo almacen editado");
//        n.editCategoria(params);

//        ver
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        String[] r = n.verCategoria(params);
//        System.out.println(Arrays.toString(r));
//        eliminar
//        List<String> params = new ArrayList<String>();
//        params.add("2");
//        n.delCategoria(params);
//        listar
//        ArrayList<String[]> lista = n.listar();
//        for (String[] r : lista) {
//            System.out.println(Arrays.toString(r));
//        }
    }

    public static void Almacen() throws ParseException, SQLException {
        NAlmacen n = new NAlmacen();
//        add
//        List<String> params = new ArrayList<String>();
//        params.add("nuevo almacen");
//        n.addAlmacen(params);
//        
//        edit
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        params.add("nuevo almacen editado");
//        n.editAlmacen(params);

//        ver
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        String[] r = n.verAlmacen(params);
//        System.out.println(Arrays.toString(r));
//        eliminar
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        n.delAlmacen(params);
//        listar
//        ArrayList<String[]> lista = n.listar();
//        for (String[] r : lista) {
//            System.out.println(Arrays.toString(r));
//        }
    }

    public static void Producto() throws ParseException, SQLException {
        NProducto producto = new NProducto();
//        add
//        List<String> params = new ArrayList<String>();
//        params.add("productoprueba");
//        params.add("marcaa");
//        params.add(17.5 + "");
//        params.add("1");
//        params.add("1");
//        producto.addProducto(params);
//
//        edit
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        params.add("producto edited");
//        params.add("marcaa edited");
//        params.add(35.3 + "");
//        params.add("1");
//        params.add("1");
//        producto.editProducto(params);
//
//        ver
        List<String> params = new ArrayList<String>();
        params.add("1");
        String[] r = producto.getProductoById(params);
        System.out.println(Arrays.toString(r));
//
//        delete
//        List<String> params = new ArrayList<String>();
//        params.add("2");
//        producto.delProducto(params);
//
//        listar
//        ArrayList<String[]> lista = producto.listar();
//        for (String[] r : lista) {
//            System.out.println(Arrays.toString(r));
//        }
    }

    public static void ProductoAlmacen() throws ParseException, SQLException {
        NProductoAlmacen producto = new NProductoAlmacen();
//        add
//        List<String> params = new ArrayList<String>();
//        params.add("10");
//        params.add("1");
//        params.add("1");
//        producto.addProductoAlmacen(params);
//
//        edit
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        params.add("10");
//        params.add("1");
//        params.add("1");
//        producto.editProductoAlmacen(params);
//
//        ver
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        String[] r = producto.verProductoAlmacen(params);
//        System.out.println(Arrays.toString(r));
//
//        delete
//        List<String> params = new ArrayList<String>();
//        params.add("1");
//        producto.delProductoAlmacen(params);
//
//        listar
//        ArrayList<String[]> lista = producto.listar();
//        for (String[] r : lista) {
//            System.out.println(Arrays.toString(r));
//        }
    }
}
