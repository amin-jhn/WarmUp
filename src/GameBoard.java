public class GameBoard {
    private Boolean[][] board; // true = S      false = O     null = E
    private Boolean[][] owner; // true = B      false = R     null = E
    private boolean GameType; // true = PvE     false = PvP
    private boolean turn; // true = B        false = R
    public GameBoard(int w, int h, boolean type) {
        board = new Boolean[w][h];
        owner = new Boolean[w][h];
        GameType = type;
        turn = true;
    }

    public int getBoardWidth(){
        return board[0].length;
    }

    public int getBoardHeight(){
        return board.length;
    }


    public boolean getTurn() {
        return turn;
    }

    public void play(boolean state, int x, int y){
        board[x][y] = state;
        owner[x][y] = turn;
        turn = !turn;
    }
}
