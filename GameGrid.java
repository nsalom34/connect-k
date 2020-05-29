public class GameGrid {
	private int NUM_ROWS;
	private int NUM_COLS;
	private int NUM_TO_WIN;
	private Column[] GRID; //initializing grid to be an array of columns
	private Player PLAYER_1;
	private Player PLAYER_2;
	private boolean PLAYER_1_GOES; //if true=player 1 turn, if false=player 2 turn

	public GameGrid(String player_1_name, 
			String player_2_name, 
			int num_rows, 
			int num_cols, 
			int num_to_win) 
	{

		this.NUM_ROWS = num_rows;
		this.NUM_COLS = num_cols;
		
		GRID = new Column[num_cols];
		
		for (int i = 0; i < num_cols; i++)
			GRID[i] = new Column(num_rows);

		PLAYER_1 = new Player(player_1_name, 'X');
		PLAYER_2 = new Player(player_2_name, 'O');
		
		PLAYER_1_GOES = true;
		
		this.NUM_TO_WIN = num_to_win;

	}

	public Player getCurrentPlayer() {
		return PLAYER_1_GOES ? PLAYER_1 : PLAYER_2;
	}

	public String getPlayerName(int player_num) {
		return player_num == 1 ? PLAYER_1.getName() : PLAYER_2.getName();
	}

	public int getNumColumns() {
		return NUM_COLS;
	}

	public int getNumRows() {
		return NUM_ROWS;
	}

	public Column[] getColumns() {
		return GRID;
	}

	public int getK() {
		return NUM_TO_WIN;
	}

	public boolean isWinner(Player toCheck) {

		char player_disk = toCheck.getDisk();

		// 1 - CHECK FOR A WIN IN ANY COLUMN
		int counter = 0;
		for (int i = 0; i < NUM_COLS; i++) {
			for (int j = NUM_ROWS - 1; j >= 0; j--) {
				if (GRID[i].getDiskAt(j) == player_disk) { 
					counter++;
					if (counter == NUM_TO_WIN)
						return true;
				} else {
					counter = 0;
				}
			}
			counter = 0;
		}
		counter = 0;

		// CHECK FOR A WIN IN ANY ROW

		int count = 0;
		for (int j = NUM_ROWS - 1; j >= 0; j--) {
			for (int i = 0; i < NUM_COLS; i++) {
				if (GRID[i].getDiskAt(j) == player_disk) {
					count++;
					if (count == NUM_TO_WIN)
						return true;
				} else {
					count = 0;
				}
			}
			count = 0;
		}
		count = 0;

		// CHECK FOR A WIN IN DIAGONAL UP FROM ANY LEFT TO RIGHT
		int min = NUM_ROWS > NUM_COLS ? NUM_COLS : NUM_ROWS;
		int countLeft = 0;
		
		for (int i = 0; i < NUM_ROWS; i++) {

			for (int j = 0; j < min; j++) {

				if (j < NUM_COLS && i + j < NUM_ROWS) {

					if (GRID[j].getDiskAt(j + i) == player_disk) {
						countLeft++;
						if (countLeft == NUM_TO_WIN)
							return true;
					} else {
						countLeft = 0;
					}
				} else {
					countLeft = 0;
				}
			}
			countLeft = 0;
		}
		
		int countBottom = 0;
		for (int i = 0; i < NUM_COLS; i++) {

			for (int j = 0; j < min; j++) {

				if (j < NUM_ROWS && i + j < NUM_COLS) {

					if (GRID[i + j].getDiskAt(j) == player_disk) {
						countBottom++;
						if (countBottom == NUM_TO_WIN)
							return true;
					} else {
						countBottom = 0;
					}
				} else {
					countBottom = 0;
				}
			}
			countBottom = 0;
		}
	

		// CHECK FOR A WIN IN DIAGONAL DOWN FROM ANY LEFT TO RIGHT
		int countLeftDown = 0;

		for (int l = 0; l < NUM_ROWS; l++) {
			for (int j = 0; j < min; j++) {
				if (j < NUM_COLS && (NUM_ROWS - 1 - l - j) < NUM_ROWS && (NUM_ROWS - 1 - l - j) >= 0) {
					if (GRID[j].getDiskAt(NUM_ROWS - 1 - l - j) == player_disk) {
						countLeftDown++;
						if (countLeftDown == NUM_TO_WIN)
							return true;
					} else {
						countLeftDown = 0;
					}
				}
			}
			countLeftDown = 0;
		}
		countLeftDown = 0;
		
		// CHECK FOR A WIN IN DIAGONAL DOWN FROM ANY TOP TO BOTTOM
		int countUptoBottom = 0;
		for (int l = 0; l < NUM_COLS; l++) {
			for (int j = 0; j < min; j++) {
				if ((j + l) < NUM_COLS && (NUM_ROWS - 1 - j) < NUM_ROWS && (NUM_ROWS - 1 - j) >= 0) {

					// TEST STATEMENTS
					// System.out.println("Check Up to Bottom");
					// System.out.print((j + l));System.out.println(" "+
					// (row - 1 - j));

					if (GRID[j + l].getDiskAt(NUM_ROWS - 1 - j) == player_disk) {

						countUptoBottom++;

						if (countUptoBottom == NUM_TO_WIN)
							return true;
					} else {
						countUptoBottom = 0;
					}

				}
			}
			countUptoBottom = 0;

		}
		countUptoBottom = 0;
		
		return false;
	}

	public boolean isWon() {
		return isWinner(PLAYER_1) || isWinner(PLAYER_2);
	}

	public boolean isFull() {
		for (int i = 0; i < NUM_COLS; i++) {
			if (!GRID[i].isFull()) { 
				return false;
			}
		}
		return true;
	}


	public void advanceTurn() {
		PLAYER_1_GOES = !PLAYER_1_GOES;
	}

	public boolean addDiskToColumn(int column, char disk) {
		if (GRID[column].isFull())
			return false;

		GRID[column].addDisk(disk);
		return true;
	}


	@Override
	public String toString() {
		String game_grid_stringify = "";

		for (int i = NUM_ROWS - 1; i >= 0; i--) {
			for (int j = 0; j < NUM_COLS; j++) {
				game_grid_stringify += getColumns()[j].getDiskAt(i) + " ";
			}
			game_grid_stringify += "\n";
		}
		for (int k = 0; k < NUM_COLS; k++)
			game_grid_stringify += k + " ";

		return game_grid_stringify;
	}

}

