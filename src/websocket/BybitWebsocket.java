package websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.json.JSONArray;
import org.json.JSONObject;


@ClientEndpoint
public class BybitWebsocket {

    @OnOpen
    public void onOpen(Session session) {
//        System.out.println("Connected to endpoint: " + session.getBasicRemote());
        try {
        	ClientRequest.session=session;
//            System.out.println(ClientRequest.session);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void processMessage(String message) {
    	if(calculatePrice(message) != null) {
        	ClientRequest.panelUpdater.setCurrentPrice(calculatePrice(message));
        	ClientRequest.panelUpdater.notifyAllObservers();    		
    		
    	}
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }
    
	private String calculatePrice(String message) {
		
		JSONObject jsonDataObjects = new JSONObject(message.toString());
		JSONObject data = null;
		JSONArray dataArray = null;

		if (jsonDataObjects != null && jsonDataObjects.has("data")) {
			data = jsonDataObjects.getJSONObject("data");
		}

		if (data != null && data.has("update")) {
			dataArray = data.getJSONArray("update");
		}
		
		if(dataArray != null && dataArray.getJSONObject(0).has("mark_price_e4")) {
			return (String) dataArray.getJSONObject(0).get("mark_price_e4").toString();
		}
		return null;
	}
}
