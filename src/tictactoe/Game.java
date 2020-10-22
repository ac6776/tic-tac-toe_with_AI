package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Game implements Common{
    private GameField field;
    private State state;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Move currentMove;

    public Game() {
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

                        if (currentPlayer.move(field, currentMove)) {

                            if (field.checkForEnd(currentPlayer, currentMove)) {
                                if (field.checkForWin(currentPlayer, currentMove)) {
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
                        }
                    } else {
                        switch (((PlayerAI)currentPlayer).getLevel()) {
                            case EASY: {
                                do {
                                    currentMove = randomMove();
                                } while (!field.move(currentMove.x, currentMove.y, currentPlayer.getC()));
                                System.out.println("Making move level \"easy\"");
                                break;
                            }
                        }
                    }
                    field.print();
                    currentPlayer = (currentPlayer.compare(player1) ? player2 : player1);
                    break;
                }
            }
        }
    }

    private Move randomMove() {
        Random random = new Random();
        return new Move(random.nextInt(3) + 1, random.nextInt(3) + 1);
    }


//    private boolean isAbleToWin() {
//        return fillField(CELL_O) || fillField(CELL_X);
//    }

//    private boolean fillField(char cell) {
//        GameField tmpField = GameField.copy(field);
//        for (int i = 0; i < FIELD_SIZE_Y; i++) {
//            for (int j = 0; j < FIELD_SIZE_X; j++) {
//                if (tmpField.makeStep(j, i, cell)) {
//                    if (checkForWin(tmpField, j, i)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    private void init(String arg1, String arg2) {
        player1 = createPlayer(arg1, CELL_X);
        player2 = createPlayer(arg2, CELL_O);
        currentPlayer = player1;
        field = new GameField();
        state = State.PLAYING;
        field.print();
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
                        init(command[1], command[2]);
                        return true;
                    } catch (Exception e) {
                        System.out.println("Incorrect input");
                    }
                } else {
                    System.out.println("Incorrect input");
                }
                return false;
            }
            case PLAYING: {
                if (command.length != 2) {
                    System.out.println("Coordinates should be 2");
                    return false;
                } else {
                    currentMove = parseCoordinates(command[0], command[1]);
                    if (currentMove != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Move parseCoordinates(String x, String y) {
        Move move = null;
        try {
            move = new Move(Integer.parseInt(x), Integer.parseInt(y));
            if (move.x > FIELD_SIZE_X || move.x < 0) {
                System.out.printf("Coordinates should be from 1 to %d!\n", FIELD_SIZE_X);
                return null;
            }
            if (move.y > FIELD_SIZE_X || move.y < 0) {
                System.out.printf("Coordinates should be from 1 to %d!\n", FIELD_SIZE_Y);
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
        }
        return move;
    }

    private Player createPlayer(String type, char c) {
        if (type.equalsIgnoreCase("user")) {
            return new PlayerUser(c);
        }
        return new PlayerAI(c, type);
    }
}
