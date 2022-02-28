import Display.Display;
import Players.*;
import Units.Types.*;


public class Game {
    public static void main(String[] args) {
        System.out.println("\033[0m Előtte \033[0m\n");
        Display.Write("Heló", 5,10);
        Display.Draw();
        System.out.println("\033[0m Utánna \033[0m\n");

        Force player1 = new Force(new Hero(), 1500);
        Force player2 = new Force(new Hero(), 1500);

        Peasant peasant = new Peasant(10);
        player1.addUnit(peasant);

        Griff griff = new Griff(3);
        player2.addUnit(griff);

        System.out.println(peasant);

        System.out.println("Támadás!");
        peasant.attack(griff);

        System.out.println(peasant);
        System.out.println(griff);
    }
}
