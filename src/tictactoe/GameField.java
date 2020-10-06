package tictactoe;

public class GameField implements Common{
    private char[][] field;

    public GameField() {
        field = new char[3][3];
        fill(CELL_EMPTY);
    }

    public void fill(char c) {
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                makeStep(j, i, c);
            }
        }
    }

    public void print(){
        System.out.println("-".repeat(9));
        for (int i = 0; i < field.length; i++) {
            StringBuilder row = new StringBuilder("| ");
            for (int j = 0; j < field[i].length; j++) {
                row.append(field[i][j]).append(" ");
            }
            System.out.println(row.toString() + "|");
        }
        System.out.println("-".repeat(9));
    }

    public boolean makeStep(int x, int y, char c) {
        if (field[y][x] == 0 || field[y][x] == CELL_EMPTY) {
            field[y][x] = c;
            return true;
        }
        return false;
    }

    public char[][] getField() {
        return field;
    }

    public static GameField copy(GameField field) {
        GameField copy = new GameField();
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                copy.field[i][j] = field.field[i][j];
            }
        }
        return copy;
    }
}
