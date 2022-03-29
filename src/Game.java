import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Game extends JFrame {
    GameBoard board;
    Rectangle[][] rectangles;
    private int cellSize;
    private int margin;
    private int currentX = -1, currentY = -1, oldCurrentY = -1, oldCurrentX = -1;

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
        int answer = JOptionPane.showOptionDialog(this, "What you need?", board.getTurn() ? "Blue" : "Red",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (answer != -1)

        board.play(answer==0 , currentX, currentY);
    }
    @Override
    public void paint (Graphics g) {
        g.setColor(Color.YELLOW);
        if (currentX != -1)
            g.fillRect(rectangles[currentX][currentY].x, rectangles[currentX][currentY].y,
                    rectangles[currentX][currentY].width, rectangles[currentX][currentY].height);

        g.setColor(Color.WHITE);
        if (oldCurrentX != -1)
            g.fillRect(rectangles[oldCurrentX][oldCurrentY].x, rectangles[oldCurrentX][oldCurrentY].y,
                    rectangles[oldCurrentX][oldCurrentY].width, rectangles[oldCurrentX][oldCurrentY].height);
        g.setColor(Color.BLACK);

        ((Graphics2D) g).setStroke(new BasicStroke(4));
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                g.drawRect(rectangles[i][j].x, rectangles[i][j].y, rectangles[i][j].width, rectangles[i][j].height);
            }
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
        GameBoard gameBoard = new GameBoard(3, 3, true);
        Game game = new Game(gameBoard);
    }
}
