/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

public class PermisoUsuario {

    public static final Permiso ALL = new Permiso("1", "ALL");
    public static final Permiso WRITE = new Permiso("2", "WRITE");
    public static final Permiso READ = new Permiso("3", "READ");

    public static class Permiso {

        private final String id;
        private final String label;

        private Permiso(String id, String label) {
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
