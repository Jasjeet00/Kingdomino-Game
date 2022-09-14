import java.awt.Color;
import java.util.ArrayList;

public class Player 
{
	private String name;
	private int points, playerNumber;
	private boolean AI;
	private Color colour;
	private ArrayList<Domino> dominoes;
	
	public Player(String name, Color colour, int playerNumber)
	{
		this.name=name;
		this.colour= colour;
		this.AI= false;
		this.playerNumber = playerNumber;
		points=0;
		dominoes = new ArrayList<>();
	}

	public Player(String name, Color colour, int playerNumber, boolean isAI)
	{
		this.name=name;
		this.colour= colour;
		this.AI= isAI;
		this.playerNumber = playerNumber;
		points=0;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public int getPoints() 
	{
		return points;
	}

	public void setPoints(int points) 
	{
		this.points = points;
	}

	public boolean isAI() 
	{
		return AI;
	}

	public Color getColour() 
	{
		return colour;
	}

	public void setColour(Color colour) 
	{
		this.colour = colour;
	}

	public int getPlayerNumber()
	{
		return playerNumber;
	}
	
	public void addDomino(Domino domino)
	{
		dominoes.add(domino);
	}
}
