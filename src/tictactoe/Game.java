package tictactoe;

import java.util.Scanner;

public class Game implements Common{
    private GameField field;
    private State state;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

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
                    }
                    if (currentPlayer.move(field)) {
                        field.print();

                        if (field.checkForEnd(currentPlayer)) {
                            if (field.checkForWin(currentPlayer)) {
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
                    }
                    break;
                }
            }
        }
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
                    return ((PlayerUser) currentPlayer).setX(command[0]) && ((PlayerUser) currentPlayer).setY(command[1]);
                }
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
