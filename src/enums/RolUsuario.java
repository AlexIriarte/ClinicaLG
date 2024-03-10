/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

public class RolUsuario {

    public static final Rol ADMIN = new Rol("1", "ADMIN");
    public static final Rol PERSONAL = new Rol("2", "PERSONAL");
    public static final Rol DOCTOR = new Rol("3", "DOCTOR");
    public static final Rol PACIENTE = new Rol("4", "PACIENTE");
    public static final Rol PROVEEDOR = new Rol("5", "PROVEEDOR");

    public static class Rol {

        private final String id;
        private final String label;

        private Rol(String id, String label) {
            this.id = id;
            this.label = label;
        }

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }
    }
}
