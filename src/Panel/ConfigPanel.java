package Panel;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfigPanel extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DIRECTORY = System.getProperty("user.dir");

	public JFrame createPanel(JFrame jFrame) {
	    JPanel configPanel = new JPanel();
	    
	    Container cont = getContentPane();
	    cont.setLayout(new GridBagLayout());
	    add(configPanel);
	    
	    
	    configPanel.setBackground(Color.white);
	    JLabel configFile = new JLabel("  "+DIRECTORY+"\\bybitconfig.txt");
	    JLabel errorMessage = new JLabel("  Check config file, apiKey or apiSecret is not Set");
	    errorMessage.setForeground(Color.red);
	    
	    GroupLayout configPanelLayout = new GroupLayout(configPanel);
	    configPanel.setLayout(configPanelLayout);

	    
		configPanelLayout.setHorizontalGroup(configPanelLayout.createSequentialGroup()
				.addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(configPanelLayout.createSequentialGroup()
								.addComponent(errorMessage))
						.addGroup(configPanelLayout.createSequentialGroup()
								.addComponent(configFile))
			));
		
		configPanelLayout.setVerticalGroup(configPanelLayout.createSequentialGroup()
				.addGroup(configPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(errorMessage))
				.addGroup(configPanelLayout.createSequentialGroup()
						.addComponent(configFile))
					
		);

		jFrame.add(configPanel);
	return jFrame;
	}
	
}
