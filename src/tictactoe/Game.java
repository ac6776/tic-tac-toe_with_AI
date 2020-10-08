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
                    parseInput(input);
                    break;
                }
                case PLAYING: {
                    if (currentPlayer instanceof PlayerUser) {
                        String input;
                        do {
                            System.out.println("Enter the coordinates:");
                            input = scanner.nextLine();
                        } while (!parseInput(input));
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

    private boolean parseInput(String input) {
        String[] command = input.trim().toLowerCase().split(" ");
        switch (state) {
            case INIT: {
                if("exit".equals(input.trim().toLowerCase())) {
                    state = State.EXIT;
                    return true;
                } else if (command[0].equals("start")) {
                    try {
                        player1 = createPlayer(command[1], CELL_X);
                        player2 = createPlayer(command[2], CELL_O);
                        currentPlayer = player1;
                        state = State.PLAYING;
                        return true;
                    } catch (Exception e) {
                        System.out.println("Incorrect input");
                    }
                }
                return false;
            }
            case PLAYING: {
                if (command.length != 2) {
                    System.out.println("Coordinates should be 2");
                } else {
                    if (((PlayerUser)currentPlayer).setX(command[0]) && ((PlayerUser)currentPlayer).setY(command[1])) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    private Player createPlayer(String type, char c) {
        if (type.equalsIgnoreCase("user")) {
            return new PlayerUser(c);
        }
        return new PlayerAI(c, type);
    }
}
