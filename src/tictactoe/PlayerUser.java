package tictactoe;

public class PlayerUser implements Player, Common {
    private char c;

    public PlayerUser(char c) {
        this.c = c;
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
        if (!field.makeStep(move.x, move.y, c)) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        return true;
    }
}
