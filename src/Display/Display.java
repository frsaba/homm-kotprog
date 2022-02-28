package Display;

public class Display {
    static char [][] buffer = new char[30][50];

    public static void Write(String s, int x, int y) {
        System.arraycopy(s.toCharArray(), 0, buffer[x] , y, s.length());
    }

    public static void Draw(){
        ClearScreen();
        for (int x = 0; x < buffer.length; x++) {
            for (int y = 0; y < buffer[0].length; y++) {
                System.out.print(buffer[x][y]);
            }
            System.out.println();
        }
    }

    //https://stackoverflow.com/questions/2979383/how-to-clear-the-console
    static void ClearScreen(){
        try{
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }catch(Exception e){
            System.err.println(e);
        }

    }
}
