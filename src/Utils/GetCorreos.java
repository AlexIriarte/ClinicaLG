/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.sun.mail.handlers.text_plain;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import static java.time.LocalDate.now;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GetCorreos {

    private static final String GMAIL = "d=gmail";
    private static final String HOTMAIL = "d=hotmail";
    private static final String YAHOO = "d=yahoo";

    public static Correo getEmail(String plain_text){
        return new Correo(getFrom(plain_text), getTo(plain_text), getSubject(plain_text), getMessage(plain_text), getFile(plain_text));
    }

    private static String getFrom(String plain_text) {
        String search = "Return-Path: <";
        int index_begin = plain_text.indexOf(search) + search.length();
        int index_end = plain_text.indexOf(">");
        return plain_text.substring(index_begin, index_end);
    }

    private static String getTo(String plain_text) {
        String to = "";
        if (plain_text.contains(GMAIL)) {
            to = getToFromGmail(plain_text);
        } else if (plain_text.contains(HOTMAIL)) {
            to = getToFromHotmail(plain_text);
        } else if (plain_text.contains(YAHOO)) {
            to = getToFromYahoo(plain_text);
        }
        return to;
    }

    private static String getSubject(String plain_text) {
        String search = "Subject: ";
        int i = plain_text.indexOf(search) + search.length();
        String end_string = "";
        if (plain_text.contains(GMAIL)) {
            end_string = "To:";
        } else if (plain_text.contains(HOTMAIL)) {
            end_string = "Thread-Topic";
        } else if (plain_text.contains(YAHOO)) {
            end_string = "MIME-Version:";
        }
        int e = plain_text.indexOf(end_string, i);
        return plain_text.substring(i, e);
    }

    private static String getMessage(String correo) {
        String mensaje = "";
        String idABuscar1 = "Content-Type: text/plain; charset=\"UTF-8\"";
        String idABuscar2 = "Content-Type: text/html; charset=\"UTF-8\"";

        if (correo.contains(GMAIL)) {
            int posInit = correo.indexOf(idABuscar1) + idABuscar1.length();
            int posEnd = correo.indexOf(idABuscar2);
            mensaje = correo.substring(posInit, posEnd).trim();
            posEnd = mensaje.indexOf("--");
            mensaje = mensaje.substring(0, posEnd).trim();
        }
        return mensaje;
    }

    private static List<byte[]> getFile(String correo) {
        try {
                    List<byte[]> filesUrls = new ArrayList<>();
        String urlBase64 = "";
        String idABuscar = "X-Attachment-Id:";
        int posInit = correo.indexOf(idABuscar);
        int posEnd = correo.indexOf("\n", posInit);
        while (posInit != -1) {
            posInit = posEnd + 2; // inicio del archivo base64
            correo = correo.substring(posInit, correo.length());
            posEnd = correo.indexOf("--");
            if (posEnd != -1) {
                urlBase64 = correo.substring(0, posEnd);
//                urlBase64 = urlBase64.replaceAll(" ", "+");
                byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(urlBase64);
//                System.out.println(Arrays.toString(imageBytes));
//                UUID uuid = UUID.randomUUID();
//                String urlAGuardar = miServer.subirArchivo(imageBytes, uuid.toString() + ".png", "Fotos/");
//                List<String> param = new ArrayList();
////                param.add(String.valueOf(filesUrls.size()));
//                param.add(urlAGuardar);
//                param.add(now().toString());
//                param.add("1");
//                param.add("111");
//                NFotografia foto = new NFotografia();
//                foto.guardar(param);
                filesUrls.add(imageBytes);

            }
            posInit = correo.indexOf(idABuscar);
            posEnd = correo.indexOf("\n", posInit);
//            System.out.println("AGARANDO CHORIZO < init " + correo.substring(0, posEnd) + " fin >");
        }

        return filesUrls;
        } catch (Exception e) {
        return new ArrayList<>();
        }
        
    }

    private static String getToFromGmail(String plain_text) {
        return getToCommon(plain_text);
    }

    private static String getToFromHotmail(String plain_text) {
        String aux = getToCommon(plain_text);
        return aux.substring(1, aux.length() - 1);
    }

    private static String getToFromYahoo(String plain_text) {
        int index = plain_text.indexOf("To: ");
        int i = plain_text.indexOf("<", index);
        int e = plain_text.indexOf(">", i);
        return plain_text.substring(i + 1, e);
    }

    private static String getToCommon(String plain_text) {
        String aux = "To: ";
        int index_begin = plain_text.indexOf(aux) + aux.length();
        int index_end = plain_text.indexOf("\n", index_begin);
        return plain_text.substring(index_begin, index_end);
    }
}
