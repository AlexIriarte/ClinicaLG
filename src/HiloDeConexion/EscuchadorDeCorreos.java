/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HiloDeConexion;

import Interfaces.ICorreoListener;
import Interfaces.ITokenEventListener;
import Interpreter.Interpreter;
import Interpreter.Main;
import Interpreter.Token;
import Interpreter.TokenEvent;
import Negocio.NAlmacen;
import Negocio.NAreaMedica;
import Negocio.NCita;
import Negocio.NEspecialidad;
import Negocio.NHorario;
import Negocio.NPago;
import Negocio.NProducto;
import Negocio.NUser;
//import Negocio.NAmbiente;
import Utils.ResStatus;
import Utils.Correo;
import enums.PermisoUsuario;
import enums.RolUsuario;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EscuchadorDeCorreos implements ICorreoListener {

    private final NUser veriUser;

    public EscuchadorDeCorreos() throws IOException, SQLException {
        veriUser = new NUser();
    }

    @Override
    public void onCorreosNuevos(List<Correo> correos) {
        int index = 1;
        System.out.println("---------------CORREOS NUEVOS-----------------");
        for (Correo unCorreo : correos) {
            System.out.println("CORREO: " + index++);
            System.out.println("----------------------");
            List<String> parametro = new ArrayList<>();
            parametro.add(unCorreo.getFrom());
            try {
                String[] user = veriUser.getUsuarioByEmail(parametro);
                if (user != null) {
                    interprete(unCorreo);
                } else {
                    System.out.println("USUARIO NO AUTORIZADO");
                    ResStatus.sendMensaje(unCorreo.getFrom(),
                            unCorreo.getSubject(),
                            "USUARIO NO AUTORIZADO",
                            -1, null, null, null);
                }
            } catch (SQLException ex) {
                ResStatus.sendMensaje(unCorreo.getFrom(),
                        unCorreo.getSubject(),
                        ex.getMessage(),
                        -1, null, null, null);
            }
        }
    }

    public void interprete(Correo correo) throws SQLException {
        NUser nUsuario = this.veriUser;
        System.out.println(nUsuario.toString());
        List<String> paramEmail = new ArrayList<>();
        paramEmail.add(correo.getFrom());
        String[] userActual = this.veriUser.getUsuarioByEmail(paramEmail);
        NAreaMedica nAreaMedica = new NAreaMedica();
        NCita nCita = new NCita();
        NHorario nHorario = new NHorario();
        NPago nPago = new NPago();
        NEspecialidad nEspecialidad = new NEspecialidad();
        NProducto nProducto = new NProducto();
        NAlmacen nAlmacen = new NAlmacen();
        Interpreter interpreter = new Interpreter(correo.getSubject().toLowerCase(), correo.getFrom());
        interpreter.setListener(new ITokenEventListener() {
            @Override
            public void usuario(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: USUARIO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    nUsuario.addUser(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.USUARIO.name(),
                                            1, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }

                            return;
                        case Token.EDIT:
                            boolean res = nUsuario.esMismoUser(
                                    Integer.parseInt(event.getParams(0)), correo.getFrom());
                            if (res) {
                                nUsuario.editUser(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.USUARIO.name(),
                                        2, null, null, null);
                            } else {
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        "SOLO PUEDE EDITAR SU PROPIO USUARIO",
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            boolean res2 = nUsuario.esMismoUser(
                                    Integer.parseInt(event.getParams(0)), correo.getFrom());
                            if (res2) {
                                nUsuario.delUser(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.USUARIO.name(),
                                        3, null, null, null);
                            } else {
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        "NO PUEDE ELIMINAR OTROS USUARIOS",
                                        400, null, null, null);
                            }

                            return;
                        case Token.GET:
                            String[] registro = nUsuario.getUserById(event.getParams());
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.USUARIO.name(),
                                    200, ResStatus.USUARIO, registro, null);
                            return;
                        case Token.GETNAME:
                            String[] registro1 = nUsuario.getUserByName(event.getParams());
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.USUARIO.name(),
                                    200, ResStatus.USUARIO, registro1, null);
                            return;
                        case Token.GETEMAIL:
                            String[] registro2 = nUsuario.getUsuarioByEmail(event.getParams());
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.USUARIO.name(),
                                    200, ResStatus.USUARIO, registro2, null);
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nUsuario.getUsers();
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.USUARIO.name(),
                                    300, ResStatus.USUARIO, null, lista);
                            return;
                    }
                } catch (SQLException | ParseException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.USUARIO.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.USUARIO.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(EscuchadorDeCorreos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void area(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: AREA");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    nAreaMedica.addAreaMedica(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.AREA_MEDICA.name(),
                                            1, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.EDIT:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    nAreaMedica.editAreaMedica(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.AREA_MEDICA.name(),
                                            2, null, null, null);

                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nAreaMedica.delAreaMedica(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.AREA_MEDICA.name(),
                                            3, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.GET:
                            String[] registro = nAreaMedica.getAreaMedicaById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.AREA_MEDICA.name(),
                                    200, ResStatus.AREA_MEDICA, registro, null);
                            return;
                        case Token.ASIGNAR:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nAreaMedica.asignar(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.AREA_MEDICA.name(),
                                            2, null, null, null);

                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nAreaMedica.getAreasMedicas();
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.AREA_MEDICA.name(),
                                    300, ResStatus.AREA_MEDICA, null, lista);
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                    }
                } catch (SQLException | ParseException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.AREA_MEDICA.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.AREA_MEDICA.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                }
            }

            @Override
            public void cita(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: CITA");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nCita.addCita(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.CITA.name(),
                                            1, null, null, null);

                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.EDIT:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nCita.editCita(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.CITA.name(),
                                            2, null, null, null);

                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nCita.delCita(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.CITA.name(),
                                            3, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.GET:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                System.out.println("ALL or WRITE or ");
                                //AGREGAR EL METODO
                                String[] registro = nCita.getCitaById(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.CITA.name(),
                                        200, ResStatus.CITA, registro, null);
                                System.out.println(Arrays.toString(registro));

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.GETCITAS:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                System.out.println("ALL or WRITE or ");
                                //AGREGAR EL METODO
                                ArrayList<String[]> listafecha = nCita.getCitaByFecha(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.CITA.name(),
                                        300, ResStatus.CITA, null, listafecha);
                                for (String[] r : listafecha) {
                                    System.out.println(Arrays.toString(r));
                                }

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }

                            return;
                        case Token.GETCITASESP:
                            ArrayList<String[]> listaesp = nCita.getCitaByEspecialidadIdAndFecha(event.getParams());
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.CITA.name(),
                                    300, ResStatus.CITA, null, listaesp);
                            for (String[] r : listaesp) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.GETCITASDOCTOR:
                            ArrayList<String[]> listadoctor = nCita.getCitaByDoctorIdAndFecha(event.getParams());
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.CITA.name(),
                                    300, ResStatus.CITA, null, listadoctor);
                            for (String[] r : listadoctor) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nCita.getCitas();
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.CITA.name(),
                                    300, ResStatus.CITA, null, lista);
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.REPORTE:
                            String[] titulo = {
                                "TOTAL INGRESO(BS) DE CITAS POR ESPECIALIDAD. ",
                                "CANTIDAD DE CITAS POR DOCTOR DOCTOR. ",
                                "TOTAL INGRESO(BS) CITAS POR DOCTOR.  "
                            };
                            List<String[]> cabeceras = new ArrayList<>();
                            cabeceras.add(ResStatus.REPORTE_GET_INGRESOS_POR_ESPECIALIDAD);
                            cabeceras.add(ResStatus.REPORTE_GET_CANTIDAD_CITAS_DOCTOR);
                            cabeceras.add(ResStatus.REPORTE_GET_INGRESOS_DOCTOR);
                            int tipoReporte = Integer.parseInt(event.getParams(0));
                            ArrayList<String[]> lista3 = new ArrayList<>();
                            if (tipoReporte == 1) {
                                lista3 = nCita.getReporte(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        "Reportes: " + titulo[0] + event.getParams(1),
                                        600, cabeceras.get(0), null, lista3);
                            } else if (tipoReporte == 2) {
                                lista3 = nCita.getReporte(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        "Reportes: " + titulo[1] + event.getParams(1),
                                        600, cabeceras.get(1), null, lista3);

                            } else {
                                lista3 = nCita.getReporte(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        "Reportes: " + titulo[2] + event.getParams(1),
                                        600, cabeceras.get(2), null, lista3);
                            }
                    }
                } catch (SQLException | ParseException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.CITA.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.CITA.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }
                }
            }

            @Override
            public void horario(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: HORARIO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nHorario.addHorario(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.HORARIO.name(),
                                            1, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.EDIT:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nHorario.editHorario(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.HORARIO.name(),
                                            2, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nHorario.delHorario(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.HORARIO.name(),
                                            3, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.GET:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())) {
                                    System.out.println("ALL ");
                                    //AGREGAR EL METODO
                                    String[] registro = nHorario.getHorarioByEspecialidadIdAndDia(event.getParams());
                                    System.out.println(Arrays.toString(registro));
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.HORARIO.name(),
                                            200, ResStatus.HORARIO, registro, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }

                            return;
                        case Token.LISTAR:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())) {
                                    System.out.println("ALL ");
                                    //AGREGAR EL METODO
                                    ArrayList<String[]> lista = nHorario.getHorarios();
                                    for (String[] r : lista) {
                                        System.out.println(Arrays.toString(r));
                                    }
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.HORARIO.name(),
                                            300, ResStatus.HORARIO, null, lista);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }

                            return;
                    }
                } catch (SQLException | ParseException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.HORARIO.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.HORARIO.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                }
            }

            @Override
            public void pago(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: PAGO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nPago.addPago(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.PAGO.name(),
                                            1, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.EDIT:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nPago.editPago(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.PAGO.name(),
                                            2, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())
                                        || permiso.equals(PermisoUsuario.WRITE.getLabel())) {
                                    System.out.println("ALL or WRITE or ");
                                    //AGREGAR EL METODO
                                    nPago.delPago(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.PAGO.name(),
                                            3, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.GET:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                System.out.println("ALL or WRITE or ");
                                //AGREGAR EL METODO
                                String[] registro = nPago.getPagoById(event.getParams());
                                System.out.println(Arrays.toString(registro));
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.PAGO.name(),
                                        200, ResStatus.PAGO, registro, null);
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }

                            return;
                        case Token.LISTAR:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                System.out.println("ALL or WRITE or ");
                                //AGREGAR EL METODO
                                ArrayList<String[]> lista = nPago.getPagos();
                                for (String[] r : lista) {
                                    System.out.println(Arrays.toString(r));
                                }
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.PAGO.name(),
                                        300, ResStatus.PAGO, null, lista);
                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }

                            return;
                    }
                } catch (SQLException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.PAGO.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.PAGO.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                }
            }

            @Override
            public void especialidad(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: ESPECIALIDAD");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {

                                //AGREGAR EL METODO
                                nEspecialidad.addEspecialidad(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.ESPECIALIDAD.name(),
                                        1, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }

                            return;
                        case Token.EDIT:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                //AGREGAR EL METODO
                                nEspecialidad.editEspecialidad(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.ESPECIALIDAD.name(),
                                        2, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {

                                //AGREGAR EL METODO
                                nEspecialidad.delEspecialidad(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.ESPECIALIDAD.name(),
                                        3, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
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
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.ESPECIALIDAD.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.ESPECIALIDAD.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                }

            }

            @Override

            public void producto(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: PRODUCTO");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                if (permiso.equals(PermisoUsuario.ALL.getLabel())) {
                                    nProducto.addProducto(event.getParams());
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.TABLAS.PRODUCTO.name(),
                                            1, null, null, null);
                                } else {
                                    System.out.println("READ");
                                    ResStatus.sendMensaje(correo.getFrom(),
                                            correo.getSubject(),
                                            ResStatus.__PERMISO,
                                            400, null, null, null);
                                }
                                //AGREGAR EL METODO

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.EDIT:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                //AGREGAR EL METODO
                                nProducto.editProducto(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.PRODUCTO.name(),
                                        2, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                //AGREGAR EL METODO
                                nProducto.delProducto(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.PRODUCTO.name(),
                                        3, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.GET:
                            String[] registro = nProducto.getProductoById(event.getParams());
                            System.out.println(Arrays.toString(registro));
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.PRODUCTO.name(),
                                    200, ResStatus.PRODUCTO, registro, null);
                            return;
                        case Token.GETNAME:
                            String[] registro1 = nProducto.getProductoByName(event.getParams());
                            System.out.println(Arrays.toString(registro1));
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.PRODUCTO.name(),
                                    200, ResStatus.PRODUCTO, registro1, null);
                            return;
                        case Token.GETSTOCK:
                            ArrayList<String[]> listaStock = nProducto.getStock(event.getParams());
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.PRODUCTO.name(),
                                    300, ResStatus.PRODUCTO, null, listaStock);
                            for (String[] r : listaStock) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                        case Token.LISTAR:
                            ArrayList<String[]> lista = nProducto.getProductos();
                            ResStatus.sendMensaje(correo.getFrom(),
                                    correo.getSubject(),
                                    ResStatus.TABLAS.PRODUCTO.name(),
                                    300, ResStatus.PRODUCTO, null, lista);
                            for (String[] r : lista) {
                                System.out.println(Arrays.toString(r));
                            }
                            return;
                    }
                } catch (SQLException | ParseException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.PRODUCTO.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.PRODUCTO.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                }
            }

            @Override
            public void almacen(TokenEvent event) {
                String permiso = "";
                System.out.println("CU: ALMACEN");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.ADD:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                //AGREGAR EL METODO
                                nAlmacen.addAlmacen(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.ALMACEN.name(),
                                        1, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.EDIT:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                //AGREGAR EL METODO
                                nAlmacen.editAlmacen(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.ALMACEN.name(),
                                        2, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
                            return;
                        case Token.DELETE:
                            permiso = nUsuario.getPermiso(correo.getFrom());
                            if (permiso != null) {
                                //AGREGAR EL METODO
                                nAlmacen.delAlmacen(event.getParams());
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.TABLAS.ALMACEN.name(),
                                        3, null, null, null);

                            } else {
                                System.out.println("READ");
                                ResStatus.sendMensaje(correo.getFrom(),
                                        correo.getSubject(),
                                        ResStatus.__PERMISO,
                                        400, null, null, null);
                            }
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
                } catch (SQLException | ParseException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                ResStatus.TABLAS.ALMACEN.name() + " NOT FOUND!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + ResStatus.TABLAS.ALMACEN.name() + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                }
                //enviar notificacion de error

            }

            @Override
            public void listaComandos(TokenEvent event) {
                System.out.println("CU: LISTA COMANDOS");
                System.out.println(event);
                try {
                    switch (event.getAction()) {
                        case Token.HELP:
                            ArrayList<String[]> lista = nUsuario.getComandos(event.getParams());
                            ResStatus.sendMensaje(correo.getFrom(), correo.getSubject(), "COMANDOS", 300, ResStatus.CABE_COMAND, null, lista);
                            return;
                    }
                } catch (SQLException | ParseException ex) {
                    System.out.println("Mensaje: " + ex.getMessage());
                    if (ex.getMessage() == null) {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "COMANDOS" + " no existe!",
                                -1, null, null, null);
                    } else {
                        ResStatus.sendMensaje(
                                correo.getFrom(),
                                correo.getSubject(),
                                "Error en el CU: " + "LISTA DE COMANDOS" + ".\n Msg de error: " + ex.getMessage(),
                                -1, null, null, null);
                    }

                }
            }

            @Override
            public void error(TokenEvent event) {
                System.out.println(event);
                ResStatus.sendMensaje(correo.getFrom(),
                        correo.getSubject(),
                        "COMANDO NO ENCONTRADO \n CONSULTE EL SGTE COMANDO: lista help[]",
                        -1, null, null, null);
            }

        }
        );

        Thread thread = new Thread(interpreter);
        thread.setName("Interpreter Thread");
        thread.start();

    }
}
