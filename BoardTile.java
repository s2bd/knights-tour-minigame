import java.awt.Color;
import javax.swing.*;

/**
 * BoardTiles generate square-like GUI objects on a visual window.
 *
 * @author Dewan Mukto (dmimukto)
 * @student_id 202004321
 * @version 2023 Jan 10
 * @attribution Mark Hatcher (mhatcher) - for some starting code & inspiration
 */
public class BoardTile extends JPanel
{
    private int xcoord, ycoord;  // coordinates for the tile
    private boolean visited;

    /**
     * Constructor for a single tile instance
     */
    public BoardTile(int xcoord, int ycoord)
    {
        super(); // inheritting all of JPanel's attributes
        this.setSize(50,50);
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.visited = false;
    }

    // @author mhatcher
    // if the decider is even, it chooses black, otherwise white (for 'column+row' will allow a chequerboard effect)
    public void setColor( int decider)
    {
        Color colour = (int) (decider/2.0) == (decider/2.0) ? Color.black : Color.white;
        this.setBackground( colour);
    }

    // if the square is black it becomes white, and vice-versa
    public void colorYellow()
    {
        this.setBackground(Color.yellow);
    }

    public void colorBlue()
    {
        this.setBackground(Color.blue);
    }
    
    public void colorize(Color paint){
        this.setBackground(paint);
    }

    // simple setters and getters
    public void setXcoord(int value)    { xcoord = value; }

    public void setYcoord(int value)    { ycoord = value; }

    public int getXcoord()              { return xcoord; }

    public int getYcoord()              { return ycoord; }

    public Color getColor()             { return this.getBackground();}

    public boolean isVisited()          { return visited; }

    public void setVisited()            { this.visited = true; }
}
