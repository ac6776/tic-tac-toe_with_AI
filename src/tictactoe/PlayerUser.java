package tictactoe;

import java.sql.Struct;

public class PlayerUser implements Player, Common {
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

    public boolean setX(String inputX) {
        int x = parseCoordinate(inputX);
        if (x != -1) {
            this.x = x - 1;
            return true;
        }
        return false;
    }

    public boolean setY(String inputY) {
        int y = parseCoordinate(inputY);
        if (y != -1) {
            this.y = FIELD_SIZE_Y - y;
            return true;
        }
        return false;
    }

    public int parseCoordinate(String input) {
        int coordinate = -1;
        try {
            coordinate = Integer.parseInt(input);
            if (coordinate > FIELD_SIZE_X || coordinate < 0) {
                System.out.printf("Coordinates should be from 1 to %d!\n", FIELD_SIZE_X);
                coordinate = -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
        }
        return coordinate;
    }
}