package tictactoe;

public class AI implements Player {
    private char c;
    private Level level;
    private int x;
    private int y;

    public AI(char c, String level) {
        this.c = c;
        this.level = Level.valueOf(level.toUpperCase());
    }

    public char getC() {
        return c;
    }

    @Override
    public boolean compare(Player anotherPlayer) {
        return this.c == anotherPlayer.getC();
    }
    @Override
    public boolean move(GameField field) {
        return field.makeStep(x, y, c);
    }

    public Level getLevel() {
        return level;
    }

    enum Level {
        EASY,
        MEDIUM
    }
}
