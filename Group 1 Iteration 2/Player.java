import java.awt.Color;

public class Player 
{
	private String name;
	private int points;
	private boolean AI;
	private Color colour;
	
	public Player(String name, Color colour, boolean isAI)
	{
		this.name=name;
		this.colour= colour;
		this.AI=isAI;
		points=0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isAI() {
		return AI;
	}

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	
}
