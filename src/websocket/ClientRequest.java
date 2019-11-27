package websocket;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import Observer.PanelUpdater;

public class ClientRequest {
	
	private String uri;
	static Session session;
	static PanelUpdater panelUpdater;

	public void startWebsocket(String api_key, String api_secret, PanelUpdater panelUpdater, String streamurl) {
		Authentication authentication = new Authentication();
		ClientRequest.panelUpdater = panelUpdater;
		this.uri = streamurl;
	
	try {		
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.connectToServer(BybitWebsocket.class, URI.create(uri));
		session.getBasicRemote().sendText("{\"op\":\"ping\"}");
		session.getBasicRemote().sendText(authentication.getAuthMessage(api_key, api_secret));
        session.getBasicRemote().sendText(authentication.subscribe("subscribe", "instrument_info.100ms.BTCUSD"));
//      session.getBasicRemote().sendText(subscribe("subscribe", "position"));
        java.io.BufferedReader r=new  java.io.BufferedReader(new java.io.InputStreamReader( System.in));
        while(true){            	
            String line=r.readLine();
            if(line.equals("quit")) break;
            session.getBasicRemote().sendText(line);
        }

		
	} catch (Exception e) {
		// TODO: handle exception
	}

	}
	
}
