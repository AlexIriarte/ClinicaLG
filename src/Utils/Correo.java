/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.List;

public class Correo {

    public static final String SUBJECT = "Request respose";
    private String from;
    private String to;
    private String asunto;
    private String mensaje;
    private List<byte[]> files;

    public Correo() {
    }

    public Correo(String from, String to , String subject, String message, List<byte[]> files) {
        this.from = from;
        this.to = to;
        this.asunto = subject;
        this.mensaje = message;
        this.files = files;
    }

    public Correo(String from, String subject) {
        this.from = from;
        this.asunto = subject;
    }
    
    public Correo(String to, String subject, String message) {
        this.to = to;
        this.asunto = subject;
        this.mensaje = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return asunto;
    }

    public void setSubject(String subject) {
        this.asunto = subject;
    }

    public String getMessage() {
        return mensaje;
    }

    public void setMessage(String message) {
        this.mensaje = message;
    }
    
    public List<byte[]> getFiles(){
        return this.files;
    }

    @Override
    public String toString() {
        return "[From: " + from + ", To: " + to + ", Subject: " + asunto + ", Message: " + mensaje + ", cantFiles: " + this.files.size()+ "]";
    }
}
