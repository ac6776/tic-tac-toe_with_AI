package tictactoe;

import java.util.LinkedList;
import java.util.List;

public class GameField implements Common{
    private char[][] field;

    public GameField() {
        field = new char[3][3];
        fill(CELL_EMPTY);
    }

    public void fill(char c) {
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                makeStep(new Move(j, i), c);
            }
        }
    }

    public void print(){
        System.out.println("-".repeat(9));
        for (char[] chars : field) {
            StringBuilder row = new StringBuilder("| ");
            for (char aChar : chars) {
                row.append(aChar).append(" ");
            }
            System.out.println(row.toString() + "|");
        }
        System.out.println("-".repeat(9));
    }

//    public boolean makeStep(int x, int y, char c) {
//        if (field[y][x] == 0 || field[y][x] == CELL_EMPTY) {
//            field[y][x] = c;
//            return true;
//        }
//        return false;
//    }

    public boolean makeStep(Move move, char c) {
        if (field[move.y][move.x] == 0 || field[move.y][move.x] == CELL_EMPTY) {
            field[move.y][move.x] = c;
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

    public boolean checkForEnd(Player player) {
        return checkForWin(player) || checkForDraw();
    }

    public boolean checkForWin(Player player) {
        return checkRow(player) == 3 || checkColumn(player) == 3;
    }

    public int checkRow(Player player) {
        int counterCELL_EMPTY = 0;
        int counter = 0;
        for (int j = 0; j < LENGTH_FOR_WIN; j++) {
            if (field[player.getMove().y][j] == CELL_EMPTY) {
                counterCELL_EMPTY++;
            }
            if (field[player.getMove().y][j] == player.getC()) {
                counter++;
            }
        }
        if (counter == 2) {
            if (counterCELL_EMPTY == 1) {
                return 2;
            }
        }
        if (counter == 3) {
            return 3;
        }
        return -1;
    }
    public int checkColumn(Player player) {
        int counterCELL_EMPTY = 0;
        int counter = 0;
        for (int i = 0; i < LENGTH_FOR_WIN; i++) {
            if (field[i][player.getMove().x] == CELL_EMPTY) {
                counterCELL_EMPTY++;
            }
            if (field[i][player.getMove().x] == player.getC()) {
                counter++;
            }
        }
        if (counter == 2) {
            if (counterCELL_EMPTY == 1) {
                return 2;
            }
        }
        if (counter == 3) {
            return 3;
        }
        return -1;
    }

    private boolean checkMainDiag() {
        int counter = 0;
        for (int i = 0; i < LENGTH_FOR_WIN - 1; i++) {
            if (field[i][i] != CELL_EMPTY) {
                if (field[i][i] == field[i + 1][i + 1]) {
                    counter++;
                }
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }

    private boolean checkSecondDiag() {
        int counter = 0;
        for (int j = LENGTH_FOR_WIN - 1, i = 0; j > 0; j--, i++) {
            if (field[i][j] != CELL_EMPTY) {
                if (field[i][j] == field[i + 1][j - 1]) {
                    counter++;
                }
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }

    private boolean checkForDraw() {
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                if (field[i][j] == CELL_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Move> getAvailableMoves() {
        List<Move> availableMoves = new LinkedList<>();
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                if (field[i][j] == CELL_EMPTY) {
                    availableMoves.add(new Move(j, i));
                }
            }
        }
        return availableMoves;
    }
}
