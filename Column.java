public class Column {

	private int colHeight;  //user input for height of column
	private int numDisks;  //number of disk in the column
	private char[] diskArray; //array column
	private int location = 0; //index for placing a disk

	public Column(int height) {
		diskArray = new char[height]; //creates an array of size height

		for (int i = 0; i < height; i++) { //fill the array with '.' as empty spots
			this.diskArray[i] = '.';
		}

		this.colHeight = height; //sets colHeight to user input for row
		this.numDisks = 0; 

	}

	public void addDisk(char disk) {
		if (isFull()) {

		} else {
			this.diskArray[location] = disk;
			this.location++; //move onto next location for next disk
			this.numDisks++;

		}
	}

	public char getDiskAt(int row) {
		return this.diskArray[row];

	}

	public boolean isFull() {
		for (int i = 0; i < colHeight; i++) {
			if (this.diskArray[i] == '.') {
				return false;
			} else {
			}
		}

		return true;
	}

}
