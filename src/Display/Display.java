package Display;

import java.awt.*;
import static java.text.MessageFormat.format;

//Rákos ANSI kódok https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797

public class Display {
    public static final String ESC = String.valueOf((char)27);
    public static final String ANSI_CLEAR = ESC + "[2J";
    public static final String ANSI_RESET = ESC + "[0m";

    static char [][] buffer = new char[30][50];

    public static void write(String s, int row, int column) {
        System.out.print(ESC + format("[{0};{1}f", row, column));
        System.out.print(s);
    }

    public static String getColorString(Color color, boolean foreground){
        return  ESC + format("[{0};2;{1};{2};{3}m",
                (foreground ? 38 : 48),
                color.getRed(), color.getGreen(), color.getBlue());
    }

    public static String getColorString(Color foregroundColor, Color backgroundColor){
        return  getColorString(foregroundColor, true) +  getColorString(backgroundColor, false);
    }

    public static void setColor(Color color, boolean foreground){
        System.out.println(getColorString(color, foreground));

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

    //https://stackoverflow.com/questions/68627535/how-to-get-the-length-of-a-string-without-calculating-the-formatting-of-the-text
    public static int getPrintableCharacterCount(String s){
        return  s.replace(
                "[\\\\u001B\\\\u009B][\\[\\]()#;?]*((([a-zA-Z\\d]*(;[-a-zA-Z\\d\\/#&.:=?%@~_]*)*)?\\u0007)|((\\d{1,4}(?:;\\d{0,4})*)?[\\dA-PR-TZcf-ntqry=><~]))",
                "").length();
    }
}
