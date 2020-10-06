package tictactoe;

public class Player {
    private char c;
    private String name;

    public Player(char c, String name) {
        this.c = c;
        this.name = name;
    }

    public char getC() {
        return c;
    }

    public String getName() {
        return name;
    }

    public boolean compare(Player anotherPlayer) {
        return this.c == anotherPlayer.c && this.name.equals(anotherPlayer.name);
    }
}
