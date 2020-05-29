import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class StartupDialog extends JDialog implements ActionListener
{
		private JTextField t1;
		private JTextField t2;
		private JTextField t3;
		private JTextField t4;
		private JTextField t5;

		private JLabel status;
	
		@SuppressWarnings({ "static-access" })
		public StartupDialog(String title)
		{	
			t1 = new JTextField();
			t2 = new JTextField();
			t3 = new JTextField();
			t4 = new JTextField();
			t5 = new JTextField();
			
			t1.setText(Util.DEFAULT_PLAYER[0]);
			t2.setText(Util.DEFAULT_PLAYER[1]);
			
			status = new JLabel("");
			status.setForeground(Color.RED);
			
			JPanel center = new JPanel();
			center.setLayout(new GridLayout(5,2));
			center.add( new JLabel("   First player's name: ") );
			center.add(t1);
			center.add( new JLabel("   Second player's name: ") );
			center.add(t2);
			center.add( new JLabel("   Number of rows: ") );
			center.add(t3);
			center.add( new JLabel("   Number of columns: ") );
			center.add(t4);	
			center.add( new JLabel("   Connect to win: ") );
			center.add(t5);		
			
			JPanel south = new JPanel();
			south.setLayout(new GridLayout(2,1));
			JButton submitBtn = new JButton("Submit");
			submitBtn.setPreferredSize(new Dimension(100, 40));
			submitBtn.addActionListener(this);
			south.setPreferredSize(new Dimension(100, 50));
			south.add(submitBtn);
			south.add(status);
			
			JPanel main = new JPanel();
			main.setLayout(new BorderLayout());
			main.add(center, BorderLayout.CENTER);
			main.add(south, BorderLayout.SOUTH);
			main.setBorder(new TitledBorder("Next Game Settings"));
			
			this.setTitle(title);
			this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
			this.add(main);
			this.setModal(true);
			this.setSize(130,180);
			this.setLocation(300, 300);
		}
		
		public void resetTitle(String title)
		{
			this.setTitle(title);
		}

		@Override
		public void actionPerformed(ActionEvent e) 
		{			
			try
			{
				String p1Name = t1.getText();
				String p2Name = t2.getText();
				
				int rows = Integer.parseInt( t3.getText() );
				int cols = Integer.parseInt( t4.getText() );
				int connect = Integer.parseInt( t5.getText() );
				
				String test = ConnectK.validateSetupInput(rows, cols, connect);
				
				if (test != null)
				{
					status.setText(test);

					// Change the default names for future games
					Util.DEFAULT_PLAYER[0] = p1Name;
					Util.DEFAULT_PLAYER[1] = p1Name;
					
				}
				else
				{
					// Destroy this modal window
					this.dispose();
				
					// Entry into student's code
					ConnectK.launchGameWithParameters(p1Name, 
													  p2Name, 
													  rows, 
													  cols, 
													  connect);
				}
			}
			catch(NumberFormatException x)
			{
				status.setText("Input must be an integer.");
			}
		}
}
