package Field;


import Interface.Drawable;

public class Field implements Drawable {
    final int NUM_ROWS;
    final int NUM_COLS;

    public int getRows(){
        return NUM_ROWS;
    }

    public int getCols(){
        return NUM_COLS;
    }

    Tile[][] field;

    public Field(int rows, int cols){

        NUM_ROWS = rows;
        NUM_COLS = cols;

        field = new Tile[NUM_ROWS][NUM_COLS];
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                field[row][col] = new Tile(row, col);
            }
        }

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Tile t = getTile(row, col);
                t.addNeighbor(getTile(row + 1, col));
                t.addNeighbor(getTile(row + 1, col + 1));
                t.addNeighbor(getTile(row + 1, col - 1));
                t.addNeighbor(getTile(row, col + 1));
            }
        }

    }

    public Tile getTile(int row, int col){
        if(row  < 0 || col < 0 || row >= NUM_ROWS || col >= NUM_COLS) return null;
        return field[row][col];
    }

    @Override
    public void draw(int top, int left) {
        for (Tile[] row : field){
            for (Tile t : row){
                t.draw(top, left);
            }
        }
    }
}
