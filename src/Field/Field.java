package Field;



public class Field {
    final int NUM_ROWS = 5;
    final int NUM_COLS = 3;

    Tile[][] field = new Tile[NUM_ROWS][NUM_COLS];

    public Field(){
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


}
