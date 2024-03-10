package main;

import ConnectionCore.SendEmailThread;
import HiloDeConexion.HiloDeConexion;
import Interfaces.ICorreoListener;
import Interfaces.ITokenEventListener;
import Interpreter.Interpreter;
import Interpreter.Main;
import Interpreter.Token;
import Interpreter.TokenEvent;
import Negocio.*;
import Utils.HtmlBuilder;
import Utils.Correo;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;

public class main {

    public static void main(String[] args) {

        HiloDeConexion hiloPrincipal = new HiloDeConexion();
//        hiloPrincipal.setCorreoEventListener(new ICorreoListener() {
//            @Override
//            public void onCorreosNuevos(List<Correo> correos) {
//                for (Correo correo : correos) {
//                    System.out.println("NUEVO CORREO");
//                    try {
//                        interprete(correo);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        });
        hiloPrincipal.start();

    }

    public static void interprete(Correo correo) throws SQLException {
        NUser nUsuario = new NUser();
        Interpreter interpreter = new Interpreter(correo.getSubject(), correo.getFrom());
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
                            lista.forEach((r) -> {
                                System.out.println(Arrays.toString(r));
                            });
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.toString());
                    //enviar notificacion de error
                } catch (ParseException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
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
//                            for (String[] r : lista) {
//                                System.out.println(Arrays.toString(r));
//                            }
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

            @Override
            public void area(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void cita(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void horario(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void pago(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void especialidad(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void producto(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void almacen(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

//        if(e!=null && e.getAction()==Token.ADDFOTO){
//            System.out.println("se detectó files en el correo");
//            subirFoto(correo);
//        }
        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();
    }

    public static void simpleNotifySuccess(String email) {
        String resp = HtmlBuilder.generateMessageCorrecto(
                "Petición realizada correctamente",
                email,
                "LIST COOMAND"
        );
        Correo emailObject = new Correo(email, Correo.SUBJECT, resp);
        sendEmail(emailObject);
    }

    public static void sendEmail(Correo email) {
        SendEmailThread sendEmail = new SendEmailThread(email);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }
}
