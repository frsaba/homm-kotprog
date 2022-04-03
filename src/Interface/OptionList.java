package Interface;

import Display.Display;
import Managers.Game;
import Utils.ColorHelpers;
import Utils.Rect;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Egy menü, amely prezentálja a felhasználót néhány T típusú opcióval, amik közül választhat.
 *
 * @param <T> az opciók típusa
 */
public class OptionList<T> extends View implements Drawable {
    List<T> options;
    Function<T, String> displayNameFunc;

    public OptionList(Rect rect, List<T> options, Function<T, String> mapper) {
        super(rect);
        this.options = options;
        this.displayNameFunc = mapper;
    }

    public OptionList(Rect rect, Collection<T> options, Function<T, String> displayNameFunc) {
        super(rect);
        this.options = options.stream().toList();
        this.displayNameFunc = displayNameFunc;
    }

    public OptionList(Rect rect, List<T> options) {
        this(rect, options, Object::toString);
    }

    public OptionList(Rect rect) {
        this(rect, new ArrayList<>());
    }

    public T getUserChoice(String promptTop, String promptBottom) {

        //Ha csak egy lehetséges opció van, rögtön kiválasztjuk
//        if (options.size() == 1) {
//            return options.get(0);
//        }

        int choice;

        draw(top, left);
        Display.write(promptTop, top, left);

        while (true) {
            Display.clearToEndOfLine(bottom - 1, left);
            Display.write(promptBottom, bottom - 1, left + 3);

            Scanner scanner = new Scanner(System.in);
            try {
                String next = scanner.next();
                if (next.matches("-?\\d+(\\.\\d+)?")) { //egy szám
                    choice = Integer.parseInt(next);
                    if (choice >= 0 && choice < options.size()) return options.get(choice);
                }

                var matches = options.stream().filter((o) -> normalize(displayNameFunc.apply(o))
                        .startsWith(normalize(next))).toList();

                //  matches.toList().stream().forEach(o -> Display.write(String.valueOf(o), 20,2));

                if (matches.size() == 1) return matches.get(0);

            } catch (Exception e) {
//                Display.write(String.valueOf(e), bottom - 2, 20);
            }
            Game.logError("\tÉrvénytelen bemenet!");


        }


    }

    public T getUserChoice(String promptTop) {
        return getUserChoice(promptTop, "Választás: ");
    }

    public T getUserChoice() {
        return getUserChoice("");
    }


    public void setOptions(List<T> options) {
        this.options = new LinkedList<>(options);
    }

    public void addOption(T option) {
        options.add(option);
    }

    public void addAll(List<T> options) {
        this.options.addAll(options);
    }

    public void removeOption(T option) {
        options.remove(option);
        draw(top, left);
    }

    public void removeOption(int index) {
        Game.log(Arrays.toString(options.toArray()));
        options.remove(index);
        draw(top, left);
        Game.log(Arrays.toString(options.toArray()));
    }

    static int dfd = 0;

    private String normalize(String s) {
//        Display.write(s, 3 + dfd,70);
        s = s.replaceAll("[^\\x00-\\x7F]", ""); //Nem ASCII karakterek törlése
        s = Normalizer.normalize(s, Normalizer.Form.NFD).toLowerCase(Locale.ROOT);
//        Display.write("->" + s, 4 + dfd,70);
        dfd += 2;
        return s;
    }

    @Override
    public void draw(int top, int left) {

        for (int i = 0; i <= getHeight(); i++) {
            Display.clearToEndOfLine(top + i, left);
        }


        int offset = 0;
        for (int i = 0; i < options.size(); i++) {
            String displayName = displayNameFunc.apply(options.get(i));
            Display.write("\t [" + i + "] " + displayName, top + +2 + i + offset, left);
            if (displayName.endsWith("\n")) offset++;
        }
    }
}
