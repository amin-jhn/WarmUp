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
    private Rectangle current, oldCurrent;

    public Game(GameBoard board) {
        this.board = board;
        cellSize = 70;
        margin = 50;
        setSize(800,800);
        setLocationRelativeTo(null);
        setTitle("Game");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initTable();
        setResizable(false);

        setVisible(true);

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Rectangle temp = null;
                if (e.getX() >= margin &&
                        e.getY() >= margin &&
                        e.getX() <= cellSize * board.getBoardWidth() + cellSize &&
                        e.getY() <= cellSize * board.getBoardHeight() + cellSize)
                temp = rectangles[(e.getY()-margin)/cellSize][(e.getX()-margin)/cellSize];

                if (temp != current) {
                    oldCurrent = current;
                    current = temp;
                    repaint();
                }
            }
        });
    }


    @Override
    public void paint (Graphics g) {
        g.setColor(Color.YELLOW);
        if (current != null)
            g.fillRect(current.x, current.y, current.width, current.height);

        g.setColor(Color.WHITE);
        if (oldCurrent != null)
            g.fillRect(oldCurrent.x, oldCurrent.y, oldCurrent.width, oldCurrent.height);
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
