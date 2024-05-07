package ReproductorV2;

public class KEY {
    //Clase EY de la practica 1 simplificada para leer secuencias de escape
    public static final int ENTER = 13; // ENTER es el 13
    public static final int SQUARE_BRACKET = 91; // "["
    public static final int TILDE = 126; // "~"
    public static final int BSK = 127; // backspace "\b"

    // Reasignaciones de la tabla ASCII, a partir de valores que no usamos
    public static final int DEL_VAL = -1;
    public static final int UP_VAL = -2;
    public static final int DOWN_VAL = -3;
    public static final int RIGHT_VAL = -4;
    public static final int LEFT_VAL = -5;

    //Strings Código ANSI
    public static final String DEL_STR = "\033[3~";
    public static final String UP_STR = "\033[A";
    public static final String DOWN_STR = "\033[B";
    public static final String RIGHT_STR = "\033[C";
    public static final String LEFT_STR = "\033[D";

    //Claves para el tipo de objeto
    public static final int POSITION = -10;
    public static final int PROGRESS = -11;
    public static final int SONG = -12;
    public static final int INIT = -13;

}



