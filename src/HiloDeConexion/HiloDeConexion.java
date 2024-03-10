/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HiloDeConexion;

import Config.env;
import Interfaces.ICorreoListener;
import Utils.Correo;
import Utils.GetCorreos;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.sasl.AuthenticationException;
import javax.swing.event.EventListenerList;
import utils.Command;

public class HiloDeConexion extends Thread {

    private final static String HOST = env.HOST_EMAIL;
    private final static int PORT = env.PORT_SMTP;
    private final static String USER = env.USER_SERVER_EMAIL;
    private final static String PASS = env.PASS_EMAIL;

    private Socket clienteSocket;
    private BufferedReader input;
    private DataOutputStream output;
    private ICorreoListener correoEventListener;

    public HiloDeConexion() {
        this.clienteSocket = null;
        this.input = null;
        this.output = null;

    }

    public HiloDeConexion(Socket Servidor) {
        this.clienteSocket = Servidor;
        this.input = null;
        this.output = null;
    }

    public ICorreoListener getCorreoEventListener() {
        return correoEventListener;
    }

    public void setCorreoEventListener(ICorreoListener correoEventListener) {
        this.correoEventListener = correoEventListener;
    }

    @Override
    public void run() {
        try {
            int c = 1;
            while (true) {
                List<Correo> correos = new ArrayList();
                clienteSocket = new Socket(HOST, PORT);
                input = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                output = new DataOutputStream(clienteSocket.getOutputStream());
                System.out.println("--------CLIENTE SOCKET CONECTADO--------");
                authUser(USER, PASS);
                int cantCorreos = getCantDeCorreos();
                if (cantCorreos > 0) {
                    correos = getListaDeCorreos(cantCorreos);
                    setCorreoEventListener(new EscuchadorDeCorreos());
                    this.correoEventListener.onCorreosNuevos(correos);
                    deleteCorreos(cantCorreos);
                }
                output.writeBytes(Command.quit());
                input.readLine();
                input.close();
                output.close();
                clienteSocket.close();
                System.out.println("----------CERRANDO CONEXION-------");
                System.out.println("Veces ejecutadas: " + c++);
                Thread.sleep(10000);
            }
        } catch (InterruptedException | IOException e) {
            throw new Error("Algo paso con el hilo de coneccion => " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(HiloDeConexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HiloDeConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void authUser(String email, String pass) {
        try {
            if (clienteSocket != null && input != null && output != null) {
                input.readLine();
                output.writeBytes(Command.user(email)); // enviar email
                input.readLine();
                output.writeBytes(Command.pass(pass)); //enviar pass
                String msg = input.readLine();
                if (msg.equals("-ERR")) {
                    throw new AuthenticationException("NO SE CONECTA");
                }
            }
        } catch (IOException e) {
            System.out.println("Algo paso en la authenticaicon: " + e.getMessage());
        }
    }

    private void deleteCorreos(int cantCorreos) throws IOException {
        for (int i = 1; i <= cantCorreos; i++) {
            output.writeBytes(Command.dele(i));
        }
    }

    private int getCantDeCorreos() throws IOException {
        output.writeBytes(Command.stat());
        String line = input.readLine();
        String[] data = line.split(" ");
        System.out.println(Arrays.toString(data));
        return Integer.parseInt(data[1]);
    }

    private List<Correo> getListaDeCorreos(int cantCorreosALeer) throws IOException, SQLException, ParseException {
        List<Correo> correos = new ArrayList<>();
        for (int i = 1; i <= cantCorreosALeer; i++) {
            output.writeBytes(Command.retr(i)); // comando pa leer el correo
            String correoActual = getCorreoMultilinea();
            System.out.println("CORREO: " + i);
            System.out.println(correoActual);
            System.out.println("----------------------------------------------");
            correos.add(GetCorreos.getEmail(correoActual));
        }
        return correos;
    }

    private String getCorreoMultilinea() throws IOException {
        String lines = "";
        while (true) {
            String line = input.readLine();
            if (line == null) {
                throw new IOException("Server no responde (ocurrio un error al abrir el correo)");
            }
            if (line.equals(".")) {
                break;
            }
            lines = lines + "\n" + line;
        }
        return lines;
    }
}

//        protected void DespachadorEventoConexion(EventConexion e) {
//        ISocketListener[] ls = listSocketListener.getListeners(ISocketListener.class);
//        for (ISocketListener l : ls) {
//            l.onClienteConectado(e);
//        }
//    }
//
//    protected void CerrarEventoConexion(EventConexion e) {
//        ISocketListener[] ls = listSocketListener.getListeners(ISocketListener.class);
//        for (ISocketListener l : ls) {
//            l.onClienteDesconectado(e);
//        }
//    }

