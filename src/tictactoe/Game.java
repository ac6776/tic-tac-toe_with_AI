package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Game implements Common{
    private GameField field;
    private State state;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int x;
    private int y;

    public Game() {
        field = new GameField();
        state = null;
    }

    public void play() {
        while (state != State.EXIT) {
            while (state != State.PLAYING) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Input command:");
                String input = scanner.nextLine();
                if(input.equals("exit")) {
                    state = State.EXIT;
                    break;
                } else {
                    getCommandFromInput(input);
                    field.print();
                }
            }
            while (state == State.PLAYING) {
                if (currentPlayer.getType() == Player.Type.USER) {
                    while (true) {
                        try {
                            getCoordinatesFromInput();
                            break;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else {
                    getCoordinatesFromRandom();
                }
                boolean stepMade = field.makeStep(x, y, currentPlayer.getC());
                if (!stepMade) {
                    if (currentPlayer.getType() == Player.Type.USER) {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                } else {
                    if (currentPlayer.getType() != Player.Type.USER) {
                        System.out.println("Making move level \"" + currentPlayer.getType().toString().toLowerCase() + "\"");
                    }
                    field.print();
                    if (checkForEnd()) {
                        if (checkForWin(field, x, y)) {
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
                    }
                    currentPlayer = (currentPlayer.compare(player1) ? player2 : player1);
                }
            }
        }
    }

    private void getCoordinatesFromInput() throws Exception {
        System.out.print("Enter the coordinates:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] coords = input.split(" ");
        if (coords.length != 2) {
            throw new Exception("Coordinates should be 2");
        }
        try {
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
        } catch (NumberFormatException e) {
            throw new Exception("You should enter numbers!");
        }
        if (x > FIELD_SIZE_X || x < 0 || y > FIELD_SIZE_Y || y < 0) {
            throw new Exception(String.format("Coordinates should be from 1 to %d!", FIELD_SIZE_X));
        }
        x = x - 1;
        y = FIELD_SIZE_Y - y;
    }

    private void getCoordinatesFromRandom() {
        Random random = new Random();
        x = random.nextInt(3);
        y = FIELD_SIZE_Y - 1 - random.nextInt(3);
    }

    private void getCommandFromInput(String input) {
        input = input.trim();
        if (input.startsWith("start")) {
            try {
                player1 = new Player(CELL_X, input.split(" ")[1].toUpperCase());
                player2 = new Player(CELL_O, input.split(" ")[2].toUpperCase());
                currentPlayer = player1;
                state = State.PLAYING;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkForEnd() {
        return checkForWin(field, x, y) || checkForDraw(field);
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
}
