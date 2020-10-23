package tictactoe;

import java.util.Random;

public class PlayerAI implements Player, Common {
    private char c;
    private Level level;
    private int x;
    private int y;

    public PlayerAI(char c, String level) {
        this.c = c;
        this.level = Level.valueOf(level.toUpperCase());
    }

    @Override
    public char getC() {
        return c;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean compare(Player anotherPlayer) {
        return this.c == anotherPlayer.getC();
    }

    @Override
    public boolean move(GameField field) {
        do {
            switch (level) {
                case EASY: {
                    setRandomX();
                    setRandomY();
                    break;
                }
                case MEDIUM: {
                    if (field.checkRow(y, c) == 2) {
                        setRandomX();
                    } else if (field.checkColumn(x, c) == 2) {
                        setRandomY();
                    } else {
                        if (blockRow(field) != -1) {
                            y = blockRow(field);
                            setRandomX();
                        } else if (blockCol(field) != -1) {
                            x = blockCol(field);
                            setRandomY();
                        } else {
                            setRandomX();
                            setRandomY();
                        }
                    }
                    break;
                }
            }
        } while (!field.makeStep(x, y, c));
        System.out.println("Making move level \"" + level.toString().toLowerCase() + "\"");
        return true;
    }

    public Level getLevel() {
        return level;
    }

    private void setRandomX() {
        Random random = new Random();
        x = random.nextInt(3);
    }

    private void setRandomY() {
        Random random = new Random();
        y = FIELD_SIZE_Y - 1 - random.nextInt(3);
    }

    enum Level {
        EASY,
        MEDIUM
    }

    private int blockRow(GameField field) {
        for (int i = 0; i < 3; i++) {
            if (field.checkRow(i, c == CELL_X ? CELL_O: CELL_X) == 2) {
                return i;
            }
        }
        return -1;
    }

    private int blockCol(GameField field) {
        for (int i = 0; i < 3; i++) {
            if (field.checkColumn(i, c == CELL_X ? CELL_O: CELL_X) == 2) {
                return i;
            }
        }
        return -1;
    }
}
