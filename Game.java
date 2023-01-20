import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 *  The main window of the gui.
 *  Notice that it extends JFrame - so we can add our own components.
 *  Notice that it implements ActionListener - so we can handle user input.
 *  This version also implements MouseListener to show equivalent functionality (compare with the other demo).
 *  @author mhatcher
 */
public class Game extends JFrame implements ActionListener, MouseListener
{
    // Declaring instance variables
    private JPanel topPanel, gamePanel;
    private JLabel infoLabel;
    private JButton resetButton;
    private GridSquare [][] gridSquares;
    private int rows,columns, selX, selY, moveCount;
    private boolean firsttime;

    /*
     *  constructor method takes as input how many rows and columns of gridsquares to create
     *  it then creates the panels, their subcomponents and puts them all together in the main frame
     *  it makes sure that action listeners are added to selectable items
     *  it makes sure that the gui will be visible
     */
    public Game(int rows, int columns)
    {
        selX = 0;
        selY = 0;
        firsttime = true;
        this.rows = rows;
        this.columns = columns;
        this.setSize(600,600);

        // first create the panels
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(rows, columns));
        gamePanel.setSize(500,500);

        // then create the components for each panel and add them to it

        // for the top panel:
        infoLabel = new JLabel("Sir Lancelot, visit every square once!");
        resetButton = new JButton("New Game");
        resetButton.addActionListener(this);            // IMPORTANT! Without this, clicking the square does nothing.

        topPanel.add(infoLabel, BorderLayout.WEST);
        topPanel.add(resetButton, BorderLayout.EAST);

        // for the bottom panel:    
        // create the squares and add them to the grid
        gridSquares = new GridSquare[rows][columns];
        for ( int x = 0; x < columns; x ++)
        {
            for ( int y = 0; y < rows; y ++)
            {
                gridSquares[x][y] = new GridSquare(x, y);
                gridSquares[x][y].setSize(20, 20);
                gridSquares[x][y].setColor(x + y);

                gridSquares[x][y].addMouseListener(this);        // AGAIN, don't forget this line!

                gamePanel.add(gridSquares[x][y]);
            }
        }

        // now add the top and bottom panels to the main frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(gamePanel, BorderLayout.CENTER);        // needs to be center or will draw too small

        // housekeeping : behaviour
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /*
     *  handles actions performed in the gui
     *  this method must be present to correctly implement the ActionListener interface
     */
    public void actionPerformed(ActionEvent aevt)
    {
        // get the object that was selected in the gui
        Object selected = aevt.getSource();

        // if resetting the squares' colours is requested then do so
        if ( selected.equals(resetButton) )
        {
            Launcher.main(new String[1]);
            // selX = 0;
            // selY = 0;
            // firsttime = true;
            // moveCount = 0;
            // infoLabel.setText("Sir Lancelot, visit every square once!");
            // for ( int x = 0; x < columns; x ++)
            // {
            // for ( int y = 0; y < rows; y ++)
            // {
            // gridSquares[x][y].setColor(x + y);
            // }
            // }
            System.out.println("Game reset!");
            setVisible(false);
            dispose();
        }
    }

    // Mouse Listener events
    public void mouseClicked(MouseEvent mevt)
    {
        Object selected = mevt.getSource();
        if (selected instanceof GridSquare)
        {
            GridSquare square = (GridSquare) selected;
            // coloring previously visited squares BLUE
            if(!firsttime && (selX!=0 || selY!=0)){
                gridSquares[selX][selY].colorBlue();
            } else if(!firsttime && selX==0 && selY==0){
                gridSquares[selX][selY].colorBlue();
            }
            // visiting new square
            if(!square.isVisited()){
                if(
                (square.getXcoord() != selX - 2 &&
                square.getYcoord() != selY - 1) ||
                (square.getXcoord() != selX + 2 &&
                square.getYcoord() != selY + 1)
                ){}
                square.colorYellow();
                square.setVisited();
                System.out.println("Visited "+square.getXcoord()+","+square.getYcoord()+"!");
                moveCount += 1;
            }

            selX = square.getXcoord();
            selY = square.getYcoord();
            infoLabel.setText("Moves made: "+moveCount);
            firsttime = false;
        }
        if(moveCount>=25){
            infoLabel.setText("You did it!");
        }
    }

    // not used but must be present to fulfil MouseListener contract
    public void mouseEntered(MouseEvent arg0){}

    public void mouseExited(MouseEvent arg0) {}

    public void mousePressed(MouseEvent arg0) {}

    public void mouseReleased(MouseEvent arg0) {}
}
