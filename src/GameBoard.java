public class GameBoard {
    private Boolean[][] board;
    private Boolean[][] owner;
    private boolean GameType;
    public GameBoard(int w, int h, boolean type) {
        board = new Boolean[w][h];
        owner = new Boolean[w][h];
        GameType = type;
    }

    public int getBoardWidth(){
        return board[0].length;
    }

    public int getBoardHeight(){
        return board.length;
    }
}
