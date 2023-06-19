package net.joedoe;

public class Format {
    static final String ANSI_ESC = "\033[";
    static final int RED = 91; // text
    static final int GREEN = 92;
    static final int CYAN = 96;
    static final int BLACK = 40; // background

    static void output(String str) {
        output(str, RED, false, false, false);
    }

    static void output(String str, int color) {
        output(str, color, false, false, false);
    }

    static void output(String str, int color, boolean... styles) {
        String s = ANSI_ESC + color;
        if (styles[0]) s += ";" + BLACK;    // background color
        if (styles[1]) s += ";1";           // bold
        if (styles[2]) s += ";4";           // underline
        s += "m" + str + ANSI_ESC + "0m";
        System.out.print(s);
    }
}
