import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private boolean gameTYPE; //True means PvE
    private int gameWidth;
    private int gameHeight;
    private Font defaultFont = new Font("tahoma", Font.BOLD,20);
    private JPanel bottomPnl;
    private JTextField widthTxt;
    private JTextField heightTxt;
    private Main me;

    public Main() {
        me = this;
        setSize(600,600);
        setLocationRelativeTo(null);
        setTitle("SOS");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        gameWidth = 3;
        gameHeight = 3;
        initTopPnl();
        initCenterPnl();
        initBottomPnl();
        setVisible(true);
    }

    private void initBottomPnl() {
        JButton start = new JButton("Start");
        start.setFont(defaultFont);
        add(start, BorderLayout.PAGE_END);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkSize()){
                    GameBoard gameBoard = new GameBoard(gameWidth, gameHeight, gameTYPE);
                    Game game = new Game(gameBoard);
                    setVisible(false);
                    game.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            setVisible(true);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(me,
                            "Enter number between 3 and 10!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean checkSize() {
        if (gameWidth == -1)
            widthTxt.setBorder(BorderFactory.createLineBorder(Color.RED,5));
        else
            widthTxt.setBorder(new JTextField().getBorder());
        if (gameHeight == -1)
            heightTxt.setBorder(BorderFactory.createLineBorder(Color.RED,5));
        else
            heightTxt.setBorder(new JTextField().getBorder());
        return gameWidth != -1 && gameHeight != -1;
    }

    private void initCenterPnl() {
        JPanel centerPnl = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel widthLbl = new JLabel("Width");
        widthLbl.setFont(defaultFont);
        widthLbl.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel heightLbl = new JLabel("Height");
        heightLbl.setFont(defaultFont);
        heightLbl.setHorizontalAlignment(SwingConstants.CENTER);

        widthTxt = new JTextField("3");
        heightTxt = new JTextField("3");
        widthTxt.setFont(defaultFont);
        heightTxt.setFont(defaultFont);
        widthTxt.setHorizontalAlignment(SwingConstants.CENTER);
        heightTxt.setHorizontalAlignment(SwingConstants.CENTER);
        heightTxt.getDocument().putProperty("owner","height");
        widthTxt.getDocument().putProperty("owner","width");

        DocumentListener ValidNum = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    int sizeTemp = -1;
                    if (ValidNum(e.getDocument().getText(0,e.getDocument().getLength())))
                        sizeTemp = Integer.parseInt(e.getDocument().getText(0,e.getDocument().getLength()));
                    if (e.getDocument().getProperty("owner").equals("width"))
                        gameWidth = sizeTemp;
                    else
                        gameHeight = sizeTemp;
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            public boolean ValidNum(String str) {
                if (isNumber(str))
                    return isValid(str);
                return false;
            }

            public boolean isNumber(String num) {
                return num.matches("[\\d]+");
            }

            public boolean isValid(String str) {
                int i = Integer.parseInt(str);
                return i>=3 && i<=10;
            }
        };

        widthTxt.getDocument().addDocumentListener(ValidNum);
        heightTxt.getDocument().addDocumentListener(ValidNum);
        centerPnl.add(widthLbl);
        centerPnl.add(heightLbl);
        centerPnl.add(widthTxt);
        centerPnl.add(heightTxt);

        centerPnl.setBorder(new EmptyBorder(50,50,50,50));

        add(centerPnl, BorderLayout.CENTER);

    }

    private void initTopPnl() {
        JPanel topPnl = new JPanel(new FlowLayout());

        JToggleButton gameTypeBtnPvE = new JToggleButton("PvE");
        JToggleButton gameTypeBtnPvP = new JToggleButton("PvP");
        gameTypeBtnPvE.setFont(defaultFont);
        gameTypeBtnPvP.setFont(defaultFont);
        gameTypeBtnPvE.setSelected(true);
        gameTYPE = true;
        topPnl.add(gameTypeBtnPvE);
        topPnl.add(gameTypeBtnPvP);

        ActionListener gameFounder = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("PvE")) {
                    gameTypeBtnPvP.setSelected(!gameTypeBtnPvP.isSelected());
                }
                else gameTypeBtnPvE.setSelected(!gameTypeBtnPvE.isSelected());
                gameTYPE = gameTypeBtnPvE.isSelected();
            }
        };

        gameTypeBtnPvE.addActionListener(gameFounder);
        gameTypeBtnPvP.addActionListener(gameFounder);
        add(topPnl, BorderLayout.PAGE_START);

    }

    public static void main(String[] args) {
        new Main();
    }
}
