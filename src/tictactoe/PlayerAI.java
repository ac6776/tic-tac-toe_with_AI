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
            if (level == Level.MEDIUM) {
                if (field.checkRow(this) == 2) {
                    setRandomX();
                } else if (field.checkColumn(this) == 2) {
                    setRandomY();
                } else {
                    setRandomX();
                    setRandomY();
                }
            } else {
                setRandomX();
                setRandomY();
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
}
