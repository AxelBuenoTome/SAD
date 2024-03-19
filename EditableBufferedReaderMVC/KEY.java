package EditableBufferedReaderMVC;

public class KEY {
    public static final int ENTER = 13; // ENTER es el 13
    public static final int ESC = 27; // ESC
    public static final int INS = 50; // 2 porque ^[[2~
    public static final int DEL = 51; // 3 porque es ^[[3~
    public static final int UP = 65; // A porque es ^[[A
    public static final int DOWN = 66; // B porque es ^[[B
    public static final int RIGHT = 67; // C porque es ^[[C
    public static final int LEFT = 68; // D porque es ^[[D
    public static final int END = 70; // F porque ^[[F
    public static final int HOME = 72; // H porque ^[[H
    public static final int SQUARE_BRACKET = 91; // "["
    public static final int TILDE = 126; // "~"
    public static final int BSK = 127; // backspace "\b"

    // Reasignaciones de la tabla ASCII, a partir de valores que no usamos
    public static final int INS_VAL = 250;
    public static final int DEL_VAL = 251;
    public static final int UP_VAL = 265;
    public static final int DOWN_VAL = 266;
    public static final int RIGHT_VAL = 267;
    public static final int LEFT_VAL = 268;
    public static final int END_VAL = 270;
    public static final int HOME_VAL = 272;
    public static final int CHAR = 300;
    public static final int CURSOR_VAL = 301;

    //valor para sumar al resto
    public static final int JOKER = 200;

    //Strings Código ANSI
    public static final String INS_STR = "\033[2~";
    public static final String DEL_STR = "\033[3~";
    public static final String UP_STR = "\033[A";
    public static final String DOWN_STR = "\033[B";
    public static final String RIGHT_STR = "\033[C";
    public static final String LEFT_STR = "\033[D";
    public static final String END_STR = "\033[F";
    public static final String HOME_STR = "\033[H";
}

