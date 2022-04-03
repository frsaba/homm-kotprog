package Interface;

import Display.Display;
import Utils.Rect;

import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
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

    public T getUserChoice() {
        int choice;

        draw(top, left);

        while (true) {
            Display.clearLine(bottom - 1);
            Display.write("Választás: ", bottom - 1, left + 1);

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
                Display.write(String.valueOf(e), bottom - 2, 20);
            }
            Display.write("Érvénytelen bemenet!", bottom - 2, left + 1);


        }


    }

    static int dfd = 0;

    private String normalize(String s){
        Display.write(s, 3 + dfd,70);
        s = s.replaceAll("[^\\x00-\\x7F]", ""); //Nem ASCII karakterek törlése
        s = Normalizer.normalize(s, Normalizer.Form.NFD).toLowerCase(Locale.ROOT);
        Display.write("->" + s, 4 + dfd,70);
        dfd+=2;
        return s;
    }

    @Override
    public void draw(int top, int left) {

        for (int i = 0; i < options.size(); i++) {
            Display.write(" [" + i + "] " + displayNameFunc.apply(options.get(i)), top + i, left);
        }
    }
}
