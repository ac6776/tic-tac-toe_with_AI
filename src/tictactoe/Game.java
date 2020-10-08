package tictactoe;

import java.util.Scanner;

public class Game implements Common{
    private GameField field;
    private State state;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game() {
        field = new GameField();
        state = State.INIT;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (state != State.EXIT) {
            switch (state) {
                case INIT: {
                    state.print();
                    String input = scanner.nextLine();
                    try {
                        parseInput(input);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case PLAYING: {
                    if (currentPlayer instanceof PlayerUser) {
                        System.out.println("Enter the coordinates:");
                        String input = scanner.nextLine();
                        try {
                            parseInput(input);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    currentPlayer.move(field);
                    field.print();

                    if (checkForEnd()) {
                        if (checkForWin(field, currentPlayer.getX(), currentPlayer.getY())) {
                            if (currentPlayer.getC() == CELL_X) {
                                state = State.X_WINS;
                                state.print();
                            }
                            if (currentPlayer.getC() == CELL_O) {
                                state = State.O_WINS;
                                state.print();
                            }
                        } else {
                            state = State.DRAW;
                            state.print();
                        }
                        state = State.INIT;
                    }
                    currentPlayer = (currentPlayer.compare(player1) ? player2 : player1);
                    break;
                }
            }
        }
    }

    public boolean checkForEnd() {
        return checkForWin(field, currentPlayer.getX(), currentPlayer.getY()) || checkForDraw(field);
    }

    public boolean checkForWin(GameField field, int x, int y) {
        return checkRow(field, y) || checkColumn(field, x) || checkMainDiag(field) || checkSecondDiag(field);
    }

    private boolean checkRow(GameField field, int y) {
        int counter = 0;
        for (int j = 0; j < LENGTH_FOR_WIN - 1; j++) {
            if (field.getField()[y][j] != CELL_EMPTY) {
                if (field.getField()[y][j] == field.getField()[y][j + 1]) {
                    counter++;
                }
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }
    private boolean checkColumn(GameField field, int x) {
        int counter = 0;
        for (int i = 0; i < LENGTH_FOR_WIN - 1; i++) {
            if (field.getField()[i][x] != CELL_EMPTY) {
                if (field.getField()[i][x] == field.getField()[i + 1][x]) {
                    counter++;
                }
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }
    private boolean checkMainDiag(GameField field) {
        int counter = 0;
        for (int i = 0; i < LENGTH_FOR_WIN - 1; i++) {
            if (field.getField()[i][i] != CELL_EMPTY) {
                if (field.getField()[i][i] == field.getField()[i + 1][i + 1]) {
                    counter++;
                }
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }
    private boolean checkSecondDiag(GameField field) {
        int counter = 0;
        for (int j = LENGTH_FOR_WIN - 1, i = 0; j > 0; j--, i++) {
            if (field.getField()[i][j] != CELL_EMPTY) {
                if (field.getField()[i][j] == field.getField()[i + 1][j - 1]) {
                    counter++;
                }
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }

    private boolean checkForDraw(GameField field) {
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                if (field.getField()[i][j] == CELL_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAbleToWin() {
        return fillField(CELL_O) || fillField(CELL_X);
    }

    private boolean fillField(char cell) {
        GameField tmpField = GameField.copy(field);
        for (int i = 0; i < FIELD_SIZE_Y; i++) {
            for (int j = 0; j < FIELD_SIZE_X; j++) {
                if (tmpField.makeStep(j, i, cell)) {
                    if (checkForWin(tmpField, j, i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void parseInput(String input) throws Exception {
        String[] command = input.trim().toLowerCase().split(" ");
        switch (state) {
            case INIT: {
                if("exit".equals(input.trim().toLowerCase())) {
                    state = State.EXIT;
                } else if (command[0].equals("start")) {
                    player1 = createPlayer(command[1], CELL_X);
                    player2 = createPlayer(command[2], CELL_O);
                    currentPlayer = player1;
                    state = State.PLAYING;
                }
                break;
            }
            case PLAYING: {
                int x, y;
                if (command.length != 2) {
                    throw new Exception("Coordinates should be 2");
                }
                try {
                    x = Integer.parseInt(command[0]);
                    y = Integer.parseInt(command[1]);
                } catch (NumberFormatException e) {
                    throw new Exception("You should enter numbers!");
                }
                if (x > FIELD_SIZE_X || x < 0 || y > FIELD_SIZE_Y || y < 0) {
                    throw new Exception(String.format("Coordinates should be from 1 to %d!", FIELD_SIZE_X));
                }
                ((PlayerUser)currentPlayer).setX(x - 1);
                ((PlayerUser)currentPlayer).setY(FIELD_SIZE_Y - y);
                break;
            }
        }
    }

    private Player createPlayer(String type, char c) {
        if (type.equalsIgnoreCase("user")) {
            return new PlayerUser(c);
        }
        return new PlayerAI(c, type);
    }
}
