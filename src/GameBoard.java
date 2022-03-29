import java.util.ArrayList;

public class GameBoard {
    private Boolean[][] board; // true = S      false = O     null = E
    private Boolean[][] owner; // true = B      false = R     null = E
    private boolean GameType; // true = PvE     false = PvP
    private boolean turn; // true = B        false = R
    private int bPoint;
    private int rPoint;
    private int emptycell;
    public GameBoard(int w, int h, boolean type) {
        board = new Boolean[w][h];
        owner = new Boolean[w][h];
        GameType = type;
        turn = true;
        bPoint = 0;
        rPoint = 0;
        emptycell = h * w;
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

    public ArrayList <int[]> play(boolean state, int x, int y){
        ArrayList<int[]> lines = new ArrayList<>();
        board[x][y] = state;
        owner[x][y] = turn;
        if (state)
            lines.addAll(checkGetPointS(x, y));
        else
            lines.addAll(checkGetPointO(x, y));
        if (turn) {
            bPoint += lines.size();
        } else {
            rPoint += lines.size();
        }
        turn = !turn;
        emptycell--;
        if (emptycell == 0) {
            return null;
        }
        return lines;
    }



    private ArrayList<int[]> checkGetPointS(int x, int y) {
        ArrayList<int[]> lines = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (j == 0 && i == 0) continue;
                if (x+i >= getBoardHeight() || y+j >= getBoardWidth() || x+i < 0 || y+j < 0) continue;
                if (x+i+i >= getBoardHeight() || y+j+j >= getBoardWidth() || x+i+i < 0 || y+j+j < 0) continue;
                if (board[x+i][y+j] == null || board[x+i+i][y+j+j] == null) continue;
                if (!board[x+i][y+j] && board[x+i+i][y+j+j]) {
                    lines.add(new int[]{x ,y ,x+i+i ,y+j+j});
                }
            }
        }
        return lines;
    }

    private ArrayList<int[]> checkGetPointO(int x, int y) {
        ArrayList<int[]> lines = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (j == 0 && i == 0) continue;
                if (j == 0 && i == -1) continue;
                if (x+i >= getBoardHeight() || y+j >= getBoardWidth() || x+i < 0 || y+j < 0) continue;
                if (x-i >= getBoardHeight() || y-j >= getBoardWidth() || x-i < 0 || y-j < 0) continue;
                if (board[x+i][y+j] == null || board[x-i][y-j] == null) continue;
                if (board[x+i][y+j] && board[x - i][y - j]) {
                    lines.add(new int[]{x+i,y+j,x-i,y-j});
                }
            }
        }

        return lines;
    }

    public int getbPoint() {
        return bPoint;
    }

    public int getrPoint() {
        return rPoint;
    }

    public Boolean getState(int x, int y) {
        return board[x][y];
    }
}
