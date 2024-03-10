package Interpreter;

public class Token {
    private int name;// si es CU, ACTION o ERROR
    private int attribute; // que tipo ya sea CU, ACTION o ERROR
    
    //constantes numericas para manejar el analex
    public static final int CU = 0;
    public static final int ACTION = 1;
    public static final int PARAMS = 2;
    public static final int END = 3;
    public static final int ERROR = 4;
    
    // ajustar de acuerdo a sus casos de uso con valores entre 100 a 199
    //Titulos de casos de uso en numero
    public static final int USUARIO = 100;
    public static final int AREA = 101;
    public static final int CITA = 102;
    public static final int HORARIO = 103;
    public static final int PAGO = 104;
    public static final int ESPECIALIDAD = 105;
    public static final int PRODUCTO = 106;
    public static final int ALMACEN = 107;
    public static final int LISTA = 108;    //para lista de comandos
    
    //ajustar de acuerdo a sus acciones con valores entre 200 a 299
    //Titulos de las acciones generales
    public static final int ADD = 200;
    public static final int DELETE = 201;
    public static final int EDIT = 202;
    public static final int GET = 203;
    public static final int GETNAME = 204;
    public static final int GETEMAIL = 205;
    public static final int LISTAR = 206;
    public static final int ASIGNAR = 207;  //asignar responsable a una área médica
    public static final int GETCITAS = 208; // citas de una fecha determinada
    public static final int GETCITASESP = 209; // citas de una especialidad en una fecha determinada
    public static final int GETCITASDOCTOR = 210; // citas de un doctor en una fecha determinada
//    public static final int ENCURSO = 212;
//    public static final int PROXIMO = 213;
    public static final int REPORTE = 214;
    public static final int GETSTOCK = 215; // stock de un producto en todos los almacenes
    public static final int HELP = 217; //comandos
    
    public static final int ERROR_COMMAND = 300;
    public static final int ERROR_CHARACTER = 301;
    
    //constantes literales para realizar un efecto de impresión
    public static final String LEXEME_CU = "caso de uso";
    public static final String LEXEME_ACTION = "action";
    public static final String LEXEME_PARAMS = "params";
    public static final String LEXEME_END = "end";
    public static final String LEXEME_ERROR = "error";
    
    // ajustar de acuerdo a sus casos de uso con valores en string
    //Titulos de casos de uso con string
    public static final String LEXEME_USUARIO = "usuario";
    public static final String LEXEME_AREA = "area";
    public static final String LEXEME_CITA = "cita";
    public static final String LEXEME_HORARIO = "horario";
    public static final String LEXEME_PAGO = "pago";
    public static final String LEXEME_ESPECIALIDAD = "especialidad";
    public static final String LEXEME_PRODUCTO = "producto";
    public static final String LEXEME_ALMACEN = "almacen";
    public static final String LEXEME_LISTA = "lista"; // para los comandos
    
    
    //ajustar de acuerdo a sus acciones con valores en string
    //Titulos de las acciones generales en string
    public static final String LEXEME_ADD = "add";
    public static final String LEXEME_DELETE = "delete";
    public static final String LEXEME_EDIT = "edit";
    public static final String LEXEME_GET = "get";
    public static final String LEXEME_GETNAME = "getName";
    public static final String LEXEME_GETEMAIL = "getEmail";
    public static final String LEXEME_LISTAR = "listar";
    public static final String LEXEME_ASIGNAR = "asignar";
    public static final String LEXEME_GETCITAS = "getcitas";
    public static final String LEXEME_GETCITASESP = "getcitasesp";
    public static final String LEXEME_GETCITASDOCTOR = "getcitasdoctor";
//    public static final String LEXEME_ENCURSO = "enCurso";
//    public static final String LEXEME_PROXIMO = "proximo";
    public static final String LEXEME_REPORTE = "reporte";
    public static final String LEXEME_GETSTOCK = "getstock";
    public static final String LEXEME_HELP = "help";
    
    public static final String LEXEME_ERROR_COMMAND = "UNKNOWN COMMAND";
    public static final String LEXEME_ERROR_CHARACTER = "UNKNOWN CHARACTER";
    
    /**
     * Constructor por default.
     */
    public Token(){
        
    }
    
    /**
     * Constructor parametrizado por el literal del token
     * @param token 
     */
    //No Tocar
    public Token(String token){
        int id = findByLexeme(token);
        if(id != -1){
            if(100 <= id && id < 200){
                this.name = CU;
                this.attribute = id;
            } else if(200 <= id && id < 300){
                this.name = ACTION;
                this.attribute = id;
            }
        } else {
            this.name = ERROR;
            this.attribute = ERROR_COMMAND;
            System.err.println("Class Token.Constructor dice: \n "
                    + " El lexema enviado al constructor no es reconocido como un token \n"
                    + "Lexema: "+token);
        }
    }
    
    /**
     * Constructor parametrizado 2.
     * @param name 
     */
    public Token(int name){
        this.name = name;
    }
    
    /**
     * Constructor parametrizado 3.
     * @param name
     * @param attribute 
     */
    public Token(int name, int attribute){
        this.name = name;
        this.attribute = attribute;
    }
    
    // Setters y Getters
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }
    
    @Override
    public String toString(){
        if(0 <= name  && name <=1){
            return "< " + getStringToken(name) + " , " + getStringToken(attribute) + ">";
        }else if(name == 2){
            return "< " + getStringToken(name) + " , " + attribute + ">";
        }else if(3 == name){
            return "< " + getStringToken(name) + " , " + "_______ >";
        } else if(name == 4){
            return "< " + getStringToken(name) + " , " + getStringToken(attribute) + ">";
        }
        return "< TOKEN , DESCONOCIDO>";
    }
    
    /**
     * Devuelve el valor literal del token enviado
     * Si no lo encuentra retorna N: token.
     * @param token
     * @return 
     */
    //ajustar de acuerdo a sus CU
    public String getStringToken(int token){
        switch(token){
            case CU:
                return LEXEME_CU;
            case ACTION:
                return LEXEME_ACTION;
            case PARAMS:
                return LEXEME_PARAMS;
            case END:
                return LEXEME_END;
            case ERROR:
                return LEXEME_ERROR;
                
            //CU
            case USUARIO:
                return LEXEME_USUARIO;
            case AREA:
                return LEXEME_AREA;
            case CITA:
                return LEXEME_CITA;
            case HORARIO:
                return LEXEME_HORARIO;
            case PAGO:
                return LEXEME_PAGO;
            case ESPECIALIDAD:
                return LEXEME_ESPECIALIDAD;
            case PRODUCTO:
                return LEXEME_PRODUCTO;
            case ALMACEN:
                return LEXEME_ALMACEN;
            case LISTA:
                return LEXEME_LISTA;
            
            //ACCION
            case ADD:
                return LEXEME_ADD;
            case DELETE:
                return LEXEME_DELETE;
            case EDIT:
                return LEXEME_EDIT;
            case GET:
                return LEXEME_GET;
            case GETNAME:
                return LEXEME_GETNAME;
            case GETEMAIL:
                return LEXEME_GETEMAIL;
            case LISTAR:
                return LEXEME_LISTAR;
            case ASIGNAR:
                return LEXEME_ASIGNAR;
            case GETCITAS:
                return LEXEME_GETCITAS;
            case GETCITASESP:
                return LEXEME_GETCITASESP;
            case GETCITASDOCTOR:
                return LEXEME_GETCITASDOCTOR;
            case GETSTOCK:
                return LEXEME_GETSTOCK;
//            case PROXIMO:
//                return LEXEME_PROXIMO;
            case REPORTE:
                return LEXEME_REPORTE;
            case HELP:
                return LEXEME_HELP;
                
            case ERROR_COMMAND:
                return LEXEME_ERROR_COMMAND;
            case ERROR_CHARACTER:
                return LEXEME_ERROR_CHARACTER;
            default:
                return "N: " + token;
        }
    }
    
    /**
     * Devuelve el valor numerico del lexema enviado
     * Si no lo encuentra retorna -1.
     * @param lexeme
     * @return 
     */
    //ajustar de acuerdo a sus CU
    private int findByLexeme(String lexeme){
        switch(lexeme){
            case LEXEME_CU:
                return CU;
            case LEXEME_ACTION:
                return ACTION;
            case LEXEME_PARAMS:
                return PARAMS;
            case LEXEME_END:
                return END;
            case LEXEME_ERROR:
                return ERROR;
              
            //CU 
            case LEXEME_USUARIO:
                return USUARIO;
            case LEXEME_AREA:
                return AREA;
            case LEXEME_CITA:
                return CITA;
            case LEXEME_HORARIO:
                return HORARIO;
            case LEXEME_PAGO:
                return PAGO;
            case LEXEME_ESPECIALIDAD:
                return ESPECIALIDAD;
            case LEXEME_PRODUCTO:
                return PRODUCTO;
            case LEXEME_ALMACEN:
                return ALMACEN;
            case LEXEME_LISTA:
                return LISTA;
                
            //ACTION    
            case LEXEME_ADD:
                return ADD;
            case LEXEME_DELETE:
                return DELETE;
            case LEXEME_EDIT:
                return EDIT;
            case LEXEME_GET:
                return GET;
            case LEXEME_GETNAME:
                return GETNAME;
            case LEXEME_GETEMAIL:
                return GETEMAIL;
            case LEXEME_LISTAR:
                return LISTAR;
            case LEXEME_ASIGNAR:
                return ASIGNAR;
            case LEXEME_GETCITAS:
                return GETCITAS;
            case LEXEME_GETCITASESP:
                return GETCITASESP;
            case LEXEME_GETCITASDOCTOR:
                return GETCITASDOCTOR;
            case LEXEME_GETSTOCK:
                return GETSTOCK;
//            case LEXEME_ENCURSO:
//                return ENCURSO;
//            case LEXEME_PROXIMO:
//                return PROXIMO;
            case LEXEME_REPORTE:
                return REPORTE;
            case LEXEME_HELP:
                return HELP;
                
                
            case LEXEME_ERROR_COMMAND:
                return ERROR_COMMAND;            
            case LEXEME_ERROR_CHARACTER:
                return ERROR_CHARACTER;            
            default:
                return -1;
        }
    }
}
