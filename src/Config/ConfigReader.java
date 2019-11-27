package Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConfigReader {

	private static final String USERDIRECTORY = System.getProperty("user.dir");
	private static final File CONFIGFILE = new File(USERDIRECTORY+"/bybitconfig.txt");
	
	public boolean checkIfConfigFileExist() {
		
		if(CONFIGFILE.exists()) {
			return true;
		} else {
			try {
				FileWriter fileWriter = new FileWriter(CONFIGFILE);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.println("apiKey=");
				printWriter.println("apiSecret=");
				printWriter.println("streamurl=wss://stream.bybit.com/realtime");
				printWriter.println("apiurl=https://api.bybit.com");
				printWriter.close();		
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}		
	}
	
	public boolean checkApiKey() {
		if(readConfig("apiKey").equals("notSet")) {
			return true;
		}
		return false;
	}
	
	public boolean checkApiSecret() {
		if(readConfig("apiSecret").equals("notSet")) {
			return true;
		}
		return false;
	}
	
	private String readConfig(String key) {
		String value = "notSet";
		File file = CONFIGFILE; 
	    Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	    while (sc.hasNextLine()) {
	    	String[] configValues = sc.nextLine().split("=");
	    	if(configValues.length < 2) {
	    		return value;
	    	} else {
	    		if(configValues[0].equals(key)) {
		    		return configValues[1];	    			
	    		}

	    	}
	    }
		return value;
	}
	
	public List<String> getKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(readConfig("apiKey"));
		keys.add(readConfig("apiSecret"));
		keys.add(readConfig("streamurl"));
		keys.add(readConfig("apiurl"));
		
		return keys;
	}
	
}
