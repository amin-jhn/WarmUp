public class GameBoard {
    private Boolean[][] board;
    private Boolean[][] owner;
    private boolean GameType;
    public GameBoard(int w, int h, boolean type) {
        board = new Boolean[w][h];
        owner = new Boolean[w][h];
        GameType = type;
    }
}
