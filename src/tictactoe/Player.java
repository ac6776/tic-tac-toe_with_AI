package tictactoe;

public class Player {
    private char c;
    private String name;
    private Type type;

    public Player(char c, String type) {
        this.c = c;
        this.type = Type.valueOf(type.toUpperCase());
    }

    public char getC() {
        return c;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean compare(Player anotherPlayer) {
        return this.c == anotherPlayer.c && this.type == anotherPlayer.type;
    }

    public boolean move(int x, int y, GameField field) {
        return field.makeStep(x, y, c);
    }

    enum Type {
        USER,
        EASY,
        MEDIUM
    }
}
