package tictactoe;

import java.util.LinkedList;
import java.util.List;
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
        return field.move(move.x, move.y, this.c);
    }
//        switch (level) {
//            case EASY:
//                do {
//                    setRandomX();
//                    setRandomY();
//                } while (!field.makeStep(x, y, c));
//                break;
//            case MEDIUM:
//                do {
//                    if (field.checkRow(y, c) == 2) {
//                        setRandomX();
//                    } else if (field.checkColumn(x, c) == 2) {
//                        setRandomY();
//                    } else {
//                        if (blockRow(field) != -1) {
//                            y = blockRow(field);
//                            setRandomX();
//                        } else if (blockCol(field) != -1) {
//                            x = blockCol(field);
//                            setRandomY();
//                        } else {
//                            setRandomX();
//                            setRandomY();
//                        }
//                    }
//                } while (!field.makeStep(x, y, c));
//                break;
//            case HARD:
//                minimax(field, this);
//                break;
//        }
//        System.out.println("Making move level \"" + level.toString().toLowerCase() + "\"");
//        return true;
//    }
//
    public Level getLevel() {
        return level;
    }
//
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
//
//    private void minimax(GameField newField) {
//
//        List<Move> availableMoves = getAvailableMoves(newField);
//
//        int score = 0;
//        if (newField.checkForWin(this)) {
//            score = 10;
//        } else if (newField.checkForWin(game.getPlayer(this.c == CELL_O ? CELL_X : CELL_O))) {
//            score = -10;
//        }
//
//        List<Move> moves = new LinkedList<>();
//        for (int i = 0; i < availableMoves.size(); i++) {
//            Move move;
//            move = availableMoves.get(i);
//            x = move.x;
//            y = move.y;
//            move(newField);
//
//
//        }
//    }

//    private List<Move> getAvailableMoves (GameField field) {
//        List<Move> availableMoves = new LinkedList<>();
//
//        for (int i = 0; i < field.getField().length; i++) {
//            for (int j = 0; j < field.getField()[i].length; j++) {
//                if (field.getField()[i][j] == CELL_EMPTY) {
//                    availableMoves.add(new Move(j, i));
//                }
//            }
//        }
//        return availableMoves;
//    }


//    private int getScore (GameField field, Player player) {
//        if (player instanceof PlayerAI) {
//            if (field.checkForWin(player)) {
//                return 10;
//            }
//        } else if (player instanceof PlayerUser) {
//            if (field.checkForWin(player)) {
//                return -10;
//            }
//        }
//        return 0;
//    }
}


