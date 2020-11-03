package tictactoe;

import java.util.List;
import java.util.Scanner;

public class Game implements Common{
    private GameField field;
    private State state;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
//    private List<Move> availableMoves;
//    private Move currentMove;

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

    private void init(String arg1, String arg2) {
        player1 = createPlayer(arg1, CELL_X);
        player2 = createPlayer(arg2, CELL_O);
        currentPlayer = player1;
        field = new GameField();
        state = State.PLAYING;
//        availableMoves = field.getAvailableMoves();
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
                    try {
                        int x = Integer.parseInt(command[0]);
                        int y = Integer.parseInt(command[1]);
                        if ((x > 0 && x <= FIELD_SIZE_X) && (y > 0 && y <= FIELD_SIZE_Y)) {
                            ((PlayerUser)currentPlayer).setMove(x - 1, FIELD_SIZE_Y - y);
                            return true;
                        } else {
                            System.out.println("Coordinates must be between from 1 to 3!");
                        }
                    }catch (Exception e) {
                        System.out.println("You should enter numbers!");
                    }
                    return false;
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

//    private Move getMoveFromAvailableMoves(int x, int y) {
//        for (int i = 0; i < availableMoves.size(); i++) {
//            if (availableMoves.get(i).x == x && availableMoves.get(i).y == y) {
//                return availableMoves.remove(i);
//            }
//        }
//        return null;
//    }
}



/*
do {

            switch (level) {
                case EASY: {
                    setRandomX();
                    setRandomY();
                    break;
                }
                case MEDIUM: {
                    if (field.checkRow(y, c) == 2) {
                        setRandomX();
                    } else if (field.checkColumn(x, c) == 2) {
                        setRandomY();
                    } else {
                        if (blockRow(field) != -1) {
                            y = blockRow(field);
                            setRandomX();
                        } else if (blockCol(field) != -1) {
                            x = blockCol(field);
                            setRandomY();
                        } else {
                            setRandomX();
                            setRandomY();
                        }
                    }
                    break;
                }
            }
        } while (!field.makeStep(x, y, c));
        System.out.println("Making move level \"" + level.toString().toLowerCase() + "\"");

 */