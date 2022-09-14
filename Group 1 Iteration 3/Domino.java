import java.awt.Color;

import javax.lang.model.type.NullType;

public class Domino
{
    private Color tile1Color, tile2Color;
    private int numOfCrownsTile1, numOfCrownsTile2, numericalVal;
    private int tile1x, tile1y, tile2x, tile2y;
    private Player pickedBy;
    private boolean available, played;


    public Domino(Color tile1Color, Color tile2Color, int numOfCrownsTile1, int numOfCrownsTile2, int numericalVal)
    {
        this.tile1Color = tile1Color;
        this.tile2Color = tile2Color;
        this.numOfCrownsTile1 = numOfCrownsTile1;
        this.numOfCrownsTile2 = numOfCrownsTile2;
        this.numericalVal = numericalVal;
        available = true;
        played = false;
        tile1x = 0;
        tile1y = 0;
        tile2x = 0;
        tile2y = 0;

    }

    public Color getTile1Color()
    {
        return tile1Color;
    }

    public Color getTile2Color()
    {
        return tile2Color;
    }

    public int getNumericalVal()
    {
        return numericalVal;
    }

    public int getNumOfCrownsTile1()
    {
        return numOfCrownsTile1;
    }

    public int getNumOfCrownsTile2()
    {
        return numOfCrownsTile2;
    }

    public int getTile1x()
    {
        return tile1x;
    }

    public void setTile1x(int tile1x)
    {
        this.tile1x = tile1x;
    }

    public int getTile1y()
    {
        return tile1y;
    }

    public void setTile1y(int tile1y)
    {
        this.tile1y = tile1y;
    }

    public int getTile2x()
    {
        return tile2x;
    }

    public void setTile2x(int tile2x)
    {
        this.tile2x = tile2x;
    }

    public int getTile2y()
    {
        return tile2y;
    }

    public void setTile2y(int tile2y)
    {
        this.tile2y = tile2y;
    }

    public Player getPickedBy()
    {
        return pickedBy;
    }
    public void setPickedBy(Player player)
    {
        pickedBy = player;
    }    

    public boolean getAvailable()
    {
        return available;
    }

    public void setAvailable(boolean available)
    {
        this.available = available;
    }

    public boolean getPlayed()
    {
        return played;
    }

    public void setPlayed(boolean played)
    {
        this.played = played;
    }
}