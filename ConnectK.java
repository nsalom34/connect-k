public class ConnectK
{
	private String p1Name;
	private String p2Name;
	private int rows; //represents number of rows in a column
	private int cols; //represents number of column objects created
	private int k; //number required to win by user
	private GameGrid gg; //initialize variable as an object of type GameGrid
	private ConnectK game; //initialize variable as an object of ConnectK

	public ConnectK(String p1Name, String p2Name, int rows, int cols, int K) {
		
		this.p1Name = p1Name;
		this.p2Name = p2Name;
		this.cols = cols;
		this.rows = rows;
		this.k = K;
		this.gg = new GameGrid(p1Name, p2Name, rows, cols, K);
	}

	public GameGrid getGameGrid() {
		return gg;
	}

	public static void launchGameWithParameters(String p1Name, String p2Name,
			int rows, int cols, int K) {
	
		Util.destroyGUI();
		ConnectK game = new ConnectK(p1Name, p2Name, rows, cols, K);
		Util.buildGUI(game);
	}

	public static String validateSetupInput(int rows, int cols, int connect) {
		if (rows > Util.MAX_SIZE) {
			return "Number of rows must be less than or " + "equal to "
					+ Util.MAX_SIZE;
		}

		else if (rows < Util.MIN_SIZE) {
			return "Number of rows must be greater than or " + "equal to "
					+ Util.MIN_SIZE;
		}
		
		if (cols > Util.MAX_SIZE) {
			return "Number of columns must be less than or " + "equal to "
					+ Util.MAX_SIZE;
		}

		else if (cols < Util.MIN_SIZE) {
			return "Number of columns must be " + "greater than or "
					+ "equal to " + Util.MIN_SIZE;
		}

		int min = cols < rows ? cols : rows;

		if (connect < Util.MIN_SIZE) {
			return "Connect length must be greater than or " + "equal to "
					+ Util.MIN_SIZE;
		}

		else if (connect > min) {
			return "Connect length must be less than or " + "equal to " + min;
		}
		return null;
	}
	
	public String placeDisk(int column) {
		boolean isWinner;

		boolean isFull = gg.getColumns()[column].isFull();
		if (isFull) {
			return "This column is full! Try again.";
		}
		else {
			gg.addDiskToColumn(column, gg.getCurrentPlayer().getDisk());
			isWinner = gg.isWinner(gg.getCurrentPlayer());

		}

		if (isWinner) {
			Util.updateGUI();
			Util.showNewGameDialog(gg.getCurrentPlayer().getName() + " won!");

			return gg.getCurrentPlayer().getName() + " won!";

		}
		
		else if (gg.isFull()) {
			Util.updateGUI();
			Util.showNewGameDialog("The grid is full. Tie game!");

			return "The grid is full. Tie game!";
		}

		gg.advanceTurn();
		Util.updateGUI();
		return "Disk successfully placed in column " + column;

	}

	public static void main(String[] args) {
		Util.showNewGameDialog("Welcome to ConnectK!");
	}

}
