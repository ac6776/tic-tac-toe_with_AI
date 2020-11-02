package tictactoe;

public interface Player {
    char getC();
    boolean compare(Player anotherPlayer);
    boolean move(GameField field);
    Move getMove();
}
