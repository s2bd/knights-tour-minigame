/*
 *  Another simple GUI demonstration using swing.
 *  Here the GridSquare class extends JPanel and
 *  holds its coordinates in the grid as attributes.
 *  @author mhatcher
 */
public class Launcher
{
    public static void main(String[] args)
    {
        // create a new GUI window
        Game game = new Game(5, 5);
    }
}
