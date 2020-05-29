import java.awt.Dimension;

public class Util 
{
	public static String[] DEFAULT_PLAYER = {"Justin Bieber", "Arnold Schwarzenegger"};
	public static final char[] PLAYER_CHAR = { 'X', 'O' };
	public static final int MAX_SIZE = 30;
	public static final int MIN_SIZE = 1;
	public static char EMPTY_SLOT = '.';
	private static Gui gui;

	public static void updateGUI()
	{
		gui.updateCanvas();
		gui.updateTurnLabel();
	}	

	public static void destroyGUI()
	{
		if (Util.gui != null) 
		{
			Util.gui.dispose();
			gui = null;
		}
	}

	public static void buildGUI(ConnectK k)
	{
		gui = new Gui();
		gui.init(k);
    	updateGUI();
	}

	public static void showNewGameDialog(String message)
	{
		StartupDialog sDialog = new StartupDialog(message);
		sDialog.setPreferredSize(new Dimension(350, 250));
		sDialog.pack();
		sDialog.setVisible(true);		
	}
}
