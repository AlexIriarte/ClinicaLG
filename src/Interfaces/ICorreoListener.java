/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Utils.Correo;
import java.util.EventListener;
import java.util.List;

public interface ICorreoListener extends EventListener {
    
    void onCorreosNuevos (List<Correo> correos); // falta agregarle la clase
    //void onMensajeCorreo(); // tal vez para enviar las respuestas al usuario;
}
