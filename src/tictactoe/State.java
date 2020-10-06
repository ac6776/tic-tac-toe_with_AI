package tictactoe;

public enum State {
//    PLAYING,
//    GAME_NOT_FINISHED,
//    DRAW,
//    X_WINS,
//    O_WINS;
    PLAYING("Playing"),
    GAME_NOT_FINISHED("Game not finished"),
    DRAW("Draw"),
    X_WINS("X wins"),
    O_WINS("O wins");

    private String message;

    State(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
//enum Playing implements State {
//    PLAYING
//}
//enum GameOver implements State {
//    GAME_NOT_FINISHED("Game not finished"),
//    DRAW("Draw"),
//    X_WINS("X wins"),
//    O_WINS("O wins");
//
//    private String message;
//
//    GameOver(String message) {
//        this.message = message;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//}
