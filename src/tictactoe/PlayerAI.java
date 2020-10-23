package tictactoe;

import java.util.Random;

public class PlayerAI implements Player, Common {
    private char c;
    private Level level;

    public PlayerAI(char c, String level) {
        this.c = c;
        this.level = Level.valueOf(level.toUpperCase());
    }

    @Override
    public char getC() {
        return c;
    }

    @Override
    public boolean compare(Player anotherPlayer) {
        return this.c == anotherPlayer.getC();
    }

    @Override
    public boolean move(GameField field, Move move) {
        System.out.println("Making move level \"" + level.toString().toLowerCase() + "\"");
        return field.makeStep(move.x, move.y, c);
    }

    public Level getLevel() {
        return level;
    }

//    private void setRandomX() {
//        Random random = new Random();
//        x = random.nextInt(3);
//    }
//
//    private void setRandomY() {
//        Random random = new Random();
//        y = FIELD_SIZE_Y - 1 - random.nextInt(3);
//    }

    enum Level {
        EASY,
        MEDIUM,
        HARD
    }

//    private int blockRow(GameField field) {
//        for (int i = 0; i < 3; i++) {
//            if (field.checkRow(i, c == CELL_X ? CELL_O: CELL_X) == 2) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//    private int blockCol(GameField field) {
//        for (int i = 0; i < 3; i++) {
//            if (field.checkColumn(i, c == CELL_X ? CELL_O: CELL_X) == 2) {
//                return i;
//            }
//        }
//        return -1;
//    }
}
