package tictactoe;

public class PlayerUser implements Player {
    private char c;
    private int x;
    private int y;

    public PlayerUser(char c) {
        this.c = c;
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
        while (true) {
            if (field.makeStep(x, y, c)) {
                break;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
        return true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
