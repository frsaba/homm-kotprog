package Display;

import Managers.Game;

import java.awt.*;

import static java.text.MessageFormat.format;

//Rákos ANSI kódok https://gist.github.com/fnky/458719343aabd01cfb17a3a4f7296797


/**
 * Konzolos kiíratást elősegítő osztály. Színezés, kurzormozgatás, törlés, stb...
 */
public class Display {
    public static final String ESC = String.valueOf((char) 27);
    public static final String ANSI_CLEAR = ESC + "[2J";
    public static final String ANSI_RESET = ESC + "[0m";


    /**
     * Kiírja a megadott szöveget a képernyőre a megadott koordinátákról kezdve.
     *
     * @param s      kiírandó szöveg
     * @param row    sor pozíció
     * @param column oszlop pozíció
     */
    public static void write(String s, int row, int column) {
        System.out.print(ESC + format("[{0};{1}f", row, column));
        System.out.print(s);
    }


    /**
     * @param color      a kívánt szín
     * @param foreground előtér (betűszín) vagy háttérszín legyen
     * @return az adott szín beállításához szükséges ANSI kódot
     */
    public static String getColorString(Color color, boolean foreground) {
        return ESC + format("[{0};2;{1};{2};{3}m",
                (foreground ? 38 : 48),
                color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * @param foregroundColor a kívánt betűszín
     * @param backgroundColor a kívánt háttérszín
     * @return az adott színkombináció beállításához szükséges ANSI kódot
     */
    public static String getColorString(Color foregroundColor, Color backgroundColor) {
        return getColorString(foregroundColor, true) + getColorString(backgroundColor, false);
    }

    /**
     * Beállítja a kiíratás színét a kívánt színre
     *
     * @param color      a kívánt szín
     * @param foreground előtér (betűszín) vagy háttérszínre vonatkozzon
     */
    public static void setColor(Color color, boolean foreground) {
        System.out.println(getColorString(color, foreground));

    }

    /**
     * Beállítja a kiíratás színét a kívánt színre
     *
     * @param foregroundColor a kívánt betűszín
     * @param backgroundColor a kívánt háttérszín
     */
    public static void setColor(Color foregroundColor, Color backgroundColor) {
        setColor(foregroundColor, true);
        setColor(backgroundColor, false);
    }


    /**
     * Visszaállítja a kiíratás színét fekete-fehérre
     */
    public static void resetColor() {
        setColor(Color.WHITE, Color.BLACK);
    }


    /**
     * Minden ANSI kód által előidézett stílust alapértelmezettre állít
     */
    public static void resetStyling() {
        System.out.println(ANSI_RESET);
    }


    /**
     * A kurzort a kívánt pozícióra helyezi
     *
     * @param row    sor pozíció
     * @param column oszlop pozíció
     */
    public static void setCursorPosition(int row, int column) {
        System.out.print(ESC + format("[{0};{1}f", row, column));
    }

    /**
     * A kitörli a kívánt sort
     *
     * @param row sor pozíció
     */
    public static void clearLine(int row){
        setCursorPosition(row,0);
        System.out.println(ESC+"[2K");
    }


    /**
     * Törli a konzolt, platformtól függő paranccsal.
     */
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            else
                new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();

        } catch (Exception e) {
            Game.logError(String.valueOf(e));
        }
        setCursorPosition(0, 0);
    }

    /**
     * Megszámolja, hogy a stringben hány kiírandó karakter van.
     * Ugye a string length az átver, mert számolja az ANSI rejtett karaktereket is, pedig azok a kiíratásban nem látszódnak.
     *
     * @param s A számolni kívánt string
     * @return A stringben taláható látható karakterek száma
     */
//    //https://stackoverflow.com/questions/68627535/how-to-get-the-length-of-a-string-without-calculating-the-formatting-of-the-text
    public static int getPrintableCharacterCount(String s) {
        return s.replace(
                "[\\\\u001B\\\\u009B][\\[\\]()#;?]*((([a-zA-Z\\d]*(;[-a-zA-Z\\d\\/#&.:=?%@~_]*)*)?\\u0007)|((\\d{1,4}(?:;\\d{0,4})*)?[\\dA-PR-TZcf-ntqry=><~]))",
                "").length();
    }
}
