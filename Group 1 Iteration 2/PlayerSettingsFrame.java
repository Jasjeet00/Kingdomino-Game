import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerSettingsFrame extends GameFrame
{
	private FrameManager frameManager;
	private int AIplayers;
	private int totalPlayers;
	private int humanPlayers;
	private String difficulty;
	
	public PlayerSettingsFrame(FrameManager frameManager) 
	{
		super();
		this.frameManager = frameManager;
		makeFrame();
		
		AIplayers=0;
		totalPlayers=0;
		humanPlayers=0;
		difficulty="";
	}
	
	public void makeFrame()
    {
		JPanel playerSelectPanel= new JPanel();
		playerSelectPanel.setLayout( new FlowLayout());
		JLabel topLabel= new JLabel("Select number of players");
		
		JPanel humanPlayerPanel=new JPanel();
		JLabel humanPlayerLabel= new JLabel("Select number of human players");
		
		JPanel bottomPannel=new JPanel();
		
		JPanel inputPanel1=new JPanel();
		JPanel inputPanel2=new JPanel();
		
		JPanel playerPanel=new JPanel();
		
		JPanel AIinfoPanel=new JPanel();
		JLabel AIinfo =new JLabel("");
		JButton easyAI=new JButton("Easy");
		easyAI.addActionListener(e->difficulty="easy");
		JButton hardAI=new JButton("Hard");
		easyAI.addActionListener(e->difficulty="hard");
		
		JPanel playerInfoPanel=new JPanel();
		JLabel playerInfoLabel=new JLabel("");
		
		JPanel humanPlayerInfoPanel=new JPanel();
		JLabel humanPlayerInfoLabel=new JLabel("");
		
		JPanel AIselectionPanel = new JPanel();
		JLabel AIselectionLabel = new JLabel("Please select AI difficulty: ");
		
		/*
		 *  Buttons for selecting total number of players
		 */
		JButton playerSelect1= new JButton("2");
		playerSelect1.addActionListener(e->{totalPlayers=2;
		playerInfoLabel.setText("There will be "+ totalPlayers +" total players");
		humanPlayers=0;
		AIplayers=0;
		AIinfo.setText("");
		humanPlayerInfoLabel.setText("");
		});
		
		JButton playerSelect2= new JButton("3");
		playerSelect2.addActionListener(e->{totalPlayers=3;
		playerInfoLabel.setText("There will be "+ totalPlayers +" total players");
		humanPlayers=0;
		AIplayers=0;
		AIinfo.setText("");
		humanPlayerInfoLabel.setText("");
		});
		
		JButton playerSelect3= new JButton("4");
		playerSelect3.addActionListener(e->{totalPlayers=4;
		playerInfoLabel.setText("There will be "+ totalPlayers +" total players");
		humanPlayers=0;
		AIplayers=0;
		AIinfo.setText("");
		humanPlayerInfoLabel.setText("");
		});
		
		/*
		 *  Buttons for selecting total number of human players
		 *  Cannot be more than total number of players
		 *  Also sets number of AI players
		 */
		JButton humanSelect1= new JButton("1");
		humanSelect1.addActionListener(e->{
		if(totalPlayers>=1) {
			humanPlayers=1;
			humanPlayerInfoLabel.setText("There will be "+ humanPlayers +" human players");
			AIplayers= totalPlayers-humanPlayers;
			AIinfo.setText("There will be "+ AIplayers +" AI players");
			AIselectionPanel.add(AIselectionLabel);
			AIselectionPanel.add(easyAI);
			AIselectionPanel.add(hardAI);
		} else {
			JOptionPane.showMessageDialog(null, "Number of human players cannot be more than the total number of players!.", null, JOptionPane.PLAIN_MESSAGE);
		}
		});
		
		JButton humanSelect2= new JButton("2");
		humanSelect2.addActionListener(e->{
		if(totalPlayers>=2) {
			humanPlayers=2;
			humanPlayerInfoLabel.setText("There will be "+ humanPlayers +" human players");
			AIplayers= totalPlayers-humanPlayers;
			AIinfo.setText("There will be "+ AIplayers +" AI players");
			AIselectionPanel.add(AIselectionLabel);
			AIselectionPanel.add(easyAI);
			AIselectionPanel.add(hardAI);
		} else {
			JOptionPane.showMessageDialog(null, "Number of human players cannot be more than the total number of players!.", null, JOptionPane.PLAIN_MESSAGE);
		}
		});
		
		JButton humanSelect3= new JButton("3");
		humanSelect3.addActionListener(e->{
		if(totalPlayers>=3) {
			humanPlayers=3;
			humanPlayerInfoLabel.setText("There will be "+ humanPlayers +" human players");
			AIplayers= totalPlayers-humanPlayers;
			AIinfo.setText("There will be "+ AIplayers +" AI players");
			AIselectionPanel.add(AIselectionLabel);
			AIselectionPanel.add(easyAI);
			AIselectionPanel.add(hardAI);
		} else {
			JOptionPane.showMessageDialog(null, "Number of human players cannot be more than the total number of players!.", null, JOptionPane.PLAIN_MESSAGE);
		}
		});
		
		JButton humanSelect4= new JButton("4");
		humanSelect4.addActionListener(e->{
		if(totalPlayers>=4) {
			humanPlayers=4;
			humanPlayerInfoLabel.setText("There will be "+ humanPlayers +" human players");
			AIplayers= totalPlayers-humanPlayers;
			AIinfo.setText("There will be "+ AIplayers +" AI players");
			AIselectionPanel.add(AIselectionLabel);
			AIselectionPanel.add(easyAI);
			AIselectionPanel.add(hardAI);
		} else {
			JOptionPane.showMessageDialog(null, "Number of human players cannot be more than the total number of players!.", null, JOptionPane.PLAIN_MESSAGE);
		}
		});
		
		
		
		JLabel info= new JLabel("Enter player name and select colours");
		
		JTextField text1= new JTextField("Player 1");
		text1.setPreferredSize(new Dimension(80,30));
		JTextField text2= new JTextField("Player 2");
		text2.setPreferredSize(new Dimension(80,30));
     	JTextField text3= new JTextField("Player 3");
     	text3.setPreferredSize(new Dimension(80,30));
     	JTextField text4= new JTextField("Player 4");
     	text4.setPreferredSize(new Dimension(80,30));
     	
     	String[] colors={"","black","red","blue","cyan","gray","green","yellow","magenta","orange","pink","white"};
     	JComboBox<String> box1=new JComboBox<String>(colors);
     	JComboBox<String> box2=new JComboBox<String>(colors);
     	JComboBox<String> box3=new JComboBox<String>(colors);
     	JComboBox<String> box4=new JComboBox<String>(colors);
     	
     	JButton backButton= new JButton("back");
		backButton.addActionListener(e->back());
		
		JButton startButton= new JButton("start game");
		startButton.addActionListener(e->next());
     
     	playerSelectPanel.add(topLabel);
     	playerSelectPanel.add(playerSelect1);
     	playerSelectPanel.add(playerSelect2);
     	playerSelectPanel.add(playerSelect3);
     	
     	playerInfoPanel.add(playerInfoLabel);
     	
     	humanPlayerPanel.add(humanPlayerLabel);
     	humanPlayerPanel.add(humanSelect1);
     	humanPlayerPanel.add(humanSelect2);
     	humanPlayerPanel.add(humanSelect3);
     	humanPlayerPanel.add(humanSelect4);
     	
     	humanPlayerInfoPanel.add(humanPlayerInfoLabel);
     	
     	AIinfoPanel.add(AIinfo);
     	
     	

     	playerPanel.add(info);
     	
     	inputPanel1.add(text1);
     	inputPanel1.add(box1);
     	inputPanel1.add(text2);
     	inputPanel1.add(box2);
     	
     	inputPanel2.add(text3);
     	inputPanel2.add(box3);
     	inputPanel2.add(text4);
     	inputPanel2.add(box4);
    
     	bottomPannel.add(backButton);
     	bottomPannel.add(startButton);
     
     
     	getContentPane().setLayout(new GridLayout(0,1));
     	getContentPane().add(playerSelectPanel);
     	getContentPane().add(playerInfoPanel);
     	getContentPane().add(humanPlayerPanel);
     	getContentPane().add(humanPlayerInfoPanel);
     	getContentPane().add(AIinfoPanel);
     	getContentPane().add(AIselectionPanel);
     	getContentPane().add(playerPanel);
     	getContentPane().add(inputPanel1);
     	getContentPane().add(inputPanel2);
     	getContentPane().add(bottomPannel);
     	
     	setVisible( false);
    }
	
	public void back()
	{
		AIplayers=0;
		totalPlayers=0;
		humanPlayers=0;
		difficulty="";
		setVisible(false);
		frameManager.showGameModeFrame();
		
	}
	
	public void next()
	{	
		if(totalPlayers==humanPlayers+AIplayers && totalPlayers!=0 && humanPlayers !=0) {
			setVisible(false);
			frameManager.showGameBoard();
			
			//TODO Create player entities and store in gameControl
			
		} else {
			JOptionPane.showMessageDialog(null, "Invalid settings inputted! Please select valid settings for your game.", null, JOptionPane.PLAIN_MESSAGE);
		}	
	}
}

    

    
