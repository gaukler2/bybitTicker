package Run;

import javax.swing.JFrame;


import Config.ConfigReader;
import Observer.PanelUpdater;
import Panel.ConfigPanel;
import Panel.StatusPanel;
import websocket.ClientRequest;

public class run {

	public static void main(String[] args) {
		ConfigReader configReader = new ConfigReader();
		
		if(! configReader.checkIfConfigFileExist() || configReader.checkApiKey() || configReader.checkApiSecret()) {
			ConfigPanel configPanel = new ConfigPanel();
			JFrame configFrame = new JFrame();
			configPanel.createPanel(configFrame);
			configFrame.pack();
			configFrame.setLocation(200,150);
			configFrame.setSize(380, 100);
			configFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			configFrame.setAlwaysOnTop(true);
			configFrame.setVisible(true);
			
		} else {
			ClientRequest clientRequest = new ClientRequest();
			PanelUpdater panelUpdater = new PanelUpdater();
			StatusPanel statusPanel = new StatusPanel();
			JFrame statusPanelFrame = new JFrame();
			

			String apiKey = configReader.getKeys().get(0);
			String apiSecret = configReader.getKeys().get(1);
			String streamurl = configReader.getKeys().get(2);
			String apiurl = configReader.getKeys().get(3);		
			
			panelUpdater.addObserver(statusPanel);
			
			statusPanel.createPanel(statusPanelFrame, apiKey, apiSecret, apiurl);
			statusPanelFrame.pack();
			statusPanelFrame.setLocation(200,150);
			statusPanelFrame.setSize(265, 370);
			statusPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			statusPanelFrame.setAlwaysOnTop(true);
			statusPanelFrame.setVisible(true);

			clientRequest.startWebsocket(apiKey, apiSecret, panelUpdater, streamurl);
		}
	}

}
