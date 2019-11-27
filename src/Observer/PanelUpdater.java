package Observer;

import java.util.Observable;

public class PanelUpdater extends Observable{

	  private String currentPrice;
  
	   public void notifyAllObservers(){
		   setChanged();
		   notifyObservers(currentPrice);
	   }

	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
}
