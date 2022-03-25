package Interface;

import Display.Display;

import java.util.ArrayList;
import java.util.List;

public class MessageBox extends View{

    private String text;
    private List<String> rows;
    private int maxRowLength;

    Border border;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;

        String row = "";
        for(String word : text.split(" ")){
            if((row + word).length() > maxRowLength || row.contains("\n")){
                rows.add(row.replace("\n", ""));
                row = "";
            }
            row = row + " " + word;

        }
        rows.add(row);

        this.Draw();
    }

    public void addText(String text){
        setText(this.text + "\n" + text);
    }

    public MessageBox(int top, int left, int bottom, int right) {
        super(top, left, bottom, right);

        border = new Border("#", top, left, bottom, right);
        addSubView(border);

        maxRowLength = right - left - 2;
        this.rows = new ArrayList<>();
    }


    @Override
    public void Draw() {
        super.Draw();
        int offset = Math.max(0, rows.size() - getHeight());
        for (int i = 0; i < Math.min(getHeight(), rows.size()) ; i++) {
            Display.write(rows.get(offset + i), top + i, left);
        }
    }
}
