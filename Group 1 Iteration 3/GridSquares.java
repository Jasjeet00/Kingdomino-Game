import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class GridSquares extends JButton
{
    
    private int xcoord, ycoord;

    public GridSquares(int y, int x)
    {
        super();
        xcoord = x;
        ycoord = y;
        setPreferredSize(new Dimension(50,50));
        setBackground(Color.WHITE);
        setOpaque(true);
        setBorderPainted(false);
    }

    public int getXCoord()  {return xcoord;}
    public int getYCoord()  {return ycoord;}
}