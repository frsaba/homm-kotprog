package Interface;

import Display.Display;

import java.util.ArrayList;
import java.util.List;

public class MessageBox extends View{

    private String text;
    private List<String> rows;
    private int maxRowLength;

    final String title;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;

        rows.clear();

        String row = "";
        for(String word : text.split(" ")){
            if(Display.getPrintableCharacterCount((row + word)) > maxRowLength || row.contains("\n")){
                rows.add(row.replace("\n", ""));
                row = "";
            }
            row = row + " " + word;

        }
        rows.add(row);

        this.draw();
    }

    public void addText(String text){
        setText(this.text + "\n " + text);
    }

    public MessageBox(int top, int left, int bottom, int right, String title) {
        super(top, left, bottom, right);

        maxRowLength = 1000;//right - left - 1;
        this.rows = new ArrayList<>();
        this.title = title;
        this.text = "";
    }

    public MessageBox(int top, int left, int bottom, int right) {
        this( top,  left,  bottom,  right, "");
    }

    @Override
    public void draw(int top, int left) {
        super.draw(top, left);
        Display.write(title, this.top + top - 1, left + this.left + 2);
        int startingRow = Math.max(0, rows.size() - getHeight());
        for (int i = 0; i < Math.min(getHeight(), rows.size()) ; i++) {
            Display.clearLine(top + this.top +  i);
            Display.write(rows.get(startingRow + i), top + this.top + i, left + this.left );
        }
    }
}
