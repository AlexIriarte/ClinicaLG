/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectionCore;

//import business.BProducto;
//import business.BUsuario;
//import communication.MailVerificationThread;
//import communication.SendEmailThread;
//import interfaces.IEmailEventListener;
//import interpreter.analex.Interpreter;
//import interpreter.analex.interfaces.ITokenEventListener;
//import interpreter.analex.utils.Token;
//import interpreter.events.TokenEvent;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
import Config.env;
import Datos.DComando;
import Utils.HtmlBuilder;
import Utils.Correo;
import Utils.ResStatus;
import enums.HtmlVars;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ConnectionCore {

    public static void main(String[] args) throws SQLException {
        /*
        MailVerificationThread mail = new MailVerificationThread();
        mail.setEmailEventListener(new IEmailEventListener() {
            @Override
            public void onReceiveEmailEvent(List<Email> emails) {
                for (Email email : emails) {
                    //System.out.println(email);
                    interprete(email);
                }
            }
        });
        
        Thread thread = new Thread( mail);
        thread.setName("Mail Verification Thread");
        thread.start();
         */

        String message, titulo;
        String[] cabecera;
        List<String[]> datos = new LinkedList();
//        titulo = "Lista de estado_activos";
//        cabecera = new String[]{"ID", "NOMBRE"};
//        datos = dEstadoActivo.listar();

//        String message = "Petición realizada correctamente #3";
//        String message = HtmlBuilder.generateTable();
//        String message = HtmlBuilder.generateMessageInfo("Ocurrio un problema", "grupo21sa@tecnoweb.org.bo", "HELP%20NUEVO");
//        String message = HtmlBuilder.generateTable2(titulo, cabecera, datos);
//        message = HtmlBuilder.generateTable();
//        message = HtmlBuilder.generateComandos();
//        message = HtmlBuilder.generatePrueba();
        datos.add(new String[]{
            "juanito", "1"
        });
        datos.add(new String[]{
            "alcachofa", "3"
        });
        datos.add(new String[]{
            "juancin", "2"
        });
        message = HtmlBuilder.generateTableReports("nise", ResStatus.REPORTE_GET_INGRESOS_POR_ESPECIALIDAD, datos);

        String rcpt = "xd@gmail.com";
        Correo emailObject = new Correo(rcpt, Correo.SUBJECT, message);

        SendEmailThread sendEmail = new SendEmailThread(emailObject);
        Thread thread = new Thread(sendEmail);
        thread.setName("Send email Thread");
        thread.start();
    }

    /*Generate lista comando*/
    public static String ListcomandosPrueba() {
        String mailto, subject, body, newLinea, espacio;
        String message, titulo;
        List<String[]> datos = new LinkedList();

        newLinea = "%0A";
        espacio = "%20";
        titulo = "EMAIL SYSTEM TECNOWEB \""+HtmlVars.TITULO_SW+"\"";

        mailto = env.EMAIL_ENVIAR;
        subject = "ADD" + espacio + "USER";
        body = "hola" + newLinea + "como";

        datos.add(new String[]{
            "Usuario",
            "registrar nuevo usuario",
            " Parámetros: nombre, contraseña, correo, permiso, personaId\n"
            + "<hr>\n"
            + "Ejemplo: user add[user1; 123456; usergmail.COM; administrador; 1]",
            " <a id=\"email\" type=\"submit\"\n"
            + "href=\"mailto:" + mailto + "?subject=" + subject + "&body=" + body + "\">Solicitar Comandos</a>"
        });

        subject = "UPDATE" + espacio + "USER";
        body = "hola" + newLinea + "  como";
        datos.add(new String[]{
            "Usuario",
            "editar usuario",
            " Parámetros: id del usuario a editar, nombre, contraseña, correo, permiso, personaId\n"
            + "<hr>\n"
            + "Ejemplo: usuario edit[1; user1; 123456; user@gmail.COM; administrador; 1]",
            " <a id=\"email\" type=\"submit\"\n"
            //            + "href=\"mailto:"+ mailto+"?subject="+subject+"&body="+body+"\">Solicitar Comandos</a>"
            + "href=\"mailto:grupo21sa@tecnoweb.org.bo?subject=HELP%20NUEVO&body=hola %0A como\">Solicitar Comandos</a>"
        });
        return HtmlBuilder.generateComandos();
    }

    /*
    public static void interprete(Email email) {
        BProducto bProducto = new BProducto();
        BUsuario bUsuario = new BUsuario();
        
        Interpreter interpreter = new Interpreter(email.getSubject(), email.getFrom());       
        interpreter.setListener(new ITokenEventListener() {
            @Override
            public void user(TokenEvent event) {
                System.out.println("CU: USER");
                System.out.println(event);
            }

            @Override
            public void client(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void dpto(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void social(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void schedule(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void notify(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void apartment(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void visit(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void support(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void reserve(TokenEvent event) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void usuario(TokenEvent event) {
                System.out.println("CU: USUARIO");
                System.out.println(event);    
                try {
                    if(event.getAction() == Token.GET) {
                        ArrayList<String[]> lista = bUsuario.listar();
                        
                        String s = "";
                        for(int i = 0; i < lista.size(); i++) {
                            s = s + "["+i+"] : ";
                            for(int j = 0; j <lista.get(i).length; j++) {
                                s = s + lista.get(i)[j] + " | ";
                            }
                            s = s + "\n";
                        }
                        System.out.println(s);
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: "+ex.getSQLState());
                    //enviar notificacion de error
                }
            }

            @Override
            public void producto(TokenEvent event) {
                System.out.println("CU: MASCOTA");
                System.out.println(event);
                try {
                    if(event.getAction() == Token.AGREGAR) {
                        bProducto.guardar(event.getParams(), event.getSender());   
                        System.out.println("OK");
                        //notificar al usuario que se ejecuto su comando
                    } else if(event.getAction() == Token.MODIFY) {

                    } else if(event.getAction() == Token.DELETE) {

                    } else {
                        System.out.println("La accion no es valida para el caso de uso");
                        //enviar al correo una notificacion
                    }                
                } catch (SQLException ex) {
                    System.out.println("Mensaje: "+ex.getSQLState());
                    //enviar notificacion de error
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
     */
}
