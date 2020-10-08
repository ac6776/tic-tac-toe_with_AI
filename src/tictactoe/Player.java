package tictactoe;

public interface Player {
    char getC();
    int getX();
    int getY();
    boolean compare(Player anotherPlayer);
    boolean move(GameField field);
}
