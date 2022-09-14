import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.UIManager;


public class GameFrame extends JFrame
{
    private Color backgroundColour;
    private Color buttonColour;
    private Color textColour;


    public GameFrame()
    {
        super();
        setBounds(100, 100, 700, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridBagLayout());
        setColours();
    }

    public void setColours()
    {
        Hashtable<String, Color> lookup = new Hashtable<String, Color>();
        lookup.put("standardTXT", Color.white);
        lookup.put("standardBG", Color.darkGray);
        lookup.put("standardBTN", Color.gray);
        lookup.put("zestyTXT", Color.white);
        lookup.put("zestyBG", new Color(15, 32, 128));
        lookup.put("zestyBTN", new Color(133, 192, 249));
        lookup.put("elegantTXT", Color.white);
        lookup.put("elegantBG", new Color(56, 33, 25));
        lookup.put("elegantBTN", new Color(204, 190, 159));
        
        try{
            Scanner fScn = new Scanner(new File("./colorConfiguration.txt"));
            String data;
            data = fScn.nextLine();
            String[] token = data.split(" ");
            backgroundColour = lookup.get(token[0]);
            buttonColour = lookup.get(token[1]);
            textColour = lookup.get(token[2]);
            fScn.close();
        }catch (Exception ex){
            return;
        }

        UIManager.put("Panel.background", getBackgroundColour());
        UIManager.put("Button.background", getButtonColour());
        UIManager.put("Button.foreground", getTextColour());
        UIManager.put("Button.border", null);
        UIManager.put("Button.focus", getButtonColour());
        UIManager.put("Button.select", getButtonColour());
        UIManager.put("Label.foreground", getTextColour());
        UIManager.put("OptionPane.foreground", getTextColour());
        UIManager.put("OptionPane.messageForeground", getTextColour());
        UIManager.put("OptionPane.background", getBackgroundColour());
        UIManager.put("ComboBox.background", getButtonColour());
        UIManager.put("ComboBox.foreground", getTextColour());
        UIManager.put("ComboBox.selectionBackground", getBackgroundColour());
        UIManager.put("windowBorder", getBackgroundColour());
        UIManager.put("ComboBox.selectionForeground", getTextColour());
    }

    public Color getBackgroundColour()
    {
        return backgroundColour; 
    }

    public Color getButtonColour()
    {
        return buttonColour; 
    }

    public Color getTextColour()
    {
        return textColour; 
    }

    public void makeVisible()
    {
        setVisible(true);
    }

    public void makeInvisible()
    {
        setVisible(false);
    }
}