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
        if (state)
            checkGetPointS(x, y);
        else
            checkGetPointO(x, y);
        turn = !turn;
    }

    private void checkGetPointS(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (j == 0 && i == 0) continue;
                if (x+i >= getBoardHeight() || y+j >= getBoardWidth() || x+i < 0 || y+j < 0) continue;
                if (x+i+i >= getBoardHeight() || y+j+j >= getBoardWidth() || x+i+i < 0 || y+j+j < 0) continue;
                if (board[x+i][y+j] == null || board[x+i+i][y+j+j] == null) continue;
                if (!board[x+i][y+j] && board[x+i+i][y+j+j]) System.out.println((x+i) + " " + (y+j));
            }
        }
        System.out.println("************");
    }

    private void checkGetPointO(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (j == 0 && i == 0) continue;
                if (j == 0 && i == -1) continue;
                if (x+i >= getBoardHeight() || y+j >= getBoardWidth() || x+i < 0 || y+j < 0) continue;
                if (x-i >= getBoardHeight() || y-j >= getBoardWidth() || x-i < 0 || y-j < 0) continue;
                if (board[x+i][y+j] == null || board[x-i][y-j] == null) continue;
                if (board[x+i][y+j] && board[x - i][y - j]) System.out.println((x+i) + " " + (y+j));
            }
        }
        System.out.println("************");
    }

    public Boolean getState(int x, int y) {
        return board[x][y];
    }
}
