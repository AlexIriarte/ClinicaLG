/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import HiloDeConexion.HiloDeConexion;
import Negocio.NAreaMedica;
import Negocio.NCita;
import Negocio.NEspecialidad;
import Negocio.NHorario;
import Negocio.NPago;
import Negocio.NUser;
import Negocio.NUserEspecialidad;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, Exception {

        // try {
        System.out.println("**********PROBANDO  MODELOS*************");

        NEspecialidad nEspecialidad = new NEspecialidad(); // ✔
        NUser nUser = new NUser(); // ✔
        NAreaMedica nAM = new NAreaMedica(); // ✔
        NHorario nHorario = new NHorario();
        NUserEspecialidad nUserEspecialidad = new NUserEspecialidad();
        NCita nCita = new NCita();
        NPago nPago = new NPago();

        System.out.println("**********ESPECIALIDADES**********");

        // List<String> paramAdd = new ArrayList<>();
        // paramAdd.add("farmacia");
        // paramAdd.add("medicamentos");
        // nEspecialidad.addEspecialidad(paramAdd);
        // List<String> paramAdd2 = new ArrayList<>();
        // paramAdd2.add("Otorinoralingologia");
        // paramAdd2.add("especialidad para el oido");
        // nEspecialidad.addEspecialidad(paramAdd2);
        // List<String> paramEdit = new ArrayList<>();
        // paramEdit.add("1");
        // paramEdit.add("Cardiologia Actualizada");
        // paramEdit.add("especialidad para el corazon");
        // nEspecialidad.editEspecialidad(paramEdit);
        // List<String> paramDel = new ArrayList<>();
        // paramDel.add("1");
        // nEspecialidad.delEspecialidad(paramDel);
        // List<String> paramId = new ArrayList<>();
        // paramId.add("2");
        // String[] espeEncontrada = nEspecialidad.getEspecialidadById(paramId);
        // System.out.println(Arrays.toString(espeEncontrada));
        ArrayList<String[]> especialidades = nEspecialidad.getEspecialidadesConHorarios();
        especialidades.forEach((especialidad) -> {
            System.out.println(Arrays.toString(especialidad));
        });
        System.out.println("");
        System.out.println("**********USUARIOS**********");
        // Probar el método addUser
        // List<String> parametrosAddUser = new ArrayList<>();
        // parametrosAddUser.add("123456789"); // ci
        // parametrosAddUser.add("Usuario Prueba"); // nombre
        // parametrosAddUser.add("1990-01-01"); // fecha_nac
        // parametrosAddUser.add("Masculino"); // genero
        // parametrosAddUser.add("1234567890"); // telefono
        // parametrosAddUser.add("usuario@prueba.com"); // email
        // parametrosAddUser.add("usuario_prueba"); // usuario
        // parametrosAddUser.add("password_prueba"); // password
        // parametrosAddUser.add("admin"); // permiso
        // parametrosAddUser.add("rol_prueba"); // rol
        // nUser.addUser(parametrosAddUser);
        // List<String> parametrosAddUser2 = new ArrayList<>();
        // parametrosAddUser2.add("123456782"); // ci
        // parametrosAddUser2.add("Usuario Prueba2"); // nombre
        // parametrosAddUser2.add("1990-01-01"); // fecha_nac
        // parametrosAddUser2.add("Masculino"); // genero
        // parametrosAddUser2.add("1234567890"); // telefono
        // parametrosAddUser2.add("usuario@prueba2.com"); // email
        // parametrosAddUser2.add("usuario_prueba2"); // usuario
        // parametrosAddUser2.add("password_prueba2"); // password
        // parametrosAddUser2.add("admin"); // permiso
        // parametrosAddUser2.add("rol_prueba"); // rol
        // nUser.addUser(parametrosAddUser2);
        // Probar el método editUser
        // List<String> parametrosEditUser = new ArrayList<>();
        // parametrosEditUser.add("2"); // id del usuario a editar
        // parametrosEditUser.add("987654321"); // nuevo ci
        // parametrosEditUser.add("Juan"); // nuevo nombre
        // parametrosEditUser.add("2000-01-01"); // nueva fecha_nac
        // parametrosEditUser.add("Femenino"); // nuevo genero
        // parametrosEditUser.add("9876543210"); // nuevo telefono
        // parametrosEditUser.add("usuario_editado@prueba.com"); // nuevo email
        // parametrosEditUser.add("usuario_editado"); // nuevo usuario
        // parametrosEditUser.add("password_editado"); // nuevo password
        // parametrosEditUser.add("user"); // nuevo permiso
        // parametrosEditUser.add("rol_editado"); // nuevo rol
        // nUser.editUser(parametrosEditUser);
        // Probar el método delUser
        // List<String> parametrosDelUser = new ArrayList<>();
        // parametrosDelUser.add("7"); // id del usuario a eliminar
        // nUser.delUser(parametrosDelUser);
        // Probar el método getUserById
        // List<String> parametrosGetByIdUser = new ArrayList<>();
        // parametrosGetByIdUser.add("8"); // id del usuario a obtener
        // String[] userObtenido = nUser.getUserById(parametrosGetByIdUser);
        // System.out.println("Usuario obtenido por ID: " +
        // Arrays.toString(userObtenido));
        // Probar el método getUsers
        ArrayList<String[]> usuarios = nUser.getUsers();
        usuarios.forEach((usuario) -> {
            System.out.println(Arrays.toString(usuario));
        });
        System.out.println("");
        System.out.println("**********AREAS_MEDICAS**********");
        // List<String> parametrosAdd = new ArrayList<>();
        // parametrosAdd.add("Rayos x");
        // parametrosAdd.add("Atencion las 24 horas");
        // parametrosAdd.add("2");
        // parametrosAdd.add("2");
        //
        // nAM.addAreaMedica(parametrosAdd);
        //
        // List<String> parametrosAdd2 = new ArrayList<>();
        // parametrosAdd2.add("Emergencia");
        // parametrosAdd2.add("Atencion las 24 horas");
        // parametrosAdd2.add("1");
        // parametrosAdd2.add("1");
        // nAM.addAreaMedica(parametrosAdd2);
        //
        // List<String> parametrosAdd3 = new ArrayList<>();
        // parametrosAdd3.add("Embarazos");
        // parametrosAdd3.add("Atencion las 24 horas");
        // parametrosAdd3.add("2");
        // parametrosAdd3.add("2");
        // nAM.addAreaMedica(parametrosAdd3);
        // List<String> parametrosEdit = new ArrayList<>();
        // parametrosEdit.add("1"); // Id del área médica a editar
        // parametrosEdit.add("Emergencia solo para mascotas");
        // parametrosEdit.add("Atencion solo 12 horas");
        // parametrosEdit.add("NuevoTipo");
        // nAM.editAreaMedica(parametrosEdit);
        // List<String> parametrosDel = new ArrayList<>();
        // parametrosDel.add("1"); // Id del área médica a eliminar
        // nAM.delAreaMedica(parametrosDel);
        // List<String> parametrosGetById = new ArrayList<>();
        // parametrosGetById.add("1"); // Id del área médica a obtener
        // String[] areaMedica2 = nAM.getAreaMedicaById(parametrosGetById);
        // System.out.println(Arrays.toString(areaMedica2));
        ArrayList<String[]> areasMedicas = nAM.getAreasMedicas();
        areasMedicas.forEach((areaMedica) -> {
            System.out.println(Arrays.toString(areaMedica));
        });
        System.out.println("");
        System.out.println("**********HORARIOS**********");
        
        
        List<String> paramH = new ArrayList<>();
        paramH.add("2");
        paramH.add("2");
        System.out.println(Arrays.toString(nHorario.getHorarioByEspecialidadIdAndDia(paramH)));
        
        // Agregar horarios para la especialidad_id 2, de lunes a viernes
        // for (int dia = 1; dia <= 5; dia++) {
        // List<String> paramAddHorario = new ArrayList<>();
        // paramAddHorario.add(String.valueOf(dia)); // día
        // paramAddHorario.add("true");
        // paramAddHorario.add("08:00"); // hora_inicio
        // paramAddHorario.add("12:30"); // hora_fin
        // paramAddHorario.add("14:00"); // hora_inicio
        // paramAddHorario.add("18:30"); // hora_fin
        // paramAddHorario.add("1"); // especialidad_id
        // nHorario.addHorario(paramAddHorario);
        // }
        // Obtener los horarios
        // ArrayList<String[]> horarios = nHorario.getHorarios();
        // System.out.println("");
        // System.out.println("Lista de Horarios");
        // horarios.forEach((horario) -> {
        // System.out.println(Arrays.toString(horario));
        // });
        System.out.println("**********CITAS**********");
        // add cita
//        List<String> paramCitaAdd = new ArrayList<>();
//        paramCitaAdd.add("2023-12-10");
//        paramCitaAdd.add("10:30");
//        paramCitaAdd.add("1");
//        paramCitaAdd.add("2");
//        paramCitaAdd.add("1");
//        nCita.addCita(paramCitaAdd);

        // edit cita
        List<String> paramCitaEdit = new ArrayList<>();
        paramCitaEdit.add("1");
        paramCitaEdit.add("2023-12-10");
        paramCitaEdit.add("10:30");
        paramCitaEdit.add("atendida");
        paramCitaEdit.add("1");
        paramCitaEdit.add("2");
        paramCitaEdit.add("1");
        nCita.editCita(paramCitaEdit);

        List<String> paramCitaFecha = new ArrayList<>();
        paramCitaFecha.add("1");
        paramCitaFecha.add("2023-09-10");

        ArrayList<String[]> citas = nCita.getCitaByEspecialidadIdAndFecha(paramCitaFecha);
        citas.forEach((citaMedica) -> {
            System.out.println(Arrays.toString(citaMedica));
        });
        System.out.println("");
        System.out.println("**********PAGOS**********");
        // // Probar el método addPago
        // List<String> parametrosAdd = new ArrayList<>();
        // parametrosAdd.add("100.0"); // monto
        // parametrosAdd.add("2023-11-30 12:00:00"); // fecha_hora
        // parametrosAdd.add("Tarjeta"); // tipo
        // nPago.addPago(parametrosAdd);
        //
        // // Obtener el ID del último pago agregado (supongamos que hay un método
        // getLastPaymentId en NPago)
        // String[] lastPaymentId = nPago.getLastPaymentId();
        //
        // // Probar el método editPago
        // List<String> parametrosEdit = new ArrayList<>();
        // parametrosEdit.add(String.valueOf(lastPaymentId)); // id del pago a editar
        // parametrosEdit.add("150.0"); // nuevo monto
        // parametrosEdit.add("2023-11-30 15:30:00"); // nueva fecha_hora
        // parametrosEdit.add("Efectivo"); // nuevo tipo
        // nPago.editPago(parametrosEdit);
        //
        // // Probar el método delPago
        // List<String> parametrosDel = new ArrayList<>();
        // parametrosDel.add(String.valueOf(lastPaymentId)); // id del pago a eliminar
        // nPago.delPago(parametrosDel);
        //
        // // Probar el método getPagoById
        // List<String> parametrosGetById = new ArrayList<>();
        // parametrosGetById.add(String.valueOf(lastPaymentId)); // id del pago a
        // obtener
        // String[] pagoObtenido = nPago.getPagoById(parametrosGetById);
        // System.out.println("Pago obtenido por ID: " + pagoObtenido);
        //
        // // Probar el método getPagos
        // ArrayList<String[]> pagos = nPago.getPagos();
        // System.out.println("Listado de pagos: " + pagos);
        // } catch (Exception e) {
        // System.out.println(e.getMessage());
        // }
    }

}
