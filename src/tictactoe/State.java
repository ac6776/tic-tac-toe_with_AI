package tictactoe;

public enum State {
    PLAYING("Playing"),
    GAME_NOT_FINISHED("Game not finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    EXIT("Exiting");

    private String message;

    State(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void print() {
        System.out.println(message);
    }
}
