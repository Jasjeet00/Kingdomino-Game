import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class KingDomino 
{

    private FrameManager frameManager;
	private ArrayList<Domino> currentRndDominos;
	private ArrayList<Domino> nextRndDominos;
	private ArrayList<Player> players;
	private DeckOfDominos dealer;
	private int roundNum, numOfPlayers, currentPlayersTurn;
	private Random rand;
	private String roundStatus;
	private Domino currentDomino;

	
	public KingDomino()
	{
		rand = new Random();
		currentRndDominos = new ArrayList<>();
		nextRndDominos = new ArrayList<>();
		dealer = new DeckOfDominos();
		players = new ArrayList<>();
		frameManager = new FrameManager(this);
        frameManager.showMainFrame();
		roundNum = 1;
		numOfPlayers = 2;
		roundStatus = "starting round";

	}

	public void startingRound()
	{
		dealer.createDeck();
		currentRndDominos = dealer.randomDominos();
		nextRndDominos = dealer.randomDominos();
		frameManager.setCurrentRndDominos(currentRndDominos);
		frameManager.setNextRndDominos(nextRndDominos);
		frameManager.setRound(roundNum);
		frameManager.setRemainingDominos(dealer.getRemainingDominos());
		numOfPlayers = frameManager.getNumOfPlayers();
		currentPlayersTurn = rand.nextInt(numOfPlayers);
		frameManager.setDoThis("Select a domino from current round dominoes");
		showCurrentPlayerBoard();

	}

	public void nextPlayersTurn()
	{
		if(roundStatus.equals("starting round") && currentDominosAvailable()){
			hideCurrentPlayerBoard();
			currentPlayersTurn = (currentPlayersTurn + 1) % numOfPlayers;
			showCurrentPlayerBoard(); 
		}
		else if(roundStatus.equals("starting round") && !currentDominosAvailable() && !playedDominoes()){
			//roundStatus = "not starting round";
			hideCurrentPlayerBoard();
			frameManager.setCurrentDominoesVisible();
			for (Domino domino : currentRndDominos){
				if(!domino.getPlayed()){
					currentPlayersTurn = (domino.getPickedBy().getPlayerNumber()) - 1;
					domino.setPlayed(true);
					currentDomino = domino;
					break;
				}
			}
			frameManager.showRotate(currentDomino);
			frameManager.setDoThis("Rotate and place the domino");
			showCurrentPlayerBoard();
			
		}
	}

	private void showCurrentPlayerBoard()
	{
		if (currentPlayersTurn == 0){
			frameManager.showPlayer1GameBoard();
		}
		else if (currentPlayersTurn == 1){
			frameManager.showPlayer2GameBoard();
		}
		else if (currentPlayersTurn == 2){
			frameManager.showPlayer3GameBoard();
		}
		else if (currentPlayersTurn == 3)
		{
			frameManager.showPlayer4GameBoard();
		}
	}

	private void hideCurrentPlayerBoard()
	{
		if (currentPlayersTurn == 0){
			frameManager.hidePlayer1GameBoard();
		}
		else if (currentPlayersTurn == 1){
			frameManager.hidePlayer2GameBoard();
		}
		else if (currentPlayersTurn == 2){
			frameManager.hidePlayer3GameBoard();
		}
		else if (currentPlayersTurn == 3)
		{
			frameManager.hidePlayer4GameBoard();
		}
	}

	public int getRoundNum()
	{
		return roundNum;
	}

	public void incrementRnd()
	{
		++roundNum;
	}

	public int getRemainingDominos()
	{
		return dealer.getRemainingDominos();
	}

	public void setTotalPlayers(int totalPlayers)
	{
		numOfPlayers = totalPlayers;
	}

	public void createHumanPlayer(String playerName, Color playerColor, int playerNumber)
	{
		players.add(new Player(playerName, playerColor, playerNumber));
	}

	public String getRoundStatus()
	{
		return roundStatus;
	}

	public void setRoundStatus(String roundStatus)
	{
		this.roundStatus = roundStatus;
	}

	public void addDominoToPlayer(Domino domino, int player)
	{
		if (player == 1){
			players.get(0).addDomino(domino);
			domino.setAvailable(false);
			domino.setPickedBy(players.get(0));
		}
		else if(player == 2){
			players.get(1).addDomino(domino);
			domino.setAvailable(false);
			domino.setPickedBy(players.get(1));
		}
		else if(player == 3){
			players.get(2).addDomino(domino);
			domino.setAvailable(false);
			domino.setPickedBy(players.get(2));
		}
		else if(player == 4){
			players.get(3).addDomino(domino);
			domino.setAvailable(false);
			domino.setPickedBy(players.get(3));
		}
	}

	public boolean currentDominosAvailable()
	{
		boolean available = false;
		for(Domino domino : currentRndDominos){
			if (domino.getAvailable() == true){
				available = true;
			}
		}
		return available;
	}

	private boolean playedDominoes()
	{
		boolean played = true;
		for(Domino domino : currentRndDominos){
			if (domino.getPlayed() == false){
				played = false;
			}
		}
		return played;
	}
}