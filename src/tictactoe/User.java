package tictactoe;

public class User implements Player {
    private char c;

    public User(char c) {
        this.c = c;
    }

    @Override
    public char getC() {
        return c;
    }

    @Override
    public boolean compare(Player anotherPlayer) {
        return false;
    }

    @Override
    public boolean move(GameField field) {
        return false;
    }
}
