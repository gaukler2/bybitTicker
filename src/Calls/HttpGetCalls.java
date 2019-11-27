package Calls;

import websocket.Authentication;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;



public class HttpGetCalls {

	private String apiurl; 
	
	public PositionObject requestPosition(String apiKey, String apiSecret, String apiurl) {
		Authentication authentication = new Authentication();
		JSONObject result = new JSONObject();
		PositionObject currPositionObject = new PositionObject();
		JSONArray dataArray = null;
		this.apiurl = apiurl;
		
		String timestap = String.valueOf(System.currentTimeMillis()+1000);
		String para = "api_key="+apiKey+"&timestamp="+timestap;

		String hash = authentication.sha256_HMAC(para, apiSecret);
		String reuqest = "/position/list?api_key="+apiKey+"&timestamp="+timestap+"&sign="+hash;
		
		try {
			result = httpCall(reuqest);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		if (result != null && result.has("result")) {
			dataArray = result.getJSONArray("result");
		}

		if(dataArray != null && dataArray.getJSONObject(0).has("position_value") && dataArray.getJSONObject(0).get("symbol").toString().equals("BTCUSD")) {
			currPositionObject.setPosition_value(Double.valueOf(dataArray.getJSONObject(0).get("position_value").toString()));
		}
		
		if(dataArray != null && dataArray.getJSONObject(0).has("liq_price") && dataArray.getJSONObject(0).get("symbol").toString().equals("BTCUSD")) {
			currPositionObject.setLiq_price(Double.valueOf(dataArray.getJSONObject(0).get("liq_price").toString()));
		}
		
		if(dataArray != null && dataArray.getJSONObject(0).has("entry_price") && dataArray.getJSONObject(0).get("symbol").toString().equals("BTCUSD")) {
			currPositionObject.setEntry_price(Double.valueOf(dataArray.getJSONObject(0).get("entry_price").toString()));
		}
		
		if(dataArray != null && dataArray.getJSONObject(0).has("side") && dataArray.getJSONObject(0).get("symbol").toString().equals("BTCUSD")) {
			currPositionObject.setTradeDirection(dataArray.getJSONObject(0).get("side").toString());
		}
		if(dataArray != null && dataArray.getJSONObject(0).has("unrealised_pnl") && dataArray.getJSONObject(0).get("symbol").toString().equals("BTCUSD")) {
			currPositionObject.setUnrealised_pnl(Double.valueOf(dataArray.getJSONObject(0).get("unrealised_pnl").toString()));
		}
		if(dataArray != null && dataArray.getJSONObject(0).has("leverage") && dataArray.getJSONObject(0).get("symbol").toString().equals("BTCUSD")) {
			currPositionObject.setLeverage(Integer.parseInt(dataArray.getJSONObject(0).get("leverage").toString()));
		}
		if(dataArray != null && dataArray.getJSONObject(0).has("position_margin") && dataArray.getJSONObject(0).get("symbol").toString().equals("BTCUSD")) {
			currPositionObject.setPositionMargin((Double.valueOf(dataArray.getJSONObject(0).get("position_margin").toString())));
		}

		return currPositionObject;
	}
	
	public JSONObject httpCall(String requestAction) throws Exception{
			
			final CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpGet request = new HttpGet(apiurl+requestAction);
	        request.addHeader("Content-Type", "application/x-www-form-urlencoded");

	        try (CloseableHttpResponse response = httpClient.execute(request)) {
	            HttpEntity entity = response.getEntity();

	            if (entity != null) {
	                String result = EntityUtils.toString(entity);
	                JSONObject jsonDataObjects = new JSONObject(result);
	                return jsonDataObjects;
	            }
	        }
			return null;
	}
}
