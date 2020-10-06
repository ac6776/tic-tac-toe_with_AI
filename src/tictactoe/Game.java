package tictactoe;

import java.util.Scanner;

public class Game implements Common{
    private GameField field;
    private State state;
    private char[] inputArray;
    private char currentStep;
    private int x;
    private int y;

    public Game() {
        field = new GameField();
        state = null;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (state != State.PLAYING) {
            System.out.println("Enter cells: ");
            String input = scanner.nextLine();
            try {
                init(input);
                int counter = 0;
                for (int i = 0; i < FIELD_SIZE_Y; i++) {
                    for (int j = 0; j < FIELD_SIZE_X; j++) {
                        field.makeStep(j, i, inputArray[counter++]);
                    }
                }
                state = State.PLAYING;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        field.print();
        while (state == State.PLAYING) {
            System.out.print("Enter the coordinates:");
            String input = scanner.nextLine();
            try {
                parseCoordinates(input);
                boolean stepMade = field.makeStep(x, y, currentStep);
                if (!stepMade) {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    field.print();
                    if (checkForEnd()) {
                        if (checkForWin(field, x, y)) {
                            if (currentStep == CELL_X) {
                                state = State.X_WINS;
                            }
                            if (currentStep == CELL_O) {
                                state = State.O_WINS;
                            }
                        } else {
                            state = State.DRAW;
                        }
                    } else {
                        state = State.GAME_NOT_FINISHED;
                    }
                    currentStep = (currentStep == CELL_X ? CELL_O : CELL_X);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(state.getMessage());
    }

    private void init(String input) throws Exception {
        if (input.length() != FIELD_SIZE) {
            throw new Exception("Incorrect length");
        }
        inputArray = new char[FIELD_SIZE];
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == 'X' || c == 'x') {
                inputArray[i] = CELL_X;
                countX++;
            } else if (c == 'O' || c =='o') {
                inputArray[i] = CELL_O;
                countO++;
            } else if (c == '_') {
                inputArray[i] = CELL_EMPTY;
            } else {
                throw new Exception("Input incorrect");
            }
        }
        currentStep = countO >= countX ? CELL_X : CELL_O;
    }

    private void parseCoordinates(String input) throws Exception {
        String[] coords = input.split(" ");
//        if (coords.length != 2) {
//            throw new Exception("Coordinates should be 2");
//        }
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

    public boolean checkForEnd() {
        return checkForWin(field, x, y) || checkForDraw(field);
    }

    public boolean checkForWin(GameField field, int x, int y) {
        return checkRow(field, y) || checkColumn(field, x) || checkMainDiag(field) || checkSecondDiag(field);
    }

    private boolean checkRow(GameField field, int y) {
        int counter = 0;
        for (int j = 0; j < LENGTH_FOR_WIN - 1; j++) {
            if (field.getField()[y][j] == field.getField()[y][j + 1]) {
                counter++;
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }
    private boolean checkColumn(GameField field, int x) {
        int counter = 0;
        for (int i = 0; i < LENGTH_FOR_WIN - 1; i++) {
            if (field.getField()[i][x] == field.getField()[i + 1][x]) {
                counter++;
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }
    private boolean checkMainDiag(GameField field) {
        int counter = 0;
        for (int i = 0; i < LENGTH_FOR_WIN - 1; i++) {
            if (field.getField()[i][i] == field.getField()[i + 1][i + 1]) {
                counter++;
            }
        }
        return counter == LENGTH_FOR_WIN - 1;
    }
    private boolean checkSecondDiag(GameField field) {
        int counter = 0;
        for (int j = LENGTH_FOR_WIN - 1, i = 0; j > 0; j--, i++) {
            if (field.getField()[i][j] == field.getField()[i + 1][j - 1]) {
                counter++;
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

    private boolean isAbleToWin(GameField field) {
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
