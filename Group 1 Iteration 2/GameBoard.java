import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends GameFrame 
{
    private GridSquares [][] gridSquares;	// squares to appear in grid formation
    private final int COLUMNS = 10;
    private final int ROWS = 10;
    private JPanel rightPanel, topPanel, centerPanel, bottomPanel;
    private JLabel round, whoTurn, doThis, tilesLeft;
    private FrameManager frameManager;



    public GameBoard(FrameManager frameManager)
    {
        super();
        this.frameManager = frameManager;
        centerPanel = new JPanel();
        setBounds(100, 100, 2000, 1500);
        getContentPane().setLayout(new BorderLayout());
        makeBoard();
    }

    private void makeBoard()
    {
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(700,1500));
        rightPanel.setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(600,60));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JPanel topLeft = new JPanel();
        topLeft.setPreferredSize(new Dimension(125,20));
        topLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        round = new JLabel("Round #: 1");
        topLeft.add(round);
        topPanel.add(topLeft);

        Dimension top1MinSize = new Dimension(50, 50);
        Dimension top1PrefSize = new Dimension(50, 50);
        Dimension top1MaxSize = new Dimension(Short.MAX_VALUE, 100);
        topPanel.add(new Box.Filler(top1MinSize, top1PrefSize, top1MaxSize));


        JPanel topCenter = new JPanel();
        topCenter.setPreferredSize(new Dimension(200,50));
        whoTurn = new JLabel("Player One's turn");
        doThis = new JLabel("Do this thing this turn");
        topCenter.add(whoTurn);
        topCenter.add(doThis);
        topPanel.add(topCenter);

        Dimension top2MinSize = new Dimension(50, 50);
        Dimension top2PrefSize = new Dimension(50, 50);
        Dimension top2MaxSize = new Dimension(Short.MAX_VALUE, 100);
        topPanel.add(new Box.Filler(top2MinSize, top2PrefSize, top2MaxSize));

        JPanel topRight = new JPanel();
        topRight.setPreferredSize(new Dimension(150,20));
        topRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
        tilesLeft = new JLabel("Tiles remaining: 64");
        topRight.add(tilesLeft);
        topPanel.add(topRight);

        rightPanel.add(topPanel, BorderLayout.NORTH);

        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(600,60));
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        JButton quit = new JButton("Quit");
        quit.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomPanel.add(quit);

        Dimension minSize = new Dimension(250, 50);
        Dimension prefSize = new Dimension(250, 50);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 100);
        bottomPanel.add(new Box.Filler(minSize, prefSize, maxSize));

        JButton options = new JButton("Options");
        options.setAlignmentX(Component.RIGHT_ALIGNMENT);
        bottomPanel.add(options);
        
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel rightCenterPanel = new JPanel();
        rightCenterPanel.setLayout(new BorderLayout());

        JPanel rightCenterTopPanel = new JPanel();
        rightCenterTopPanel.setPreferredSize(new Dimension(600,40));
        rightCenterTopPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        JButton leftRotate = new JButton("Left");
        rightCenterTopPanel.add(leftRotate);

        JLabel rotate = new JLabel("Rotate Tiles");
        rightCenterTopPanel.add(rotate);

        JButton rightRotate = new JButton("Right");
        rightCenterTopPanel.add(rightRotate);

        rightCenterPanel.add(rightCenterTopPanel, BorderLayout.NORTH);



        rightPanel.add(rightCenterPanel, BorderLayout.CENTER);

        getContentPane().add(rightPanel, BorderLayout.EAST);

        centerPanel.setLayout(new GridLayout(ROWS,COLUMNS,5,5));
        addGridSquares();
        getContentPane().add(centerPanel, BorderLayout.CENTER);


        setVisible(false);

    }

    private void addGridSquares()
    {
        gridSquares = new GridSquares [ROWS][COLUMNS];
        for(int i = 0; i < ROWS; ++i){
            for(int j = 0; j < COLUMNS; ++j){
                gridSquares[i][j] = new GridSquares(i,j);
                centerPanel.add(gridSquares[i][j]);
            }
        }
    }
/*
    public static void main(String[] args) {
        FrameManager frameManager = new FrameManager();
        new GameBoard(frameManager);
    }
*/
}