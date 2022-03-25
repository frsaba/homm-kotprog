package Display;

import java.awt.*;
import static java.text.MessageFormat.format;

//Rákos ANSI kódok https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797

public class Display {
    static final String ESC = String.valueOf((char)27);
    static final String ANSI_CLEAR = ESC + "[2J";
    static final String ANSI_RESET = ESC + "[0m";

    static char [][] buffer = new char[30][50];

    public static void write(String s, int row, int column) {
        System.out.print(ESC + format("[{0};{1}f", row, column));
        System.out.print(s);
    }

    public static void setColor(Color color, boolean foreground){
        System.out.println(ESC + format("[{0};2;{1};{2};{3}m",
                        (foreground ? 38 : 48),
                        color.getRed(), color.getGreen(), color.getBlue()));

    }

    public static void setColor(Color foregroundColor, Color backgroundColor){
        setColor(foregroundColor, true);
        setColor(backgroundColor, false);
    }

    public static void resetColor(){
        setColor(Color.WHITE, Color.BLACK);
    }

    public static void resetStyling(){
        System.out.println(ANSI_RESET);
    }

    public static void setCursorPosition(int row, int column){
        System.out.print(ESC + format("[{0};{1}f", row, column));
    }

    public static void clear(){
        System.out.println(ANSI_CLEAR);
        setCursorPosition(0,0);
    }
}
