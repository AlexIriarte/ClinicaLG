/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Config.env;
import ConnectionCore.SendEmailThread;
import java.util.ArrayList;
import java.util.List;

public class ResStatus {

        public static String EMAIL_SERVER = env.EMAIL_SERVER;
        public static String __HELP = "lista help[]";
        public static String __PENDING = "EN PROXIMA ACTUALIZACION :D";
        public static String __PERMISO = "NO TIENE EL PERMISO CORRESPONDIENTE";

        public enum TABLAS {
                REPORTETIPOT,
                PRODUCTO,
                ALMACEN,
                CATEGORIA,
                PAGO,
                CITA,
                HORARIO,
                ESPECIALIDAD,
                AREA_MEDICA,
                USUARIO,
                REPORTE_GET_INGRESOS_POR_ESPECIALIDAD,
                REPORTE_GET_CANTIDAD_CITAS_DOCTOR,
                REPORTE_GET_INGRESOS_DOCTOR
        }

        // CABECERAS
        public static String[] USUARIO = { "CI", "NOMBRE", "FECHA_NAC", "GENERO", "TELEFONO", "EMAIL", "USUARIO",
                        "PASSWORD", "PERMISO", "ROL" };

        public static String[] AREA_MEDICA = { "ID", "NOMBRE", "DESCRIPCION", "RESPONSABLE", "MEDICO" };

        public static String[] ESPECIALIDAD = { "ID", "NOMBRE", "DESCRIPCION" };

        public static String[] HORARIO = { "ID", "DIA", "ACTIVO", "TURNO_MANHA_INICIO", "TURNO_MANHA_FIN",
                        "TURNO_TARDE_INICIO", "TURNO_TARDE_FIN", "ESPECIALIDAD", // "CREATED_AT", "UPDATED_AT"
        };

        public static String[] CITA = { "ID", "FECHA", "HORA", "ESTADO", "MEDICO", "PACIENTE", "ESPECIALIDADID" };

        public static String[] PAGO = { "ID", "MONTO", "FECHA_HORA", "TIPO", "CITA_ID" };

        public static String[] CATEGORIA = { "ID", "NOMBRE" };

        public static String[] ALMACEN = { "ID", "NOMBRE" };

        public static String[] PRODUCTO = { "ID", "NOMBRE", "MARCA", "PRECIO", "PROVEDOR", "CATEGORIA" };

        // Cabecera Reportes
        public static String[] REPORTE_GET_INGRESOS_POR_ESPECIALIDAD = {
                        "ID",
                        "ESPECIALIDAD",
                        "INGRESO TOTAL"
        };
        public static String[] REPORTE_GET_CANTIDAD_CITAS_DOCTOR = {
                        "ID",
                        "DOCTOR",
                        "CANTIDAD DE CITAS"
        };
        public static String[] REPORTE_GET_INGRESOS_DOCTOR = {
                        "ID",
                        "DOCTOR",
                        "INGRESO TOTAL"
        };

        public static String[] PERMISOS = { "ALL", "WRITE", "READ" };
        public static String[] CABE_COMAND = { "CASO DE USO", "DESC", "PARÁMETROS", "EJEMPLO", "ACCION" };

        // MENSAJES
        // persona
        public static void sendMensaje(String from, String subject, String cu, int action, String[] cabecera,
                        String[] datos, ArrayList<String[]> todos) {
                String msg = "";
                switch (action) {
                        case 1:
                                // add
                                msg = cu + " SAVED SUCCESSFULLY";
                                enviarRespuesta(from, subject,
                                                HtmlBuilder.generateMessageCorrecto(msg, EMAIL_SERVER, __HELP));
                                return;
                        case 2:
                                // edit
                                msg = cu + " UPDATED SUCCESSFULLY";
                                enviarRespuesta(from, subject,
                                                HtmlBuilder.generateMessageCorrecto(msg, EMAIL_SERVER, __HELP));
                                return;
                        case 3:
                                // delete
                                msg = cu + " DELETED SUCCESSFULLY";
                                enviarRespuesta(from, subject,
                                                HtmlBuilder.generateMessageCorrecto(msg, EMAIL_SERVER, __HELP));
                                return;
                        case 200:
                                // GET
                                msg = "VISTA DE " + cu;
                                if (datos != null && datos.length > 0) {
                                        enviarRespuesta(from, subject, HtmlBuilder.generateTableUniqueDate(
                                                        msg, cabecera, datos));
                                } else {
                                        enviarRespuesta(from, subject, HtmlBuilder.generateMessageError(
                                                        msg + " NOT FOUND", EMAIL_SERVER, __HELP));
                                }
                                return;
                        case 300:
                                // LISTAS
                                msg = "LISTA DE " + cu;
                                if (todos != null && !todos.isEmpty()) {
                                        enviarRespuesta(from, subject, HtmlBuilder.generateTable(msg, cabecera, todos));
                                } else {
                                        enviarRespuesta(from, subject, HtmlBuilder.generateMessageError(msg + " vacia",
                                                        EMAIL_SERVER, __HELP));
                                }
                                return;
                        case 400:
                                enviarRespuesta(from, subject,
                                                HtmlBuilder.generateMessageInfo(cu, EMAIL_SERVER, __HELP));
                                return;
                        // case 500:
                        // // LISTAS
                        // msg = "LISTA DE " + cu;
                        // if (datos != null && datos.length > 0) {
                        // enviarRespuesta(from, subject, HtmlBuilder.generateTableWithPhotos(msg,
                        // cabecera, datos, AMBIENTE_FOTOS, todos));
                        // } else {
                        // enviarRespuesta(from, subject, HtmlBuilder.generateMessageError(msg + "
                        // vacia",
                        // EMAIL_SERVER, __HELP));
                        // }
                        // return;
                        case 600:
                                // LISTAS
                                msg = "LISTA DE " + cu;
                                if (todos != null && todos.size() > 0) {
                                        enviarRespuesta(from, subject,
                                                        HtmlBuilder.generateTableReports(msg, cabecera, todos));
                                } else {
                                        enviarRespuesta(from, subject, HtmlBuilder.generateMessageError(msg + " SIN DATOS",
                                                        EMAIL_SERVER, __HELP));
                                }
                                return;
                        case -1:
                                enviarRespuesta(from, subject,
                                                HtmlBuilder.generateMessageError(cu, EMAIL_SERVER, __HELP));
                                return;
                        default:
                                // errores
                                enviarRespuesta(from, subject, HtmlBuilder.generateMessageError(
                                                "ERROR COMANDO NO ENCONTRADO", EMAIL_SERVER, __HELP));
                }
        }

        public static void enviarRespuesta(String from, String subject, String msg) {
                SendEmailThread sendEmail = new SendEmailThread(new Correo(from, subject, msg));
                Thread thread = new Thread(sendEmail);
                thread.setName("Send email Thread");
                thread.start();
        }
}
