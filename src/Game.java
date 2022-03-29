import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Game extends JFrame {
    GameBoard board;
    Rectangle[][] rectangles;
    private int cellSize;
    private int margin;
    private int currentX = -1, currentY = -1, oldCurrentY = -1, oldCurrentX = -1;
    private Font defaultFont = new Font("tahoma", Font.BOLD,40);


    public Game(GameBoard board) {
        this.board = board;
        cellSize = 70;
        margin = 50;
        setSize(board.getBoardWidth() * cellSize + 2 * margin, board.getBoardHeight() * cellSize + 2 * margin);
        setLocationRelativeTo(null);
        setTitle("Game");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initTable();
        setResizable(false);

        setVisible(true);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int tempX = -1;
                int tempY = -1;
                if (e.getX() >= margin &&
                        e.getY() >= margin &&
                        e.getX() <= cellSize * board.getBoardWidth() + cellSize &&
                        e.getY() <= cellSize * board.getBoardHeight() + cellSize) {

                    tempX = (e.getY() - margin) / cellSize;
                    tempY = (e.getX() - margin) / cellSize;
                    if (board.getState(tempX, tempY) != null) {
                        tempX = tempY = -1;
                    }
            }
                if (tempX != currentX || tempY != currentY) {
                    oldCurrentX = currentX;
                    oldCurrentY = currentY;
                    currentX = tempX;
                    currentY = tempY;
                    repaint();
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentX != -1 && currentY != -1) {
                    play();

                }
            }
        });
    }

    private void play() {
        String[] options = new String[]{"S" , "O"};
        setEnabled(false);
        int answer = JOptionPane.showOptionDialog(this, "What you need?", board.getTurn() ? "Blue" : "Red",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (answer != -1) {
            lines.addAll(board.play(answer == 0, currentX, currentY));
            wasPlayed = true;
            repaint();
        }
        setEnabled(true);
        requestFocus();
    }

    boolean wasPlayed = false;
    boolean firstPaint = true;
    ArrayList <int[]> lines = new ArrayList<>();

    @Override
    public void paint (Graphics g) {
        if (firstPaint){
            g.setColor(Color.BLACK);
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            for (int i = 0; i < board.getBoardHeight(); i++) {
                for (int j = 0; j < board.getBoardWidth(); j++) {
                    g.drawRect(rectangles[i][j].x, rectangles[i][j].y, rectangles[i][j].width, rectangles[i][j].height);
                }
            }
            firstPaint = false;
        }
        if (wasPlayed){
            g.setColor(board.getTurn()? new Color(0xDA7070) : new Color(0x6161D7));
                g.fillRect(rectangles[currentX][currentY].x, rectangles[currentX][currentY].y,
                        rectangles[currentX][currentY].width, rectangles[currentX][currentY].height);
            g.setColor(board.getTurn()? Color.BLACK : Color.WHITE);
            g.setFont(defaultFont);
            g.drawString(board.getState(currentX, currentY) ? "S" : "O",
                    rectangles[currentX][currentY].x + 21, rectangles[currentX][currentY].y + 50);
            g.setColor(board.getTurn()? Color.RED : Color.blue);
            ((Graphics2D) g).setStroke(new BasicStroke(4));
            while (!lines.isEmpty()) {
                int[] coor = lines.remove(0);
                g.drawLine(rectangles[coor[0]][coor[1]].x + cellSize/2,
                        rectangles[coor[0]][coor[1]].y + cellSize/2,
                        rectangles[coor[2]][coor[3]].x + cellSize/2,
                        rectangles[coor[2]][coor[3]].y + cellSize/2);
            }
            currentX = currentY = oldCurrentY = oldCurrentX = -1;
            wasPlayed = false;

        } else {
            g.setColor(Color.YELLOW);
            if (currentX != -1)
                g.fillRect(rectangles[currentX][currentY].x + 2, rectangles[currentX][currentY].y + 2,
                        rectangles[currentX][currentY].width - 4, rectangles[currentX][currentY].height - 4);

            g.setColor(Color.WHITE);
            if (oldCurrentX != -1)
                g.fillRect(rectangles[oldCurrentX][oldCurrentY].x + 2, rectangles[oldCurrentX][oldCurrentY].y + 2,
                        rectangles[oldCurrentX][oldCurrentY].width - 4, rectangles[oldCurrentX][oldCurrentY].height - 4);
        }

    }

    private void initTable() {
        rectangles = new Rectangle[board.getBoardHeight()][board.getBoardWidth()];
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                rectangles[i][j] = new Rectangle(j * cellSize + margin, i * cellSize + margin, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(10, 10, true);
        Game game = new Game(gameBoard);
    }
}
