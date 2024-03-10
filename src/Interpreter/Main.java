package Interpreter;

import Interfaces.ITokenEventListener;
import Negocio.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws SQLException {
        String correo = "juan@gmail.com";

//         USUARIO
//        String comando = "usuario add[1234567; Juan#; 2022-11-10; m; 77248596; juan@gmail.com; juanp; juan123;  all; doctor]";
//        String comando = "usuario edit[1; 123456; Juan Pablo edited; 2022-11-10; m; 77248596; juan@gmail.com; juan; juan123;  admin; doctor]";
//        String comando = "usuario delete[1]";
//        String comando = "usuario get[6]";
//        String comando = "usuario getName[juanp]";
//        String comando = "usuario getEmail[juan@gmail.com]";
//        String comando = "usuario listar[]";
//        String comando = "USUARIO LISTAR[]";
//         AREA MEDICA
        String comando = "area add[RAYOS GAMA; prueba; 1; 1]";
//        String comando = "area edit[3; emergencia edited; sala de emergencia; 4; 4]";
//        String comando = "area delete[3]";
//        String comando = "area get[6]";
//        String comando = "area listar[]";
//        String comando = "area asignar[3; 5]";
//         CITA
//        String comando = "cita add[2023-12-05; 20:00; 1; 4; 1]";
//        String comando = "cita edit[1; 2023-12-05; 18:00; 4; 1; 2]";
//        String comando = "cita delete[3]";
//        String comando = "cita get[1]";
//        String comando = "cita listar[]";
//        String comando = "cita getcitas[2023-12-05]";
//        String comando = "cita getcitasesp[2; 2023-12-05]";
//        String comando = "cita getcitasdoctor[1; 2023-12-05]";
//         HORARIO
//        String comando = "horario add[2; true; 08:00; 14:00; 14:00; 20:00; 3]";
//        String comando = "horario edit[7; 6; true; 08:00; 13:00; 14:00; 20:00; 3]";
//        String comando = "horario get[1; 1]";
//        String comando = "horario delete[1]";
//        String comando = "horario listar[]";
//         PAGO
//        String comando = "pago add[450; transferencia; 1]";
//        String comando = "pago edit[1; 555; transferencia; 1]";
//        String comando = "pago delete[1]";
//        String comando = "pago get[2]";
//        String comando = "pago listar[]";
//         ESPECIALIDAD
//        String comando = "especialidad add[nuevaEsp; Especialidad nueva]";
//        String comando = "especialidad edit[1; derma; cuidados en la piel3]";
//        String comando = "especialidad delete[6]";
//        String comando = "especialidad listar[]";
//        String comando = "especialidad get[1]";
//         PRODUCTO
//        String comando = "producto add[jeringa; generic; 10.0; 1; 1]";
//        String comando = "producto edit[1; jeringa2; generic; 10.0; 1; 1]";
//        String comando = "producto delete[1]";
//        String comando = "producto get[1]";
//        String comando = "producto getstock[5]";
//        String comando = "producto getName[Estetoscopio]";
//        String comando = "producto listar[]";

//         ALMACEN
//        String comando = "almacen add[almacen1]";
//        String comando = "almacen edit[7; principal]";
//        String comando = "almacen delete[7]";
//        String comando = "almacen get[6]";
//        String comando = "almacen listar[]";
        String comando = "almacen add[almacen1; almacen principal]";
//         REPORTES
//        String comando = "cita reporte[3; 2023-12-05; 2023-12-10]";

//        String comando = "lista help[]";
        NUser nUsuario = new NUser();
        NAreaMedica nAreaMedica = new NAreaMedica();
        NCita nCita = new NCita();
        NHorario nHorario = new NHorario();
        NPago nPago = new NPago();
        NEspecialidad nEspecialidad = new NEspecialidad();
        NProducto nProducto = new NProducto();
        NAlmacen nAlmacen = new NAlmacen();

        Interpreter interpreter = new Interpreter(comando.toLowerCase(), correo);
        interpreter.setListener(new ITokenEventListener() {
            @Override
            public void usuario(TokenEvent event) {
                System.out.println("CU: USUARIO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nUsuario.addUser(event.getParams());
                            return;
                        case Token.EDIT:
                            nUsuario.editUser(event.getParams());
                            return;
                        case Token.DELETE:
                            nUsuario.delUser(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nUsuario.getUserById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.GETNAME:
                            String[] registro1 = nUsuario.getUserByName(event.getParams());
                            System.out.println(Arrays.toString(registro1));
                            return;
                        case Token.GETEMAIL:
                            String[] registro2 = nUsuario.getUsuarioByEmail(event.getParams());
                            System.out.println(Arrays.toString(registro2));
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nUsuario.getUsers();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void area(TokenEvent event) {
                System.out.println("CU: AREA");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nAreaMedica.addAreaMedica(event.getParams());
                            return;
                        case Token.EDIT:
                            nAreaMedica.editAreaMedica(event.getParams());
                            return;
                        case Token.DELETE:
                            nAreaMedica.delAreaMedica(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nAreaMedica.getAreaMedicaById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.ASIGNAR:
                            nAreaMedica.asignar(event.getParams());
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nAreaMedica.getAreasMedicas();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (Exception ex) {
                    System.out.println("error al procesar el comando");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void cita(TokenEvent event) {
                System.out.println("CU: CITA");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nCita.addCita(event.getParams());
                            return;
                        case Token.EDIT:
                            nCita.editCita(event.getParams());
                            return;
                        case Token.DELETE:
                            nCita.delCita(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nCita.getCitaById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.GETCITAS:
                            ArrayList<String[]> listafecha = nCita.getCitaByFecha(event.getParams());
                            for (String[] r : listafecha) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.GETCITASESP:
                            ArrayList<String[]> listaesp = nCita.getCitaByEspecialidadIdAndFecha(event.getParams());
                            for (String[] r : listaesp) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.GETCITASDOCTOR:
                            ArrayList<String[]> listadoctor = nCita.getCitaByDoctorIdAndFecha(event.getParams());
                            for (String[] r : listadoctor) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nCita.getCitas();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.REPORTE:
                            ArrayList<String[]> listaReporte = nCita.getReporte(event.getParams());
                            for (String[] r : listaReporte) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (Exception ex) {
                    System.out.println("error al procesar el comando");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void horario(TokenEvent event) {
                System.out.println("CU: HORARIO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nHorario.addHorario(event.getParams());
                            return;
                        case Token.EDIT:
                            nHorario.editHorario(event.getParams());
                            return;
                        case Token.DELETE:
                            nHorario.delHorario(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nHorario.getHorarioByEspecialidadIdAndDia(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nHorario.getHorarios();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (Exception ex) {
                    System.out.println("error al procesar el comando");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void pago(TokenEvent event) {
                System.out.println("CU: PAGO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nPago.addPago(event.getParams());
                            return;
                        case Token.EDIT:
                            nPago.editPago(event.getParams());
                            return;
                        case Token.DELETE:
                            nPago.delPago(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nPago.getPagoById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nPago.getPagos();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (Exception ex) {
                    System.out.println("error al procesar el comando");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void especialidad(TokenEvent event) {
                System.out.println("CU: ESPECIALIDAD");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nEspecialidad.addEspecialidad(event.getParams());
                            return;
                        case Token.EDIT:
                            nEspecialidad.editEspecialidad(event.getParams());
                            return;
                        case Token.DELETE:
                            nEspecialidad.delEspecialidad(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nEspecialidad.getEspecialidadById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nEspecialidad.getEspecialidadesConHorarios();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (Exception ex) {
                    System.out.println("error al procesar el comando");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void producto(TokenEvent event) {
                System.out.println("CU: PRODUCTO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nProducto.addProducto(event.getParams());
                            return;
                        case Token.EDIT:
                            nProducto.editProducto(event.getParams());
                            return;
                        case Token.DELETE:
                            nProducto.delProducto(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nProducto.getProductoById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.GETNAME:
                            String[] registro1 = nProducto.getProductoByName(event.getParams());
                            System.out.println(Arrays.toString(registro1));
                            return;
                        case Token.GETSTOCK:
                            ArrayList<String[]> listaStock = nProducto.getStock(event.getParams());
                            for (String[] r : listaStock) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nProducto.getProductos();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (Exception ex) {
                    System.out.println("error al procesar el comando");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void almacen(TokenEvent event) {
                System.out.println("CU: ALMACEN");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            nAlmacen.addAlmacen(event.getParams());
                            return;
                        case Token.EDIT:
                            nAlmacen.editAlmacen(event.getParams());
                            return;
                        case Token.DELETE:
                            nAlmacen.delAlmacen(event.getParams());
                            return;
                        case Token.GET:
                            String[] registro = nAlmacen.getAlmacenById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nAlmacen.getAlmacenes();
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (Exception ex) {
                    System.out.println("error al procesar el comando");
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void listaComandos(TokenEvent event) {
                System.out.println("CU: LISTA COMANDOS");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.HELP:
                            ArrayList<String[]> lista = nUsuario.getComandos(event.getParams());
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void error(TokenEvent event) {
                System.out.println("DESCONOCIDO");
                System.out.println(event);
                //enviar una notificacion
            }
        });

        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }
}
