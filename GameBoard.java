import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends GameFrame implements ActionListener
{
    private GridSquares [][] gridSquares;	// squares to appear in grid formation
    private final int COLUMNS = 9;
    private final int ROWS = 9;
    private int topMostDominoRow;
    private int bottomMostDominoRow;
    private int leftMostDominoColumn;
    private int rightMostDominoColumn;
    private int playerNum;
    private int lastRoundTracker;
    private Random rand;
    private JPanel rightPanel, topPanel, centerPanel, bottomPanel;
    private JLabel round, whoTurn, doThis, dominosLeft;
    private JButton rotateTile2, rotateTile4, rotateTile5, rotateTile6, rotateTile8;
	private JButton leftRotate, rightRotate, discardDomino, endTurn;
    private JButton currentTile11, currentTile12, currentTile21, currentTile22, currentTile31, currentTile32, currentTile41, currentTile42;
    private JButton nextRndTile11, nextRndTile12, nextRndTile21, nextRndTile22, nextRndTile31, nextRndTile32, nextRndTile41, nextRndTile42; 
    private FrameManager frameManager;
    private ArrayList<Domino> currentDominos;
    private ArrayList<Domino> nextDominos; 
    private boolean AIboard;
    private transient Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int dRow[] = { -1, 0, 1, 0 };
    static int dCol[] = { 0, 1, 0, -1 };

    public GameBoard(FrameManager frameManager, int playerNum) throws IOException
    {
        super();
        setResizable(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frameManager = frameManager;
        this.playerNum = playerNum;
        this.AIboard = false;
        rand = new Random();
        topMostDominoRow = 4;
        bottomMostDominoRow = 4;
        leftMostDominoColumn = 4;
        rightMostDominoColumn = 4;
        lastRoundTracker = 0;
        //setBounds(100, 100, 2000, 1500);
        getContentPane().setLayout(new BorderLayout());
        //setResizable(true);
        makeBoard();
    }
    
    private void makeBoard() throws IOException
    {
        /***********************************************/
        /*Create panel to the right of the playing area*/
        rightPanel = new JPanel();
        //rightPanel.setPreferredSize(new Dimension(700,1500));
        rightPanel.setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height));
        rightPanel.setLayout(new BorderLayout());

        /****************************************************/
        /* Creating top panel with currentDominos round information*/
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        //topPanel.setPreferredSize(new Dimension(650,60));
        topPanel.setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height * 2/20));
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        
        //Dimension minSize = new Dimension(screenSize.width * 3/8 * 1/15, screenSize.height * 1/20);
        //Dimension prefSize = new Dimension(screenSize.width * 3/8 * 1/15, screenSize.height * 1/20);
        //Dimension maxSize = new Dimension(screenSize.width * 3/8 * 1/15, screenSize.height * 1/20);

        //topPanel.add(new Box.Filler(minSize, prefSize, maxSize), BorderLayout.NORTH);
         
        JPanel topNorth = new JPanel();
        topNorth.setLayout(new FlowLayout());
        topNorth.setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height * 1/20));

        JPanel topLeft = new JPanel();
        //topLeft.setPreferredSize(new Dimension(125,20));
        //topLeft.setPreferredSize(new Dimension(screenSize.width * 3/8 * 2/9, screenSize.height * 1/9));
        //topLeft.setLayout(new BoxLayout(topLeft, BoxLayout.Y_AXIS));
        round = new JLabel("Round #: 0");
        //round.setHorizontalAlignment(SwingConstants.LEFT);
        topLeft.add(round);
        //round.setAlignmentX(Component.LEFT_ALIGNMENT);
        topNorth.add(topLeft);

        Dimension minSize = new Dimension(screenSize.width * 3/8 * 1/10, screenSize.height * 1/20);
        Dimension prefSize = new Dimension(screenSize.width * 3/8 * 1/10, screenSize.height * 1/20);
        Dimension maxSize = new Dimension(screenSize.width * 3/8 * 1/10, screenSize.height * 1/20);

        topNorth.add(new Box.Filler(minSize, prefSize, maxSize));

        JPanel topRight = new JPanel();
 
        dominosLeft = new JLabel("Dominos remaining: 0");
        topRight.add(dominosLeft);
        topNorth.add(topRight);

        topPanel.add(topNorth, BorderLayout.NORTH);

 

        JPanel topCenter = new JPanel();
        topCenter.setLayout(new FlowLayout());
        topCenter.setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height * 1/20));
        whoTurn = new JLabel("Player One's turn");
        doThis = new JLabel("Do this thing this turn");
        topCenter.add(whoTurn);
        topCenter.add(doThis);
        topPanel.add(topCenter, BorderLayout.SOUTH);


        rightPanel.add(topPanel, BorderLayout.NORTH);

        /*****************************************************************/
        /* Creating bottom panel to hold the 'End Turn' and 'Quit' Buttons*/
        bottomPanel = new JPanel();
        setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height * 1/20));

        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));

        discardDomino = new JButton("Discard domino");
        discardDomino.addActionListener(e -> discardDomino(frameManager.getCurrentDomino()));
        bottomPanel.add(discardDomino);


        minSize = new Dimension(screenSize.width * 3/8 * 2/5, screenSize.height * 1/20);
        prefSize = new Dimension(screenSize.width * 3/8 * 2/5, screenSize.height * 1/20);
        maxSize = new Dimension(screenSize.width * 3/8 * 2/5, screenSize.height * 1/20);

        bottomPanel.add(new Box.Filler(minSize, prefSize, maxSize));

        endTurn = new JButton("End Turn");
        //endTurn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        endTurn.addActionListener(this);
        bottomPanel.add(endTurn);
        
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        /*******************************************************/
        /*Creating panel to hold the buttons to rotate the tile*/
        JPanel rightCenterPanel = new JPanel();
        rightCenterPanel.setLayout(new BorderLayout());

        JPanel rightCenterTopPanel = new JPanel();
        setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height * 1/20));
        rightCenterTopPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));



        leftRotate = new JButton("Left");
        leftRotate.addActionListener(e-> rotateLeft(frameManager.getCurrentDomino()));
        rightCenterTopPanel.add(leftRotate);

        JLabel rotate = new JLabel("Rotate Tiles");
        rightCenterTopPanel.add(rotate);

        rightRotate = new JButton("Right");
        rightRotate.addActionListener(e-> rotateRight(frameManager.getCurrentDomino()));
        rightCenterTopPanel.add(rightRotate);

        rightCenterPanel.add(rightCenterTopPanel, BorderLayout.NORTH);

        /***********************************************************************/
        /*Creating new panel with border layout to be nested in center position*/
        JPanel rightCenterCenterPanel = new JPanel();
        rightCenterCenterPanel.setLayout(new BorderLayout());
        
        /*********************************************************************/
        /**************Creating panel to show rotating tiles******************/
        JPanel rightCenterCenterTopPanel = new JPanel();
        rightCenterCenterTopPanel.setLayout(new BorderLayout());
        
        JPanel rightCenterCenterTopCenterPanel = new JPanel();
        rightCenterCenterTopCenterPanel.setPreferredSize(new Dimension(screenSize.width *1/3 * 1/2, screenSize.height * 5/20));
        rightCenterCenterTopCenterPanel.setLayout(new GridLayout(3,3,2,2));
        JButton rotateTile1 = new JButton();
        rotateTile1.setPreferredSize(new Dimension(50,50));
        rotateTile1.setMaximumSize(new Dimension(50,50));
        rotateTile1.setBackground(getBackgroundColour());
        rotateTile1.setOpaque(true);
        rotateTile1.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile1);

        rotateTile2 = new JButton();
        rotateTile2.setPreferredSize(new Dimension(50,50));
        rotateTile2.setMaximumSize(new Dimension(50,50));
        rotateTile2.setBackground(Color.WHITE);
        rotateTile2.setOpaque(true);
        rotateTile2.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile2);

        JButton rotateTile3 = new JButton();
        rotateTile3.setPreferredSize(new Dimension(50,50));
        rotateTile3.setMaximumSize(new Dimension(50,50));
        rotateTile3.setBackground(getBackgroundColour());
        rotateTile3.setOpaque(true);
        rotateTile3.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile3);

        rotateTile4 = new JButton();
        rotateTile4.setPreferredSize(new Dimension(50,50));
        rotateTile4.setMaximumSize(new Dimension(50,50));
        rotateTile4.setBackground(Color.WHITE);
        rotateTile4.setOpaque(true);
        rotateTile4.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile4);

        rotateTile5 = new JButton();
        rotateTile5.setPreferredSize(new Dimension(50,50));
        rotateTile5.setMaximumSize(new Dimension(50,50));
        rotateTile5.setBackground(Color.WHITE);
        rotateTile5.setOpaque(true);
        rotateTile5.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile5);

        rotateTile6 = new JButton();
        rotateTile6.setPreferredSize(new Dimension(50,50));
        rotateTile6.setMaximumSize(new Dimension(50,50));
        rotateTile6.setBackground(Color.WHITE);
        rotateTile6.setOpaque(true);
        rotateTile6.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile6);

        JButton rotateTile7 = new JButton();
        rotateTile7.setPreferredSize(new Dimension(50,50));
        rotateTile7.setMaximumSize(new Dimension(50,50));
        rotateTile7.setBackground(getBackgroundColour());
        rotateTile7.setOpaque(true);
        rotateTile7.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile7);

        rotateTile8 = new JButton();
        rotateTile8.setPreferredSize(new Dimension(50,50));
        rotateTile8.setMaximumSize(new Dimension(50,50));
        rotateTile8.setBackground(Color.WHITE);
        rotateTile8.setOpaque(true);
        rotateTile8.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile8);

        JButton rotateTile9 = new JButton();
        rotateTile9.setPreferredSize(new Dimension(50,50));
        rotateTile9.setMaximumSize(new Dimension(50,50));
        rotateTile9.setBackground(getBackgroundColour());
        rotateTile9.setOpaque(true);
        rotateTile9.setBorderPainted(false);
        rightCenterCenterTopCenterPanel.add(rotateTile9);

        JPanel eastFiller = new JPanel();
        JPanel westFiller = new JPanel();
        Dimension topCCMinSize = new Dimension(screenSize.width * 1/4 * 1/3, screenSize.height * 5/20);
        Dimension topCCPrefSize = new Dimension(screenSize.width * 1/4 * 1/3, screenSize.height * 5/20);
        Dimension topCCMaxSize = new Dimension(screenSize.width * 1/4 * 1/3, screenSize.height * 5/20);//150,200
        eastFiller.add(new Box.Filler(topCCMinSize, topCCPrefSize, topCCMaxSize));
        westFiller.add(new Box.Filler(topCCMinSize, topCCPrefSize, topCCMaxSize));
        rightCenterCenterTopPanel.add(eastFiller, BorderLayout.EAST);
        rightCenterCenterTopPanel.add(westFiller, BorderLayout.WEST);
        
        rightCenterCenterTopPanel.add(rightCenterCenterTopCenterPanel, BorderLayout.CENTER);

        /*********************************************************************/
        /**Create panel to hold the currentDominos round tiles and next round tiles**/
        JPanel rightCenterCenterTopBottomPanel = new JPanel();
        rightCenterCenterTopBottomPanel.setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height * 1/20));
        rightCenterCenterTopBottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        JLabel currentRndTiles = new JLabel("Current Round Dominoes");
        rightCenterCenterTopBottomPanel.add(currentRndTiles);
        Dimension label1MinSize = new Dimension(screenSize.width * 3/8 * 3/10, screenSize.height * 1/20);
        Dimension label1PrefSize = new Dimension(screenSize.width * 3/8 * 3/10, screenSize.height * 1/20);
        Dimension label1MaxSize = new Dimension(screenSize.width * 3/8 * 3/10, screenSize.height * 1/20);
        rightCenterCenterTopBottomPanel.add(new Box.Filler(label1MinSize, label1PrefSize, label1MaxSize));
        JLabel nextRndTiles = new JLabel("Next Round Dominoes");
        rightCenterCenterTopBottomPanel.add(nextRndTiles);

        rightCenterCenterTopPanel.add(rightCenterCenterTopBottomPanel, BorderLayout.SOUTH);
        rightCenterCenterPanel.add(rightCenterCenterTopPanel, BorderLayout.NORTH);

        JPanel rightCenterCenterLeftPanel = new JPanel();
        rightCenterCenterLeftPanel.setPreferredSize(new Dimension(30,screenSize.height * 11/20));

        Dimension playerIconMinSize = new Dimension(5, 15);//20
        Dimension playerIconPrefSize = new Dimension(5, 15);
        Dimension playerIconMaxSize = new Dimension(5, 15);
        rightCenterCenterLeftPanel.add(new Box.Filler(playerIconMinSize, playerIconPrefSize, playerIconMaxSize));

        Dimension playerIconMinSize1 = new Dimension(5, 40);
        Dimension playerIconPrefSize1 = new Dimension(5, 40);
        Dimension playerIconMaxSize1 = new Dimension(5, 40);
        rightCenterCenterLeftPanel.add(new Box.Filler(playerIconMinSize1, playerIconPrefSize1, playerIconMaxSize1));

        rightCenterCenterLeftPanel.add(new Box.Filler(playerIconMinSize1, playerIconPrefSize1, playerIconMaxSize1));

        rightCenterCenterLeftPanel.add(new Box.Filler(playerIconMinSize1, playerIconPrefSize1, playerIconMaxSize1));

        rightCenterCenterPanel.add(rightCenterCenterLeftPanel, BorderLayout.WEST);

        JPanel rightCenterCenterRightPanel = new JPanel();
        rightCenterCenterRightPanel.setPreferredSize(new Dimension(30,screenSize.height * 11/20));

        rightCenterCenterRightPanel.add(new Box.Filler(playerIconMinSize1, playerIconPrefSize1, playerIconMaxSize1));

        rightCenterCenterRightPanel.add(new Box.Filler(playerIconMinSize1, playerIconPrefSize1, playerIconMaxSize1));

        rightCenterCenterRightPanel.add(new Box.Filler(playerIconMinSize1, playerIconPrefSize1, playerIconMaxSize1));

        rightCenterCenterPanel.add(rightCenterCenterRightPanel, BorderLayout.EAST);

        JPanel rightCenterCenterCenterPanel = new JPanel();
        rightCenterCenterCenterPanel.setLayout(new GridLayout(4,5,2,2));
        rightCenterCenterCenterPanel.setPreferredSize(new Dimension(screenSize.width * 3/8, screenSize.height * 11/20));

        currentTile11 = new JButton();
        currentTile11.setPreferredSize(new Dimension(50,50));
        currentTile11.setMaximumSize(new Dimension(50,50));
        currentTile11.setBackground(Color.WHITE);
        currentTile11.setOpaque(true);
        currentTile11.setBorderPainted(false);
        currentTile11.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile11);

        currentTile12 = new JButton();
        currentTile12.setPreferredSize(new Dimension(50,50));
        currentTile12.setBackground(Color.WHITE);
        currentTile12.setOpaque(true);
        currentTile12.setBorderPainted(false);
        currentTile12.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile12);

        Dimension tile1MinSize = new Dimension(50, 50);//200
        Dimension tile1PrefSize = new Dimension(50, 50);//200
        Dimension tile1MaxSize = new Dimension(50, 50);//Short.MAX_VALUE
        rightCenterCenterCenterPanel.add(new Box.Filler(tile1MinSize, tile1PrefSize, tile1MaxSize));

        nextRndTile11 = new JButton();
        nextRndTile11.setPreferredSize(new Dimension(50,50));
        nextRndTile11.setBackground(Color.WHITE);
        nextRndTile11.setOpaque(true);
        nextRndTile11.setBorderPainted(false);
        nextRndTile11.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile11);

        nextRndTile12 = new JButton();
        nextRndTile12.setPreferredSize(new Dimension(50,50));
        nextRndTile12.setBackground(Color.WHITE);
        nextRndTile12.setOpaque(true);
        nextRndTile12.setBorderPainted(false);
        nextRndTile12.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile12);

        currentTile21 = new JButton();
        currentTile21.setPreferredSize(new Dimension(50,50));
        currentTile21.setBackground(Color.WHITE);
        currentTile21.setOpaque(true);
        currentTile21.setBorderPainted(false);
        currentTile21.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile21);

        currentTile22 = new JButton();
        currentTile22.setPreferredSize(new Dimension(50,50));
        currentTile22.setBackground(Color.WHITE);
        currentTile22.setOpaque(true);
        currentTile22.setBorderPainted(false);
        currentTile22.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile22);

        rightCenterCenterCenterPanel.add(new Box.Filler(tile1MinSize, tile1PrefSize, tile1MaxSize));

        nextRndTile21 = new JButton();
        nextRndTile21.setPreferredSize(new Dimension(50,50));
        nextRndTile21.setBackground(Color.WHITE);
        nextRndTile21.setOpaque(true);
        nextRndTile21.setBorderPainted(false);
        nextRndTile21.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile21);

        nextRndTile22 = new JButton();
        nextRndTile22.setPreferredSize(new Dimension(50,50));
        nextRndTile22.setBackground(Color.WHITE);
        nextRndTile22.setOpaque(true);
        nextRndTile22.setBorderPainted(false);
        nextRndTile22.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile22);

        currentTile31 = new JButton();
        currentTile31.setPreferredSize(new Dimension(50,50));
        currentTile31.setBackground(Color.WHITE);
        currentTile31.setOpaque(true);
        currentTile31.setBorderPainted(false);
        currentTile31.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile31);

        currentTile32 = new JButton();
        currentTile32.setPreferredSize(new Dimension(50,50));
        currentTile32.setBackground(Color.WHITE);
        currentTile32.setOpaque(true);
        currentTile32.setBorderPainted(false);
        currentTile32.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile32);

        rightCenterCenterCenterPanel.add(new Box.Filler(tile1MinSize, tile1PrefSize, tile1MaxSize));

        nextRndTile31 = new JButton();
        nextRndTile31.setPreferredSize(new Dimension(50,50));
        nextRndTile31.setBackground(Color.WHITE);
        nextRndTile31.setOpaque(true);
        nextRndTile31.setBorderPainted(false);
        nextRndTile31.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile31);

        nextRndTile32 = new JButton();
        nextRndTile32.setPreferredSize(new Dimension(50,50));
        nextRndTile32.setBackground(Color.WHITE);
        nextRndTile32.setOpaque(true);
        nextRndTile32.setBorderPainted(false);
        nextRndTile32.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile32);

        currentTile41 = new JButton();
        currentTile41.setPreferredSize(new Dimension(50,50));
        currentTile41.setBackground(Color.WHITE);
        currentTile41.setOpaque(true);
        currentTile41.setBorderPainted(false);
        currentTile41.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile41);

        currentTile42 = new JButton();
        currentTile42.setPreferredSize(new Dimension(50,50));
        currentTile42.setBackground(Color.WHITE);
        currentTile42.setOpaque(true);
        currentTile42.setBorderPainted(false);
        currentTile42.addActionListener(this);
        rightCenterCenterCenterPanel.add(currentTile42);

        rightCenterCenterCenterPanel.add(new Box.Filler(tile1MinSize, tile1PrefSize, tile1MaxSize));

        nextRndTile41 = new JButton();
        nextRndTile41.setPreferredSize(new Dimension(50,50));
        nextRndTile41.setBackground(Color.WHITE);
        nextRndTile41.setOpaque(true);
        nextRndTile41.setBorderPainted(false);
        nextRndTile41.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile41);

        nextRndTile42 = new JButton();
        nextRndTile42.setPreferredSize(new Dimension(50,50));
        nextRndTile42.setBackground(Color.WHITE);
        nextRndTile42.setOpaque(true);
        nextRndTile42.setBorderPainted(false);
        nextRndTile42.addActionListener(this);
        rightCenterCenterCenterPanel.add(nextRndTile42);

        rightCenterCenterPanel.add(rightCenterCenterCenterPanel, BorderLayout.CENTER);
        rightCenterPanel.add(rightCenterCenterPanel, BorderLayout.CENTER);
        
        rightPanel.add(rightCenterPanel, BorderLayout.CENTER);

        getContentPane().add(rightPanel, BorderLayout.EAST);

        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(screenSize.width * 5/8, screenSize.height));
        centerPanel.setLayout(new GridLayout(ROWS,COLUMNS,2,2));
        addGridSquares();
        getContentPane().add(centerPanel, BorderLayout.WEST);

        Image startingTile = ImageIO.read(getClass().getResource("/images/StartingTile.png"));
        startingTile = startingTile.getScaledInstance(screenSize.width * 5/8 * 1/9, screenSize.height * 1/9, java.awt.Image.SCALE_SMOOTH); //185, 110
        gridSquares[4][4].setIcon(new ImageIcon(startingTile));
        gridSquares[4][4].setBackground(Color.GRAY);

        makeMenuBar();
        pack();
        setVisible(false);

    }
	
    private void makeMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e->save());
        fileMenu.add(saveItem);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e->{
            try {
                quit();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        fileMenu.add(quitItem);

        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);

        JMenuItem viewPlayer1 = new JMenuItem("View Player 1");
        viewPlayer1.addActionListener(e -> viewPlayer1());
        viewMenu.add(viewPlayer1);

        JMenuItem viewPlayer2 = new JMenuItem("View Player 2");
        viewPlayer2.addActionListener(e -> viewPlayer2());
        viewMenu.add(viewPlayer2);

        JMenuItem viewPlayer3 = new JMenuItem("View Player 3");
        viewPlayer3.addActionListener(e -> viewPlayer3());
        viewMenu.add(viewPlayer3);

        JMenuItem viewPlayer4 = new JMenuItem("View Player 4");
        viewPlayer4.addActionListener(e -> viewPlayer4());
        viewMenu.add(viewPlayer4);
    }
    
    public void actionPerformed(ActionEvent e)
    {	
		Domino current;
		Domino next;

        if (frameManager.getRoundStatus().equals("starting round") && frameManager.currentDominosAvailable() && !(frameManager.getPlayerTookTurn(frameManager.getPlayerNumber(this)))){
		    if ((e.getSource() == currentTile11) || (e.getSource() == currentTile12)){
			    current = currentDominos.get(0);
                frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                frameManager.setCurrentDomino1Invisible();
                setDoThis("Press 'End Turn' to end your turn.");
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                enableEndTurn();
		    }

		    else if ((e.getSource() == currentTile21) || (e.getSource() == currentTile22)){
			    current = currentDominos.get(1);
                frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                frameManager.setCurrentDomino2Invisible(); 
                setDoThis("Press 'End Turn' to end your turn.");
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                enableEndTurn();
		    }

		    else if ((e.getSource() == currentTile31) || (e.getSource() == currentTile32)){
			    current = currentDominos.get(2);
                frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                frameManager.setCurrentDomino3Invisible();
                setDoThis("Press 'End Turn' to end your turn.");
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                enableEndTurn();
		    }

		    else if ((e.getSource() == currentTile41) || (e.getSource() == currentTile42)){
			    current = currentDominos.get(3);
                frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                frameManager.setCurrentDomino4Invisible();
                setDoThis("Press 'End Turn' to end your turn.");
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                enableEndTurn();
		    }
        }

        else if (frameManager.getRoundStatus().equals("starting round") && frameManager.currentDominosAvailable() && frameManager.getPlayerTookTurn(frameManager.getPlayerNumber(this))){
            if (e.getSource() == endTurn){
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), false);
                setDoThis("Select a domino from current round dominoes");
                disableEndTurn();   
                frameManager.nextPlayersTurn();
            }
        }

        else if (frameManager.getRoundStatus().equals("starting round") && !(frameManager.currentDominosAvailable())){

            if (e.getSource() == endTurn){
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), false);
                disableEndTurn();
                frameManager.setRoundStatus("place domino");   //place dominos
                frameManager.nextPlayersTurn();
            }
        }

        else if (frameManager.lastRoundTracker() == 4)
        {   
            if (e.getSource() == endTurn)
            {
                frameManager.showResultsFrame();
            }
        }         

		else if (frameManager.getRoundStatus().equals("select domino") && !(frameManager.getPlayerTookTurn(frameManager.getPlayerNumber(this)))){

		    if ((e.getSource() == nextRndTile11) || (e.getSource() == nextRndTile12)){

			    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to select this domino for the next round?", "Confirm", JOptionPane.YES_NO_OPTION); 

                if(confirm == JOptionPane.YES_OPTION){

            	    next = nextDominos.get(0);
                    frameManager.addDominoToPlayer(next, frameManager.getPlayerNumber(this)); 
            	    frameManager.setNextDomino1Invisible();
                    frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                    setDoThis("Press 'End Turn' to end your turn.");
                    enableEndTurn();
                }
		    }
		
		    else if ((e.getSource() == nextRndTile21) || (e.getSource() == nextRndTile22)){

			    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to select this domino for the next round?", "Confirm", JOptionPane.YES_NO_OPTION); 

                if(confirm == JOptionPane.YES_OPTION){
            	    next = nextDominos.get(1);
            	    frameManager.addDominoToPlayer(next, frameManager.getPlayerNumber(this)); 
            	    frameManager.setNextDomino2Invisible();
                    frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                    setDoThis("Press 'End Turn' to end your turn.");
                    enableEndTurn();
                }
		    }
		
		    else if ((e.getSource() == nextRndTile31) || (e.getSource() == nextRndTile32)){

			    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to select this domino for the next round?", "Confirm", JOptionPane.YES_NO_OPTION); 

                if(confirm == JOptionPane.YES_OPTION){

            	    next = nextDominos.get(2); 
            	    frameManager.addDominoToPlayer(next, frameManager.getPlayerNumber(this)); 
            	    frameManager.setNextDomino3Invisible();
                    frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                    setDoThis("Press 'End Turn' to end your turn.");
                    enableEndTurn();
                }
		    }   
		
		    else if ((e.getSource() == nextRndTile41) || (e.getSource() == nextRndTile42)){

			    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to select this domino for the next round?", "Confirm", JOptionPane.YES_NO_OPTION); 

                if(confirm == JOptionPane.YES_OPTION){

            	    next = nextDominos.get(3); 
            	    frameManager.addDominoToPlayer(next, frameManager.getPlayerNumber(this)); 
            	    frameManager.setNextDomino4Invisible();
                    frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                    setDoThis("Press 'End Turn' to end your turn.");
                    enableEndTurn();
                }
		    }
        }
		
        else if (frameManager.getRoundStatus().equals("select domino") && (frameManager.getPlayerTookTurn(frameManager.getPlayerNumber(this)))){

            if (e.getSource() == endTurn){

                frameManager.setRoundStatus("place domino");
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), false);
                disableEndTurn();
                frameManager.nextPlayersTurn();
            }
        }

        else if (frameManager.getRoundStatus().equals("place domino")){

            for(int i = 0; i < ROWS; ++i){

                for(int j = 0; j < COLUMNS; ++j){

                    if (e.getSource() == gridSquares[i][j]){

                        if (gridSquares[i][j].getBackground() == Color.WHITE){

                            if (rotatingTileOnRight(i, j)){

                                if (verifyAdjacentSquare(i,j+1) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                                    
                                    placeTile(i, j);
                                    frameManager.setRoundStatus("select domino");
                                    frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
                                    frameManager.getListOfPlayers().get(playerNum-1).setPoints(calculateScore());
                                }

                                else{

                                    JOptionPane.showMessageDialog(null, "Please select a valid square for your domino!", null, JOptionPane.PLAIN_MESSAGE);
                                }
                            }

                            else if (rotatingTileOnLeft(i, j)){

                                if (verifyAdjacentSquare(i,j-1) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){

                                    placeTile(i,j);
                                    frameManager.setRoundStatus("select domino");
                                    frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
                                    frameManager.getListOfPlayers().get(playerNum-1).setPoints(calculateScore());
                                }

                                else{
                                    JOptionPane.showMessageDialog(null, "Please select a valid square for your domino!", null, JOptionPane.PLAIN_MESSAGE);
                                }
                            }
                            else if (rotatingTileBelow(i, j)){

                                if (verifyAdjacentSquare(i+1,j) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){

                                    placeTile(i,j);
                                    frameManager.setRoundStatus("select domino");
                                    frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
                                    frameManager.getListOfPlayers().get(playerNum-1).setPoints(calculateScore());
                                }

                                else
                                {
                                    JOptionPane.showMessageDialog(null, "Please select a valid square for your domino!", null, JOptionPane.PLAIN_MESSAGE);
                                }
                            }
                            else if (rotatingTileOnTop(i, j))
                            {
                                if (verifyAdjacentSquare(i-1,j) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){

                                    placeTile(i,j);
                                    frameManager.setRoundStatus("select domino");
                                    frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
                                    frameManager.getListOfPlayers().get(playerNum-1).setPoints(calculateScore());
                                }

                                else{

                                    JOptionPane.showMessageDialog(null, "Please select a valid square for your domino!", null, JOptionPane.PLAIN_MESSAGE);
                                }
                            }
                        }

                        else if (rotateTile5.getBackground() != Color.WHITE){

                            JOptionPane.showMessageDialog(null, "Square already taken! Please select a different square.", null, JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }

        else{

            if ( e.getSource() == endTurn){

                frameManager.nextPlayersTurn();
            }
        }
    }



    private boolean rotatingTileOnRight(int i, int j)
    {
        if((rotateTile6.getBackground() != Color.WHITE) && ((j+1) < COLUMNS) && rotateTile5.getBackground() != Color.WHITE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean rotatingTileOnLeft(int i, int j)
    {
        if((rotateTile4.getBackground() != Color.WHITE && ((j-1) >= 0) && rotateTile5.getBackground() != Color.WHITE))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean rotatingTileBelow(int i, int j)
    {
        if((rotateTile8.getBackground() != Color.WHITE && ((i+1) < ROWS) && rotateTile5.getBackground() != Color.WHITE))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean rotatingTileOnTop(int i, int j)
    {
        if((rotateTile2.getBackground() != Color.WHITE && ((i-1) >= 0) && rotateTile5.getBackground() != Color.WHITE))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean verifyAdjacentSquare(int i, int j)
    {
        if (gridSquares[i][j].getBackground() == Color.WHITE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean verifyTerrainRule(int i, int j)
    {
        if(rotatingTileOnRight(i, j))
        {
            if((j-1)>=0)
            {
                if ((gridSquares[i][j-1].getBackground() == rotateTile5.getBackground()) || (gridSquares[i][j-1].getBackground() == Color.GRAY))
                {
                    return true;
                }
            }

            if((i-1)>=0)
            {   

                if ((gridSquares[i-1][j].getBackground() == rotateTile5.getBackground()) || (gridSquares[i-1][j].getBackground() == Color.GRAY) || (gridSquares[i-1][j+1].getBackground() == Color.GRAY) || (gridSquares[i-1][j+1].getBackground() == rotateTile6.getBackground()))
                {
                    return true;
                }
            } 

            if((i+1)<ROWS)
            {
                if ((gridSquares[i+1][j].getBackground() == rotateTile5.getBackground()) || (gridSquares[i+1][j].getBackground() == Color.GRAY) || (gridSquares[i+1][j+1].getBackground() == Color.GRAY) || (gridSquares[i+1][j+1].getBackground() == rotateTile6.getBackground()))
                {
                    return true;
                }
            }

            if((j+2)<COLUMNS)
            {
                if ((gridSquares[i][j+2].getBackground() == Color.GRAY) || (gridSquares[i][j+2].getBackground() == rotateTile6.getBackground()))
                {
                    return true;
                }
            }
            
            return false;
            
        }

        if(rotatingTileOnLeft(i, j))
        {
             if((j+1)<COLUMNS)
            {
                if ((gridSquares[i][j+1].getBackground() == rotateTile5.getBackground()) || (gridSquares[i][j+1].getBackground() == Color.GRAY))
                {
                    return true;
                }
            }

            if((i-1)>=0)
            {   

                if ((gridSquares[i-1][j].getBackground() == rotateTile5.getBackground()) || (gridSquares[i-1][j].getBackground() == Color.GRAY) || (gridSquares[i-1][j-1].getBackground() == Color.GRAY) || (gridSquares[i-1][j-1].getBackground() == rotateTile2.getBackground()))
                {
                    return true;
                }
            } 

            if((i+1)<ROWS)
            {
                if ((gridSquares[i+1][j].getBackground() == rotateTile5.getBackground()) || (gridSquares[i+1][j].getBackground() == Color.GRAY) || (gridSquares[i+1][j-1].getBackground() == Color.GRAY) || (gridSquares[i+1][j-1].getBackground() == rotateTile2.getBackground()))
                {
                    return true;
                }
            }

            if((j-2) >= 0)
            {
                if ((gridSquares[i][j-2].getBackground() == Color.GRAY) || (gridSquares[i][j-2].getBackground() == rotateTile2.getBackground()))
                {
                    return true;
                }
            }
            
            return false;
        }

        if(rotatingTileBelow(i, j))
        {
            if((i-1)>=0)
            {
                if ((gridSquares[i-1][j].getBackground() == rotateTile5.getBackground()) || (gridSquares[i-1][j].getBackground() == Color.GRAY))
                {
                    return true;
                }
            }

            if((j-1)>=0)
            {   

                if ((gridSquares[i][j-1].getBackground() == rotateTile5.getBackground()) || (gridSquares[i][j-1].getBackground() == Color.GRAY) || (gridSquares[i+1][j-1].getBackground() == Color.GRAY) || (gridSquares[i+1][j-1].getBackground() == rotateTile8.getBackground()))
                {
                    return true;
                }
            } 

            if((j+1)<COLUMNS)
            {
                if ((gridSquares[i][j+1].getBackground() == rotateTile5.getBackground()) || (gridSquares[i][j+1].getBackground() == Color.GRAY) || (gridSquares[i+1][j+1].getBackground() == Color.GRAY) || (gridSquares[i+1][j+1].getBackground() == rotateTile8.getBackground()))
                {
                    return true;
                }
            }

            if((i+2)<ROWS)
            {
                if ((gridSquares[i+2][j].getBackground() == Color.GRAY) || (gridSquares[i+2][j].getBackground() == rotateTile8.getBackground()))
                {
                    return true;
                }
            }
            
            return false;
        }

        if(rotatingTileOnTop(i, j))
        {
            if((i+1)<ROWS)
            {
                if ((gridSquares[i+1][j].getBackground() == rotateTile5.getBackground()) || (gridSquares[i+1][j].getBackground() == Color.GRAY))
                {
                    return true;
                }
            }

            if((j-1)>=0)
            {   

                if ((gridSquares[i][j-1].getBackground() == rotateTile5.getBackground()) || (gridSquares[i][j-1].getBackground() == Color.GRAY) || (gridSquares[i-1][j-1].getBackground() == Color.GRAY) || (gridSquares[i-1][j-1].getBackground() == rotateTile2.getBackground()))
                {
                    return true;
                }
            } 

            if((j+1)<COLUMNS)
            {
                if ((gridSquares[i][j+1].getBackground() == rotateTile5.getBackground()) || (gridSquares[i][j+1].getBackground() == Color.GRAY) || (gridSquares[i-1][j+1].getBackground() == Color.GRAY) || (gridSquares[i-1][j+1].getBackground() == rotateTile2.getBackground()))
                {
                    return true;
                }
            }

            if((i-2)>=0)
            {
                if ((gridSquares[i-2][j].getBackground() == Color.GRAY) || (gridSquares[i-2][j].getBackground() == rotateTile2.getBackground()))
                {
                    return true;
                }
            }
            
            return false;
        }

        return false;
    }

     
    private boolean verifyDimensions(int i, int j)
    {

        if(rotatingTileOnRight(i, j))
        {
            if (((leftMostDominoColumn <= j) && (j+1 <= rightMostDominoColumn)) && ((topMostDominoRow <= i) && (i <= bottomMostDominoRow)))
            {
                return true;
            }

            if (((j+1) > rightMostDominoColumn)  && ((topMostDominoRow <= i) && (i <= bottomMostDominoRow))) //1
            {
                if(((j+1) - leftMostDominoColumn) < 5)
                {   
                    
                    rightMostDominoColumn = j+1;
                    return true;
                }
            }

            if ((j < leftMostDominoColumn) && ((topMostDominoRow <= i) && (i <= bottomMostDominoRow))) //2
            {
                if((rightMostDominoColumn - j) < 5)
                {
                    
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            if (((leftMostDominoColumn <= j) && ((j+1) <= rightMostDominoColumn)) && (i < topMostDominoRow)) //3
            {
                if((bottomMostDominoRow - i) < 5)
                {
                    
                    topMostDominoRow = i;
                    return true;
                }
            }

            if (((leftMostDominoColumn <= j) && ((j+1) <= rightMostDominoColumn)) && (i > bottomMostDominoRow)) //4
            {
                if((i - topMostDominoRow) < 5)
                {
                    
                    bottomMostDominoRow = i;
                    return true;
                }
            }

            if (((j+1) > rightMostDominoColumn) && (i > bottomMostDominoRow)) //5
            {
                if((((j+1) - leftMostDominoColumn) < 5) && ((i - topMostDominoRow) < 5))
                {
                    
                    bottomMostDominoRow = i;
                    rightMostDominoColumn = j+1;
                    return true;
                }
            }
            
            if (((j+1) > rightMostDominoColumn) && (i < topMostDominoRow)) //6
            {
                if((((j+1) - leftMostDominoColumn) < 5) && ((bottomMostDominoRow - i) < 5))
                {
                    
                    topMostDominoRow = i;
                    rightMostDominoColumn = j+1;
                    return true;
                }
            }

            if (((j) < leftMostDominoColumn) && (i > bottomMostDominoRow)) //7
            {
                if(((rightMostDominoColumn - j) < 5) && ((i - topMostDominoRow) < 5))
                {
                    
                    bottomMostDominoRow = i;
                    leftMostDominoColumn = j;
                    return true;
                }
            }
            
            if (((j) < leftMostDominoColumn) && (i < topMostDominoRow)) //8
            {
                if(((rightMostDominoColumn - j) < 5) && ((bottomMostDominoRow - i) < 5))
                {
                    
                    topMostDominoRow = i;
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            return false;
            
        }

        if(rotatingTileOnLeft(i, j))
        {
           if (((leftMostDominoColumn <= j - 1) && (j <= rightMostDominoColumn)) && ((topMostDominoRow <= i) && (i <= bottomMostDominoRow)))
            {
                return true;
            }
            
            if (((j) > rightMostDominoColumn)  && ((topMostDominoRow <= i) && (i <= bottomMostDominoRow))) //1
            {
                if(((j) - leftMostDominoColumn) < 5)
                {   
                    
                    rightMostDominoColumn = j;
                    return true;
                }
            }   

            if ((j-1 < leftMostDominoColumn) && ((topMostDominoRow <= i) && (i <= bottomMostDominoRow))) //2
            {
                if((rightMostDominoColumn - (j-1)) < 5)
                {
                    
                    leftMostDominoColumn = j-1;
                    return true;
                }
            }

            if (((leftMostDominoColumn <= j-1) && ((j) <= rightMostDominoColumn)) && (i < topMostDominoRow)) //3
            {
                if((bottomMostDominoRow - i) < 5)
                {
                    
                    topMostDominoRow = i;
                    return true;
                }
            }

            if (((leftMostDominoColumn <= j-1) && ((j) <= rightMostDominoColumn)) && (i > bottomMostDominoRow)) //4
            {
                if((i - topMostDominoRow) < 5)
                {
                    
                    bottomMostDominoRow = i;
                    return true;
                }
            }

            if (((j) > rightMostDominoColumn) && (i > bottomMostDominoRow)) //5
            {
                if((((j) - leftMostDominoColumn) < 5) && ((i - topMostDominoRow) < 5))
                {
                    
                    bottomMostDominoRow = i;
                    rightMostDominoColumn = j;
                    return true;
                }
            }
            
            if (((j) > rightMostDominoColumn) && (i < topMostDominoRow)) //6
            {
                if((((j) - leftMostDominoColumn) < 5) && ((bottomMostDominoRow - i) < 5))
                {
                    
                    topMostDominoRow = i;
                    rightMostDominoColumn = j;
                    return true;
                }
            }

            if (((j-1) < leftMostDominoColumn) && (i > bottomMostDominoRow)) //7
            {
                if(((rightMostDominoColumn - (j-1)) < 5) && ((i - topMostDominoRow) < 5))
                {
                    
                    bottomMostDominoRow = i;
                    leftMostDominoColumn = j-1;
                    return true;
                }
            }
            
            if (((j-1) < leftMostDominoColumn) && (i < topMostDominoRow)) //8
            {
                if(((rightMostDominoColumn - (j-1)) < 5) && ((bottomMostDominoRow - i) < 5))
                {
                    
                    topMostDominoRow = i;
                    leftMostDominoColumn = j-1;
                    return true;
                }
            }

            return false;
        }

        if(rotatingTileBelow(i, j))
        {
            if (((leftMostDominoColumn <= j) && (j <= rightMostDominoColumn)) && ((topMostDominoRow <= i) && ((i+1) <= bottomMostDominoRow)))
            {
                return true;
            }

            if (((i+1) > bottomMostDominoRow)  && ((leftMostDominoColumn <= j) && (j <= rightMostDominoColumn))) //1
            {
                if(((i+1) - topMostDominoRow) < 5)
                {   
                    
                    bottomMostDominoRow = i+1;
                    return true;
                }
            }

            if ((i < topMostDominoRow) && ((leftMostDominoColumn <= j) && (j <= rightMostDominoColumn))) //2
            {
                if((bottomMostDominoRow - i) < 5)
                {
                    
                    topMostDominoRow = i;
                    return true;
                }
            }

            if (((topMostDominoRow <= i) && ((i+1) <= bottomMostDominoRow)) && (j < leftMostDominoColumn)) //3
            {
                if((rightMostDominoColumn - j) < 5)
                {
                    
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            if (((topMostDominoRow <= i) && ((i+1) <= bottomMostDominoRow)) && (j > rightMostDominoColumn)) //4
            {
                if((j - leftMostDominoColumn) < 5)
                {
                    
                    rightMostDominoColumn = j;
                    return true;
                }
            }

            if (((i+1) > bottomMostDominoRow) && (j > rightMostDominoColumn)) //5
            {
                if((((i+1) - topMostDominoRow) < 5) && ((j - leftMostDominoColumn) < 5))
                {
                    
                    bottomMostDominoRow = i+1;
                    rightMostDominoColumn = j;
                    return true;
                }
            }
            
            if (((i+1) > bottomMostDominoRow) && (j < leftMostDominoColumn)) //6
            {
                if((((i+1) - topMostDominoRow) < 5) && ((rightMostDominoColumn - j) < 5))
                {
                    
                    bottomMostDominoRow = i+1;
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            if (((i) < topMostDominoRow) && (j > rightMostDominoColumn)) //7
            {
                if(((bottomMostDominoRow - i) < 5) && ((j - leftMostDominoColumn) < 5))
                {
                    
                    topMostDominoRow = i;
                    rightMostDominoColumn = j;
                    return true;
                }
            }
            
            if (((i) < topMostDominoRow) && (j < leftMostDominoColumn)) //8
            {
                if(((bottomMostDominoRow - i) < 5) && ((rightMostDominoColumn - j) < 5))
                {
                    
                    topMostDominoRow = i;
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            return false;
        }

        if(rotatingTileOnTop(i, j))
        {
            if (((leftMostDominoColumn <= j) && (j <= rightMostDominoColumn)) && ((topMostDominoRow <= (i-1)) && (i <= bottomMostDominoRow)))
            {
                return true;
            }

            if (((i) > bottomMostDominoRow)  && ((leftMostDominoColumn <= j) && (j <= rightMostDominoColumn))) //1
            {
                if(((i) - topMostDominoRow) < 5)
                {   
                    
                    bottomMostDominoRow = i;
                    return true;
                }
            }

            if (((i-1) < topMostDominoRow) && ((leftMostDominoColumn <= j) && (j <= rightMostDominoColumn))) //2
            {
                if((bottomMostDominoRow - (i-1)) < 5)
                {
                    
                    topMostDominoRow = i-1;
                    return true;
                }
            }

            if (((topMostDominoRow <= (i-1)) && (i <= bottomMostDominoRow)) && (j < leftMostDominoColumn)) //3
            {
                if((rightMostDominoColumn - j) < 5)
                {
                    
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            if (((topMostDominoRow <= (i-1)) && (i <= bottomMostDominoRow)) && (j > rightMostDominoColumn)) //4
            {
                if((j - leftMostDominoColumn) < 5)
                {
                    
                    rightMostDominoColumn = j;
                    return true;
                }
            }

            if (((i) > bottomMostDominoRow) && (j > rightMostDominoColumn)) //5
            {
                if((((i) - topMostDominoRow) < 5) && ((j - leftMostDominoColumn) < 5))
                {
                    
                    bottomMostDominoRow = i;
                    rightMostDominoColumn = j;
                    return true;
                }
            }
            
            if (((i) > bottomMostDominoRow) && (j < leftMostDominoColumn)) //6
            {
                if(((i - topMostDominoRow) < 5) && ((rightMostDominoColumn - j) < 5))
                {
                    
                    bottomMostDominoRow = i;
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            if (((i-1) < topMostDominoRow) && (j > rightMostDominoColumn)) //7
            {
                if(((bottomMostDominoRow - (i-1)) < 5) && ((j - leftMostDominoColumn) < 5))
                {
                    
                    topMostDominoRow = i-1;
                    rightMostDominoColumn = j;
                    return true;
                }
            }
            
            if (((i-1) < topMostDominoRow) && (j < leftMostDominoColumn)) //8
            {
                if(((bottomMostDominoRow - (i-1)) < 5) && ((rightMostDominoColumn - j) < 5))
                {
                    topMostDominoRow = i-1;
                    leftMostDominoColumn = j;
                    return true;
                }
            }

            return false;
        }

        return false;
    }

    private boolean verifyWithInKingdom(int i, int j)
    {
        int rowStart  = Math.max( i - 2, 0 );
        int rowFinish = Math.min( i + 2, ROWS - 1 );
        int colStart  = Math.max( j - 2, 0 );
        int colFinish = Math.min( j + 2, COLUMNS - 1 );

        for ( int curRow = rowStart; curRow <= rowFinish; curRow++ )
        {    
            for ( int curCol = colStart; curCol <= colFinish; curCol++ ) 
            {
                {
                    if (gridSquares[curRow][curCol].getBackground() != Color.WHITE)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void placeTile(int i, int j)
    {
        if(rotatingTileOnRight(i, j))
        {
            gridSquares[i][j].setBackground(rotateTile5.getBackground());
            gridSquares[i][j+1].setBackground(rotateTile6.getBackground());
            gridSquares[i][j].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile1());
            gridSquares[i][j+1].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile2());
            gridSquares[i][j].setIcon(rotateTile5.getIcon());
            gridSquares[i][j+1].setIcon(rotateTile6.getIcon());
            rotateTile5.setBackground(Color.WHITE);
            rotateTile6.setBackground(Color.WHITE);
            rotateTile5.setIcon(null);
            rotateTile6.setIcon(null);
            if(((frameManager.getNumOfPlayers() == 2) && (frameManager.getRoundNum() == 6)) || ((frameManager.getNumOfPlayers() == 4) && (frameManager.getRoundNum() == 12)))
            {
                frameManager.updateLastRoundTracker();
            }
        }

        if(rotatingTileOnLeft(i, j))
        {
            gridSquares[i][j].setBackground(rotateTile5.getBackground());
            gridSquares[i][j-1].setBackground(rotateTile4.getBackground());
            gridSquares[i][j].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile1());
            gridSquares[i][j-1].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile2());
            gridSquares[i][j].setIcon(rotateTile5.getIcon());
            gridSquares[i][j-1].setIcon(rotateTile4.getIcon());
            rotateTile5.setBackground(Color.WHITE);
            rotateTile4.setBackground(Color.WHITE);
            rotateTile5.setIcon(null);
            rotateTile4.setIcon(null);
            if(((frameManager.getNumOfPlayers() == 2) && (frameManager.getRoundNum() == 6)) || ((frameManager.getNumOfPlayers() == 4) && (frameManager.getRoundNum() == 12)))
            {
                frameManager.updateLastRoundTracker();
            }
        }

        if(rotatingTileBelow(i, j))
        {
            gridSquares[i][j].setBackground(rotateTile5.getBackground());
            gridSquares[i+1][j].setBackground(rotateTile8.getBackground());
            gridSquares[i][j].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile1());
            gridSquares[i+1][j].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile2());
            gridSquares[i][j].setIcon(rotateTile5.getIcon());
            gridSquares[i+1][j].setIcon(rotateTile8.getIcon());
            rotateTile5.setBackground(Color.WHITE);
            rotateTile8.setBackground(Color.WHITE);
            rotateTile5.setIcon(null);
            rotateTile8.setIcon(null);
            if(((frameManager.getNumOfPlayers() == 2) && (frameManager.getRoundNum() == 6)) || ((frameManager.getNumOfPlayers() == 4) && (frameManager.getRoundNum() == 12)))
            {
                frameManager.updateLastRoundTracker();
            }
        }

        if(rotatingTileOnTop(i, j))
        {
            gridSquares[i][j].setBackground(rotateTile5.getBackground());
            gridSquares[i-1][j].setBackground(rotateTile2.getBackground());
            gridSquares[i][j].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile1());
            gridSquares[i-1][j].setCrowns(frameManager.getCurrentDomino().getNumOfCrownsTile2());
            gridSquares[i][j].setIcon(rotateTile5.getIcon());
            gridSquares[i-1][j].setIcon(rotateTile2.getIcon());
            rotateTile5.setBackground(Color.WHITE);
            rotateTile2.setBackground(Color.WHITE);
            rotateTile5.setIcon(null);
            rotateTile2.setIcon(null);
            if(((frameManager.getNumOfPlayers() == 2) && (frameManager.getRoundNum() == 6)) || ((frameManager.getNumOfPlayers() == 4) && (frameManager.getRoundNum() == 12)))
            {
                frameManager.updateLastRoundTracker();
            }
        }

    }

    private void addGridSquares()
    {
        gridSquares = new GridSquares [ROWS][COLUMNS];
        for(int i = 0; i < ROWS; ++i){
            for(int j = 0; j < COLUMNS; ++j){
                gridSquares[i][j] = new GridSquares(i,j);
                gridSquares[i][j].addActionListener(this);
                centerPanel.add(gridSquares[i][j]);
            }
        }
    }

    private void quit() throws IOException
    {
        int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit the current game?", "Confirmation Required", JOptionPane.YES_NO_OPTION); 
            if(answer == 0){
                setVisible(false);
                frameManager.reset();
            }
    }

    private void save()
    {
        frameManager.saveGame();
    }

    private void rotateLeft(Domino domino)
    {
        Domino currDomino = domino;

		if (rotateTile6.getBackground() != Color.WHITE)
		{
			rotateTile2.setBackground(rotateTile6.getBackground());
            rotateTile2.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile6.setBackground(Color.WHITE);
            rotateTile6.setIcon(null);
		}

		else if (rotateTile2.getBackground() != Color.WHITE)
		{
			rotateTile4.setBackground(rotateTile2.getBackground());
            rotateTile4.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile2.setBackground(Color.WHITE);
            rotateTile2.setIcon(null);
		}

		else if (rotateTile4.getBackground() != Color.WHITE)
		{
			rotateTile8.setBackground(rotateTile4.getBackground());
            rotateTile8.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile4.setBackground(Color.WHITE);
            rotateTile4.setIcon(null);
		}

		else if (rotateTile8.getBackground() != Color.WHITE)
		{
			rotateTile6.setBackground(rotateTile8.getBackground());
            rotateTile6.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile8.setBackground(Color.WHITE);
            rotateTile8.setIcon(null);
		}
    }

    private void rotateRight(Domino domino)
    {
        Domino currDomino = domino;

		if (rotateTile2.getBackground() != Color.WHITE)
		{
			rotateTile6.setBackground(rotateTile2.getBackground());
            rotateTile6.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile2.setBackground(Color.WHITE);
            rotateTile2.setIcon(null);
		}

		else if (rotateTile6.getBackground() != Color.WHITE)
		{
			rotateTile8.setBackground(rotateTile6.getBackground());
            rotateTile8.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile6.setBackground(Color.WHITE);
            rotateTile6.setIcon(null);
		}

		else if (rotateTile8.getBackground() != Color.WHITE)
		{
			rotateTile4.setBackground(rotateTile8.getBackground());
            rotateTile4.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile8.setBackground(Color.WHITE);
            rotateTile8.setIcon(null);
		}

		else if (rotateTile4.getBackground() != Color.WHITE)
		{
			rotateTile2.setBackground(rotateTile4.getBackground());
            rotateTile2.setIcon(new ImageIcon(currDomino.getTile2Image()));
			rotateTile4.setBackground(Color.WHITE);
            rotateTile4.setIcon(null);
		}
    }

    private void discardDomino(Domino domino)
    {
        if (rotateTile5.getBackground() != Color.WHITE){

            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to discard this domino?", "Confirm", JOptionPane.YES_NO_OPTION); 

                if(confirm == JOptionPane.YES_OPTION){

                    rotateTile2.setBackground(Color.WHITE);
                    rotateTile2.setIcon(null);
                    rotateTile4.setBackground(Color.WHITE);
                    rotateTile4.setIcon(null);
                    rotateTile5.setBackground(Color.WHITE);
                    rotateTile5.setIcon(null);
                    rotateTile6.setBackground(Color.WHITE);
                    rotateTile6.setIcon(null);
                    rotateTile8.setBackground(Color.WHITE);
                    rotateTile8.setIcon(null);

                    frameManager.removeDomino();
                    frameManager.setRoundStatus("select domino");
                    frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));

                    if(((frameManager.getNumOfPlayers() == 2) && (frameManager.getRoundNum() == 6)) || ((frameManager.getNumOfPlayers() == 4) && (frameManager.getRoundNum() == 12)))
                    {
                        frameManager.updateLastRoundTracker();
                    }
                }

        }
    }

    public void setCurrentRndDominos(ArrayList<Domino> currentRndDominos)
    {
        currentDominos = currentRndDominos;
        Domino currentDomino;
        currentDomino = currentDominos.get(0);
        currentTile11.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        currentTile12.setIcon(new ImageIcon(currentDomino.getTile2Image()));
        currentTile11.setBackground(currentDomino.getTile1Color());
        currentTile12.setBackground(currentDomino.getTile2Color());

        currentDomino = currentDominos.get(1);
        currentTile21.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        currentTile22.setIcon(new ImageIcon(currentDomino.getTile2Image()));
        currentTile21.setBackground(currentDomino.getTile1Color());
        currentTile22.setBackground(currentDomino.getTile2Color());

        currentDomino = currentDominos.get(2);
        currentTile31.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        currentTile32.setIcon(new ImageIcon(currentDomino.getTile2Image()));
        currentTile31.setBackground(currentDomino.getTile1Color());
        currentTile32.setBackground(currentDomino.getTile2Color());

        currentDomino = currentDominos.get(3);
        currentTile41.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        currentTile42.setIcon(new ImageIcon(currentDomino.getTile2Image()));
        currentTile41.setBackground(currentDomino.getTile1Color());
        currentTile42.setBackground(currentDomino.getTile2Color());

    }

    public void setNextRndDominos(ArrayList<Domino> nextRndDominos)
    {
    	nextDominos = nextRndDominos;
        Domino currentDomino = nextRndDominos.get(0);
        nextRndTile11.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        nextRndTile12.setIcon(new ImageIcon(currentDomino.getTile2Image()));

        currentDomino = nextRndDominos.get(1);
        nextRndTile21.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        nextRndTile22.setIcon(new ImageIcon(currentDomino.getTile2Image()));

        currentDomino = nextRndDominos.get(2);
        nextRndTile31.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        nextRndTile32.setIcon(new ImageIcon(currentDomino.getTile2Image()));

        currentDomino = nextRndDominos.get(3);
        nextRndTile41.setIcon(new ImageIcon(currentDomino.getTile1Image()));
        nextRndTile42.setIcon(new ImageIcon(currentDomino.getTile2Image()));
    }

    public void setRound(int roundNum)
    {
        round.setText("Round #: " + roundNum);
    }

    public void setRemainingDominos(int dominosRemaining)
    {
        dominosLeft.setText("Dominos left: " + dominosRemaining);
    }

    public void enableCurrentRndDominos()
    {
        currentTile11.setEnabled(true);
        currentTile12.setEnabled(true);
        currentTile21.setEnabled(true);
        currentTile22.setEnabled(true);
        currentTile31.setEnabled(true);
        currentTile32.setEnabled(true);
        currentTile41.setEnabled(true);
        currentTile42.setEnabled(true);
    }

    public void setPlayerName(Player player)
    {
        whoTurn.setText(player.getName() + "'s turn");
        whoTurn.setForeground(player.getColour());
    }

    public void setCurrentDomino1Invisible()
    {
        currentTile11.setOpaque(false);
        currentTile12.setOpaque(false);
        currentTile11.setEnabled(false);
        currentTile12.setEnabled(false);
    }

    public void setCurrentDomino2Invisible()
    {
        currentTile21.setOpaque(false);
        currentTile22.setOpaque(false);
        currentTile21.setEnabled(false);
        currentTile22.setEnabled(false);
    }

    public void setCurrentDomino3Invisible()
    {
        currentTile31.setOpaque(false);
        currentTile32.setOpaque(false);
        currentTile31.setEnabled(false);
        currentTile32.setEnabled(false);
    }

    public void setCurrentDomino4Invisible()
    {
        currentTile41.setOpaque(false);
        currentTile42.setOpaque(false);
        currentTile41.setEnabled(false);
        currentTile42.setEnabled(false);
    }

    public void setCurrentDominoesVisible()
    {
        //currentTile11.setOpaque(true);
        currentTile11.setEnabled(true);
        currentTile12.setEnabled(true);
        currentTile21.setEnabled(true);
        currentTile22.setEnabled(true);
        currentTile31.setEnabled(true);
        currentTile32.setEnabled(true);
        currentTile41.setEnabled(true);
        currentTile42.setEnabled(true);
    }

    public void setNextDomino1Invisible()
    {
        nextRndTile11.setOpaque(false);
        nextRndTile12.setOpaque(false);
        nextRndTile11.setEnabled(false);
        nextRndTile12.setEnabled(false);
    }

    public void setNextDomino2Invisible()
    {
        nextRndTile21.setOpaque(false);
        nextRndTile22.setOpaque(false);
        nextRndTile21.setEnabled(false);
        nextRndTile22.setEnabled(false);
    }

    public void setNextDomino3Invisible()
    {
        nextRndTile31.setOpaque(false);
        nextRndTile32.setOpaque(false);
        nextRndTile31.setEnabled(false);
        nextRndTile32.setEnabled(false);
    }

    public void setNextDomino4Invisible()
    {
        nextRndTile41.setOpaque(false);
        nextRndTile42.setOpaque(false);
        nextRndTile41.setEnabled(false);
        nextRndTile42.setEnabled(false);
    }

    public void setNextRndDominoesVisible()
    {
        nextRndTile11.setOpaque(true);
        nextRndTile12.setOpaque(true);
        nextRndTile21.setOpaque(true);
        nextRndTile22.setOpaque(true);
        nextRndTile31.setOpaque(true);
        nextRndTile32.setOpaque(true);
        nextRndTile41.setOpaque(true);
        nextRndTile42.setOpaque(true);
    }

    public void setDoThis(String thisThing)
    {
        doThis.setText(thisThing);
    }

    public void showRotate(Domino domino)
    {
        rotateTile5.setIcon(new ImageIcon(domino.getTile1Image()));
        rotateTile6.setIcon(new ImageIcon(domino.getTile2Image()));
        rotateTile5.setBackground(domino.getTile1Color());
        rotateTile6.setBackground(domino.getTile2Color());
        rotateTile2.setBackground(Color.WHITE);
        rotateTile4.setBackground(Color.WHITE);
        rotateTile8.setBackground(Color.WHITE);
        rotateTile2.setIcon(null);
        rotateTile4.setIcon(null);
        rotateTile8.setIcon(null);
    }

    public void disableNextRndDominoes()
    {
        nextRndTile11.setEnabled(false);
        nextRndTile12.setEnabled(false);
        nextRndTile21.setEnabled(false);
        nextRndTile22.setEnabled(false);
        nextRndTile31.setEnabled(false);
        nextRndTile32.setEnabled(false);
        nextRndTile41.setEnabled(false);
        nextRndTile42.setEnabled(false);
    }

    public void enableNextRndDominoes1()
    {
        nextRndTile11.setEnabled(true);
        nextRndTile12.setEnabled(true);
    }

    public void enableNextRndDominoes2()
    {
        nextRndTile21.setEnabled(true);
        nextRndTile22.setEnabled(true);
    }

    public void enableNextRndDominoes3()
    {
        nextRndTile31.setEnabled(true);
        nextRndTile32.setEnabled(true);
    }

    public void enableNextRndDominoes4()
    {
        nextRndTile41.setEnabled(true);
        nextRndTile42.setEnabled(true);
    }

    public void enableEndTurn()
    {
        endTurn.setEnabled(true);
    }

    public void disableEndTurn()
    {
        endTurn.setEnabled(false);
    }

    public void viewPlayer1()
    {
        makeInvisible();
        frameManager.showPlayer1GameBoard();
    }

    public void viewPlayer2()
    {
        makeInvisible();
        frameManager.showPlayer2GameBoard();
    }

    public void viewPlayer3()
    {
        if (frameManager.getNumOfPlayers() == 4){
            makeInvisible();
            frameManager.showPlayer3GameBoard();
        }
    }

    public void viewPlayer4()
    {
        if (frameManager.getNumOfPlayers() == 4){
            makeInvisible();
            frameManager.showPlayer3GameBoard();
        }
    }

    public void setAsAIboard() 
    {
    	this.AIboard = true;
    }
    
    public boolean isAIboard() 
    {
    	return AIboard;
    }
    
    public void doAIaction()
    {
    	//if first round, pick from current pile and enable end turn
    	//if not first round, place tile, pick next tile and enable end turn
    	if (frameManager.getRoundStatus().equals("starting round") && frameManager.currentDominosAvailable() && !(frameManager.getPlayerTookTurn(frameManager.getPlayerNumber(this)))){
    		AIpickTile();
    	} else {
    		AIplaceTile();        // Easy AI does not know how to rotate and just arbitrarily places and picks tiles 
    		AIpickNextTile();
    	}
    }
    
    public void AIpickTile()
    {	
    	boolean picked = false;
    	while(picked==false){
    		int num = rand.nextInt(currentDominos.size());
    		if(currentDominos.get(num).getAvailable() == true) {
    			Domino current = currentDominos.get(num);
                frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                if(num == 0)frameManager.setCurrentDomino1Invisible();
                if(num == 1)frameManager.setCurrentDomino2Invisible();
                if(num == 2)frameManager.setCurrentDomino3Invisible();
                if(num == 3)frameManager.setCurrentDomino4Invisible();
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                picked = true;
                setDoThis("The computer has picked tile #" + (num+1) + ", please end turn to continue");
                enableEndTurn();// TEMPORARY 
    		}
    	}
    }
    
    public void AIplaceTile() 
    {
    	ArrayList<Integer> icords= new ArrayList<>();
    	ArrayList<Integer> jcords= new ArrayList<>();
    	for(int i = 0; i < ROWS; ++i){
            for(int j = 0; j < COLUMNS; ++j){
            	if (gridSquares[i][j].getBackground() == Color.WHITE){
                    if (rotatingTileOnRight(i, j)){
                        if (verifyAdjacentSquare(i,j+1) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                            icords.add(i);
                            jcords.add(j);
                        	}
                        }
                    else if (rotatingTileOnLeft(i, j)){
                        if (verifyAdjacentSquare(i,j-1) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                        	icords.add(i);
                            jcords.add(j);
                        	}
                        }
                    else if (rotatingTileBelow(i, j)){

                        if (verifyAdjacentSquare(i+1,j) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                        	icords.add(i);
                            jcords.add(j);
                        	}
                       	}
                    else if (rotatingTileOnTop(i, j))
                    {
                        if (verifyAdjacentSquare(i-1,j) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                        	icords.add(i);
                            jcords.add(j);
                        }
                        }
                    }
                }
            }
    	if(icords.size()==0) {          //If no placement available, discard domino
    		rotateTile2.setBackground(Color.WHITE);
            rotateTile2.setIcon(null);
            rotateTile4.setBackground(Color.WHITE);
            rotateTile4.setIcon(null);
            rotateTile5.setBackground(Color.WHITE);
            rotateTile5.setIcon(null);
            rotateTile6.setBackground(Color.WHITE);
            rotateTile6.setIcon(null);
            rotateTile8.setBackground(Color.WHITE);
            rotateTile8.setIcon(null);
            //System.out.println("I had to discard the domino :(");   //DEBUG
            frameManager.removeDomino();
            frameManager.setRoundStatus("select domino");
            frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
            if(((frameManager.getNumOfPlayers() == 2) && (frameManager.getRoundNum() == 6)) || ((frameManager.getNumOfPlayers() == 4) && (frameManager.getRoundNum() == 12)))
            {
                frameManager.updateLastRoundTracker();
            }
    	} else { 						//place from list randomly here
    		int num = rand.nextInt(icords.size());
        	placeTile(icords.get(num),jcords.get(num));
            frameManager.getListOfPlayers().get(playerNum-1).setPoints(calculateScore());
        	frameManager.setRoundStatus("select domino");
            frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
    	}
    }
    
    public void AIpickNextTile()
    {
    	boolean picked = false;
    	while(picked==false){
    		int num = rand.nextInt(nextDominos.size());
    		if(nextDominos.get(num).getAvailable() == true) {
    			Domino current = nextDominos.get(num);
                frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                if(num == 0)frameManager.setNextDomino1Invisible();
                if(num == 1)frameManager.setNextDomino2Invisible();
                if(num == 2)frameManager.setNextDomino3Invisible();
                if(num == 3)frameManager.setNextDomino4Invisible();
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                picked = true;
                setDoThis("The computer has picked tile #" + (num+1) + ", please end turn to continue");
                enableEndTurn();// TEMPORARY 
    		}
    	}
    }

    public int calculateScore()
    {
        int rows = 9;
        int cols = 9;
    
        Queue<Point> q = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
    
        int totalScore = 0;
        for(int r=0; r<rows; r++)
        {
            for(int c=0; c<cols; c++)
            {
                if(!visited[r][c])
                {
                    q.add(new Point(r, c));
                    visited[r][c] = true;

                    int crowns = gridSquares[r][c].getCrowns();
                    int score = 0;
                    while(!q.isEmpty())
                    {
                        Point p = q.poll();
                        score += 1;

                        for(int d=0; d<4; d++)
                        {
                            int i = p.x + dRow[d];
                            int j = p.y + dCol[d];
                        
                            if(i >= 0 && i < rows && j >= 0 && j < cols && !visited[i][j] && (gridSquares[i][j].getBackground() == gridSquares[r][c].getBackground())) 
                            {
                                q.add(new Point(i, j));
                                crowns = crowns + gridSquares[i][j].getCrowns();
                                visited[i][j] = true;
                            }
                        }
                    }
                    totalScore = totalScore + (score*crowns);
                }
            }
        }
        return totalScore;
    }
    
    public void doHardAIaction()
    {
    	//if first round, pick from current pile and enable end turn
    	//if not first round, place tile, pick next tile and enable end turn
    	if (frameManager.getRoundStatus().equals("starting round") && frameManager.currentDominosAvailable() && !(frameManager.getPlayerTookTurn(frameManager.getPlayerNumber(this)))){
    		AIpickTile();
    	} else {
    		hardAIplaceTile();        // Hard AI selects tiles similar to what it has
    		hardAIpickNextTile();
    	}
    }
    
    public void hardAIplaceTile()
    {   
    	ArrayList<Integer> icords= new ArrayList<>();
    	ArrayList<Integer> jcords= new ArrayList<>();
    	for(int num = 0; num < 4 ; num++) {
        	for(int i = 0; i < ROWS; ++i){
                for(int j = 0; j < COLUMNS; ++j){
                	if (gridSquares[i][j].getBackground() == Color.WHITE){
                        if (rotatingTileOnRight(i, j)){
                            if (verifyAdjacentSquare(i,j+1) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                                icords.add(i);
                                jcords.add(j);
                            	}
                            }
                        else if (rotatingTileOnLeft(i, j)){
                            if (verifyAdjacentSquare(i,j-1) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                            	icords.add(i);
                                jcords.add(j);
                            	}
                            }
                        else if (rotatingTileBelow(i, j)){

                            if (verifyAdjacentSquare(i+1,j) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                            	icords.add(i);
                                jcords.add(j);
                            	}
                           	}
                        else if (rotatingTileOnTop(i, j))
                        {
                            if (verifyAdjacentSquare(i-1,j) && verifyTerrainRule(i,j) && verifyWithInKingdom(i,j) && verifyDimensions(i,j)){
                            	icords.add(i);
                                jcords.add(j);
                            }
                            }
                        }
                    }
                }
    		if(icords.size()==0) {
    			rotateLeft(frameManager.getCurrentDomino());
    		}
    		else {
    			break;
    		}
    	}

    	if(icords.size()==0) {          //If no placement available, discard domino
    		rotateTile2.setBackground(Color.WHITE);
            rotateTile2.setIcon(null);
            rotateTile4.setBackground(Color.WHITE);
            rotateTile4.setIcon(null);
            rotateTile5.setBackground(Color.WHITE);
            rotateTile5.setIcon(null);
            rotateTile6.setBackground(Color.WHITE);
            rotateTile6.setIcon(null);
            rotateTile8.setBackground(Color.WHITE);
            rotateTile8.setIcon(null);
            //System.out.println("I had to discard the domino :(");   //DEBUG
            frameManager.removeDomino();
            frameManager.setRoundStatus("select domino");
            frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
            if(((frameManager.getNumOfPlayers() == 2) && (frameManager.getRoundNum() == 6)) || ((frameManager.getNumOfPlayers() == 4) && (frameManager.getRoundNum() == 12)))
            {
                frameManager.updateLastRoundTracker();
            }
    	} else { 						//place from list randomly here
    		int num = rand.nextInt(icords.size());
        	placeTile(icords.get(num),jcords.get(num));
            frameManager.getListOfPlayers().get(playerNum-1).setPoints(calculateScore());
        	frameManager.setRoundStatus("select domino");
            frameManager.selectNextRndDomino(frameManager.getPlayerNumber(this));
    	}
    }
    
    
    public int hardAIpickNextTile()
    {
    	ArrayList <Color> biomes = new ArrayList<Color>();
    	ArrayList <Integer> count = new ArrayList<Integer>();
    	
    	biomes.add(Color.BLUE);
    	biomes.add(Color.GREEN);
    	biomes.add(Color.CYAN);
    	biomes.add(Color.YELLOW);
    	biomes.add(Color.BLACK);
    	biomes.add(Color.LIGHT_GRAY);
    	
    	count.add(0);
    	count.add(0);
    	count.add(0);
    	count.add(0);
    	count.add(0);
    	count.add(0);
    	
    	for(int i = 0; i < ROWS; ++i){
            for(int j = 0; j < COLUMNS; ++j){
            	if(gridSquares[i][j].getBackground() == Color.BLUE)count.set(0, count.get(0)+1);
            	if(gridSquares[i][j].getBackground() == Color.GREEN)count.set(1, count.get(1)+1);
            	if(gridSquares[i][j].getBackground() == Color.CYAN)count.set(2, count.get(2)+1);
            	if(gridSquares[i][j].getBackground() == Color.YELLOW)count.set(3, count.get(4)+1);
            	if(gridSquares[i][j].getBackground() == Color.BLACK)count.set(4, count.get(4)+1);
            	if(gridSquares[i][j].getBackground() == Color.LIGHT_GRAY)count.set(5, count.get(5)+1);
            }
    	}
    	
    	//check available dominos if they have tile similar to highest most occurring type, then second most occurring type, then pick at random
    	int size = count.size();
        for (int i = 0; i < size - 1; i++)
        	for (int j = 0; j < size - i - 1; j++)
        	  if (count.get(j) > count.get(j+1)) {
                
        		  int tempInt = count.get(j);
        		  Color tempColor = biomes.get(j);
              
        		  count.set(j, count.get(j+1));
        		  biomes.set(j, biomes.get(j+1));
              
        		  count.set(j+1, tempInt);
        		  biomes.set(j+1, tempColor);
        		  }
    	
    	
    	Color highest = biomes.get(biomes.size()-1);
    	Color secondHighest = biomes.get(biomes.size()-2);
    	if(count.get(count.size()-2)==0) {
    		secondHighest = Color.WHITE;
    	}
    	
    	for(int i = 0; i <nextDominos.size(); i++ ) {
    		Domino current = nextDominos.get(i);
    		if((current.getTile1Color()==highest || current.getTile2Color()==highest) && current.getAvailable() == true) {
    			frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                if(i == 0)frameManager.setNextDomino1Invisible();
                if(i == 1)frameManager.setNextDomino2Invisible();
                if(i == 2)frameManager.setNextDomino3Invisible();
                if(i == 3)frameManager.setNextDomino4Invisible();
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                setDoThis("The computer has picked tile #" + (i+1) + ", please end turn to continue");
                enableEndTurn();
                //System.out.println("Did hard pick, chose most frequent tile");//DEBUG
    			return 1;
    		}
    	}
    	
    	for(int i = 0; i <nextDominos.size(); i++ ) {
    		Domino current = nextDominos.get(i);
    		if((current.getTile1Color()==secondHighest || current.getTile2Color()==secondHighest) && current.getAvailable() == true) {
    			frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                if(i == 0)frameManager.setNextDomino1Invisible();
                if(i == 1)frameManager.setNextDomino2Invisible();
                if(i == 2)frameManager.setNextDomino3Invisible();
                if(i == 3)frameManager.setNextDomino4Invisible();
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                setDoThis("The computer has picked tile #" + (i+1) + ", please end turn to continue");
                enableEndTurn(); 
                //System.out.println("Did hard pick, chose 2nd most frequent tile");//DEBUG
                return 1;
    		}
    	}
    	
    	boolean picked = false;
    	while(picked==false){
    		int num = rand.nextInt(nextDominos.size());
    		if(nextDominos.get(num).getAvailable() == true) {
    			Domino current = nextDominos.get(num);
                frameManager.addDominoToPlayer(current, frameManager.getPlayerNumber(this));
                if(num == 0)frameManager.setNextDomino1Invisible();
                if(num == 1)frameManager.setNextDomino2Invisible();
                if(num == 2)frameManager.setNextDomino3Invisible();
                if(num == 3)frameManager.setNextDomino4Invisible();
                frameManager.setPlayerTookTurn(frameManager.getPlayerNumber(this), true);
                setDoThis("The computer has picked tile #" + (num+1) + ", please end turn to continue");
                enableEndTurn();// TEMPORARY 
                return 1;
    		}
    	}
    	return 0;
    }

    
/*
    public static void main(String[] args) {
        FrameManager frameManager = new FrameManager();
        new GameBoard(frameManager);
        frameManager.showGameBoard();
    }
*/
}
