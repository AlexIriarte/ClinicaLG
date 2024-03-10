package Interfaces;

import Interpreter.TokenEvent;

//AJUSTAR DE ACUERDO A NUESTRO CU
public interface ITokenEventListener {

    /*
        USUARIO
        AREA
        CITA
        TURNO -- HORARIO
        PAGO
        ESPECIALIDAD
        PRODUCTO
        ALMACEN
        LISTA --> para lista de comandos
     */

    void usuario(TokenEvent event);
    void area(TokenEvent event);
    void cita(TokenEvent event);
    void horario(TokenEvent event);
    void pago(TokenEvent event);
    void especialidad(TokenEvent event);
    void producto(TokenEvent event);
    void almacen(TokenEvent event);
    void listaComandos(TokenEvent event);
    void error(TokenEvent event);
}
