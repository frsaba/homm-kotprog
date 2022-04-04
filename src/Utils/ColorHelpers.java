package Utils;

import Display.Display;
import Managers.Game;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Színezést segítő statikus metódusok osztálya
 */
public class ColorHelpers {
    public static String surroundWithColor(String s, Color foreground, Color background) {
        return Display.getColorString(foreground, background) + s + Display.ANSI_RESET;
    }

    public static String surroundWithColor(String s, Color background) {
        return surroundWithColor(s, Color.black,background);
    }

    public static String surroundWithColor(String s, Color color, boolean foreground) {
        return Display.getColorString(color, foreground) + s + Display.ANSI_RESET;
    }

    public static Color applyFilter(Color color, Color filter){
        float[] originalHSB = RGBtoHSB(color);
        float[] filterHSB = RGBtoHSB(filter);

        var filtered = IntStream.range(0, filterHSB.length)
                .mapToDouble(i -> (filterHSB[i] + 0.5) * originalHSB[i]).toArray(); //Most tényleg ezt kell csinálni? Arrays.stream nem örül a floatnak

        return Color.getHSBColor((float) filtered[0], (float) filtered[1], (float) filtered[2]);
    }

    public static float[] RGBtoHSB(Color color){
        return Color.RGBtoHSB(color.getRed(),  color.getGreen(), color.getBlue(), null);

    }
}
