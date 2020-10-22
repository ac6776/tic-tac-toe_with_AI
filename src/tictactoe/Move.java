package tictactoe;

public class Move implements Common {
    int x;
    int y;

    public Move(int x, int y) {
        this.x = x - 1;
        this.y = FIELD_SIZE_Y - y;
    }

    @Override
    public String toString() {
        return "Move{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
