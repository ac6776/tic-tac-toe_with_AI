package tictactoe;

public class PlayerUser implements Player, Common {
    private char c;
    private Move move;

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
    public boolean move(GameField field) {
        if (!field.makeStep(move, c)) {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        return true;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setMove(int x, int y) {
        move = new Move(x, y);
    }

    @Override
    public Move getMove() {
        return move;
    }
}
