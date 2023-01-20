import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Launches a minigame of Knight's Tour, using Java Swing GUI
 *
 * @author Dewan Mukto (dmimukto)
 * @student_id 202004321
 * @version 2023 Jan 16
 * @attribution Mark Hatcher (mhatcher) - for some starting code & inspiration
 */
public class Game extends JFrame implements ActionListener, MouseListener
{
    // Declaring instance variables
    private JPanel topPanel, gamePanel;
    private JLabel infoLabel;
    private JButton resetButton;
    private BoardTile [][] gridTiles;
    private int rows,columns, selX, selY, moveCount;
    private boolean firsttime, invalidMove;
    private Border yellowBorder;

    /**
     * Constructor for the Game
     */
    public Game()
    {
        selX = 0;
        selY = 0;
        firsttime = true;
        this.invalidMove = false;
        this.rows = 5;
        this.columns = 5;
        this.setSize(600,600);

        // Creating 2 main zones for the window
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, columns));
        gamePanel.setSize(500,500);

        yellowBorder = BorderFactory.createLineBorder(Color.yellow);

        // Creating the menu bar UI components
        infoLabel = new JLabel("Sir Lancelot, visit every square once!");
        resetButton = new JButton("New Game");
        resetButton.addActionListener(this);
        // Adding the UI components with the menu bar
        topPanel.add(infoLabel, BorderLayout.WEST);
        topPanel.add(resetButton, BorderLayout.EAST);

        // Creating the main 'game' zone
        // Initializing the 5x5 chess-like grid
        gridTiles = new BoardTile[rows][columns];
        for ( int x = 0; x < columns; x ++)
        {
            for ( int y = 0; y < rows; y ++)
            {
                gridTiles[x][y] = new BoardTile(x, y);
                gridTiles[x][y].setSize(20, 20);
                gridTiles[x][y].setColor(x + y);
                gridTiles[x][y].setOpaque(true);
                gridTiles[x][y].setBorder(yellowBorder);
                gridTiles[x][y].addMouseListener(this);
                // adding components to the playable zone
                gamePanel.add(gridTiles[x][y]);
            }
        }

        // adding everything to the main window
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        // wrapping up
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Action handler for the game
     */
    public void actionPerformed(ActionEvent aevt)
    {
        // get the object that was selected in the gui
        Object selected = aevt.getSource();

        // if resetting the squares' colours is requested then do so
        if ( selected.equals(resetButton) )
        {
            Launcher.main(new String[1]);
            System.out.println("Game reset!");
            setVisible(false);
            dispose();
        }
    }

    /**
     * Mouse Action handler for the game
     */
    public void mouseClicked(MouseEvent mevt)
    {
        Object selected = mevt.getSource();
        if (selected instanceof BoardTile)
        {
            BoardTile square = (BoardTile) selected;
            // coloring previously visited squares BLUE
            if(!firsttime && (selX!=0 || selY!=0)){
                gridTiles[selX][selY].colorBlue();
            } else if(!firsttime && selX==0 && selY==0 && !invalidMove){
                gridTiles[selX][selY].colorBlue();
            } else if(invalidMove){
                gridTiles[selX][selY].colorYellow();
            }
            // visiting new square
            if(!square.isVisited() && !firsttime){
                if(
                // Validating the selection for a KNIGHT piece on a chessboard
                ((square.getXcoord() == selX +1 && square.getYcoord() == selY -2) || (square.getXcoord() == selX +1 && square.getYcoord() == selY +2)) ||
                ((square.getXcoord() == selX -1 && square.getYcoord() == selY -2) || (square.getXcoord() == selX -1 && square.getYcoord() == selY +2)) ||
                ((square.getXcoord() == selX -2 && square.getYcoord() == selY -1) || (square.getXcoord() == selX +2 && square.getYcoord() == selY -1)) ||
                ((square.getXcoord() == selX -2 && square.getYcoord() == selY +1) || (square.getXcoord() == selX +2 && square.getYcoord() == selY +1))
                ){
                    square.colorYellow();
                    square.setVisited();
                    System.out.println("Visited "+square.getXcoord()+","+square.getYcoord()+"!");
                    moveCount += 1;
                    selX = square.getXcoord();
                    selY = square.getYcoord();
                    infoLabel.setText("Moves made: "+moveCount);
                    firsttime = false;
                    gamePanel.setBackground(null);
                    invalidMove = false;
                } else {
                    infoLabel.setText("You cannot go there!");
                    gamePanel.setBackground(Color.red);
                    invalidMove = true;
                }

            } else if(firsttime) {
                square.colorYellow();
                square.setVisited();
                System.out.println("Visited "+square.getXcoord()+","+square.getYcoord()+"!");
                moveCount += 1;
                selX = square.getXcoord();
                selY = square.getYcoord();
                infoLabel.setText("Moves made: "+moveCount);
                firsttime = false;
            }
        }
        if(moveCount>=25){
            infoLabel.setText("You did it!");
            for ( int x = 0; x < columns; x ++)
            {
                for ( int y = 0; y < rows; y ++)
                {
                    gridTiles[x][y].colorize(Color.yellow);
                    gridTiles[x][y].setBorder(null);
                }
            }
            gridTiles[1][1].colorize(Color.black);
            gridTiles[1][3].colorize(Color.black);
            gridTiles[3][0].colorize(Color.black);
            gridTiles[4][1].colorize(Color.black);
            gridTiles[4][2].colorize(Color.black);
            gridTiles[4][3].colorize(Color.black);
            gridTiles[3][4].colorize(Color.black);
            gamePanel.setBackground(Color.green);
        }
    }

    // Dependency satisfying requisites
    public void mouseEntered(MouseEvent arg0){}

    public void mouseExited(MouseEvent arg0) {}

    public void mousePressed(MouseEvent arg0) {}

    public void mouseReleased(MouseEvent arg0) {}
}
