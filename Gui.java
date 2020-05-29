import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Gui extends JFrame
{
	ConnectK connectK;
	private final static int SLOT_WIDTH = 34;
	private final static int DOT_DIAMETER = 10;
	private final static int DISK_DIAMETER = 24;
	private final static int BORDER = 10;

	/**
	 * GUI components
	 */
	private JLabel pTurnLabel;
	private JLabel statusLabel;
	private Canvas canvas;

	/**
	 * Initializes the GUI
	 * @param connectK The ConnectK object that this GUI will represent.
	 */
	@SuppressWarnings("static-access")
	public void init(ConnectK connectK)
	{
		this.connectK = connectK;
		GameGrid game = connectK.getGameGrid();
		if ( null == game ) {
			System.out.println("getGameGrid in ConnectK returned null. "+
					"It must return a properly initialized GameGrid to continue. ");
		} 

		String title = "ConnectK K="+game.getK();

		// RESET UTIL DEFAULT NAMES TO CURRENT GAME'S NAMES
		Util.DEFAULT_PLAYER[0] = game.getPlayerName(1);
		Util.DEFAULT_PLAYER[1] = game.getPlayerName(2);
		JPanel mainPanel = new JPanel();

		int cHeight = game.getNumRows() * SLOT_WIDTH + BORDER;
		int	cWidth = game.getNumColumns() * SLOT_WIDTH + BORDER;

		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(cWidth, cHeight + 50));
		canvas.setBorder(BorderFactory.createRaisedBevelBorder());

		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(canvas, BorderLayout.CENTER);

		JLabel directions = new JLabel("Click on a column to place disk.");
		JPanel north = new JPanel();
		north.add(directions);
		north.setPreferredSize(new Dimension(cWidth, 30));

		mainPanel.add(north, BorderLayout.NORTH);
		mainPanel.setBorder(new TitledBorder(title));

		pTurnLabel = new JLabel();
		statusLabel = new JLabel();

		JPanel lowerPanel = new JPanel();

		GridLayout gLayout = new GridLayout(2,1);
		gLayout.setVgap(5);
		lowerPanel.setLayout(gLayout);
		lowerPanel.add(statusLabel);
		lowerPanel.add(pTurnLabel);

		mainPanel.add(lowerPanel, BorderLayout.SOUTH);

		this.add(mainPanel);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		if (cWidth < 300)
		{
			int currW = (int) mainPanel.getPreferredSize().getWidth();
			int currH = (int) mainPanel.getPreferredSize().getHeight();
			mainPanel.setPreferredSize(new Dimension(currW + 150, currH));
		}
		this.setResizable(false);
		this.pack();
		this.setVisible(true);

	}

	/**
	 * Update the label that specifies the next player's turn
	 */
	public void updateTurnLabel()
	{
		Player currPlayer = connectK.getGameGrid().getCurrentPlayer();
		pTurnLabel.setText( currPlayer.getName() + "'s turn.");
	}

	/**
	 * This method re-draws the grid on the GUI 
	 */
	public void updateCanvas()
	{
		canvas.repaint();
	}

	/**
	 * Encapsulates the component used to draw the grid to the GUI as well as
	 * for listening to mouse clicks within the component.
	 * 
	 * @author matthewb - matthewb@cs.wisc.edu
	 *
	 */
	private class Canvas extends JComponent implements MouseListener
	{
		/**
		 * Constructor
		 */
		public Canvas()
		{
			this.addMouseListener(this);
		}

		/**
		 * Paints the graphics to this component
		 */
		public void paintComponent(Graphics g)
		{			
			super.paintComponent(g);
			this.draw(g);
		}	

		/**
		 * Draws the game grid
		 * 
		 * @param g the graphics object used to create the drawing
		 */
		public void draw(Graphics g)
		{		
			try
			{

				g.setColor(Color.BLUE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				GameGrid game = connectK.getGameGrid();
				if ( game == null ) {
					System.err.println("There was an error in your code! Check " +
							"that your ConnectK.getGameGrid() returns a valid " +
							"GameGrid instance that is not null. " );
				}

				int x = BORDER;
				Column[] columns = game.getColumns();
				if ( columns == null ) {
					System.err.println("There was an error in your code! Check " +
							"that your GameGrid getColumns() is not null.");
				}

				for (int ci=0; ci < columns.length; ci++ )
				{				
					Column col = columns[ci];
					if ( col == null ) {
						System.err.println("There was an error in your code! Check " +
								"that none of your Columns are null.");
					}
					int y = BORDER;
					for (int ri = game.getNumRows() - 1; ri >=0 ; ri--)
					{
						char c = col.getDiskAt(ri);
						switch(c)
						{
						case '.':
							g.setColor(Color.lightGray);
							g.fillOval(x, y, DOT_DIAMETER, DOT_DIAMETER);
							break;
						case 'X':  // player 1 is 'X'
							g.setColor(Color.YELLOW);
							g.fillOval(x, y, DISK_DIAMETER, DISK_DIAMETER);
							break;
						case 'O':
							g.setColor(Color.RED);
							g.fillOval(x, y, DISK_DIAMETER, DISK_DIAMETER);
							break;
						default:
							throw new RuntimeException(
						"Unknown disk character '"+c+"' found at row "+ri+" of column "+ci+".");
						}
						y += SLOT_WIDTH;
					}
					x += SLOT_WIDTH;

				}
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				System.err.println("The GUI tried to access a disk within" +
						" your GameGrid's numRows and numColumns, but this " +
						"resulted in an ArrayIdexOutOfBounds " +
						"Exception.");
				e.printStackTrace();
			}

		}


		/**
		 * This method is called when a the mouse is clicked within the Canvas
		 * panel of the GUI.  This method determines the column that was
		 * clicked and calls the ConnectK instance's "placeDisk" method passing
		 * it the column of the disk.
		 */
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			try
			{
				int totalCols = connectK.getGameGrid().getNumColumns();

				int col = e.getX()  / SLOT_WIDTH;	
				if (col < totalCols)
				{
					String result = connectK.placeDisk(col);
					statusLabel.setText(result);
				}
			}
			catch(NullPointerException x)
			{
				System.err.println("There was an error in your code!, check " +
						"that the GameGrid instance and all columns are " +
						"not null");
				x.printStackTrace();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
	}
}