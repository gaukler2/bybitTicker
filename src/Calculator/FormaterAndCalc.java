package Calculator;

import java.text.DecimalFormat;

public class FormaterAndCalc {
	
	private double maxUpPriceCalcTemp = 0.0;
	private double maxDownPriceCalcTemp = 0.0;
	private DecimalFormat df = new DecimalFormat( "####.##" );

	public String formatToDisplay(String number) {
		return String.valueOf(stringToDouble(number));
	}
	
	public double stringToDouble(String number) {
		return Double.valueOf(number) / 10000;
	}
	
	// True = up | false = down
	public boolean setPriceColor(double lastPrice, double newPrice) {

		if (lastPrice < newPrice) {
			return true;
		}		
		return false;
	}
	
	public double calculateMaxUp(double watchPriceCalc, double newPriceCalc, double maxUpPriceCalc) {
		maxUpPriceCalcTemp = 0.0;
		
		if(newPriceCalc > watchPriceCalc) {
			maxUpPriceCalcTemp = newPriceCalc - watchPriceCalc;
		} 
		
		if(maxUpPriceCalcTemp > maxUpPriceCalc) {
			return Double.valueOf(df.format(maxUpPriceCalcTemp));
		}
		return maxUpPriceCalc;
	}
	
	public double calculateMaxDown(double watchPriceCalc, double newPriceCalc, double maxDownPriceCalc) {
		maxDownPriceCalcTemp = 0.0;
		
		if(watchPriceCalc > newPriceCalc) {
			maxDownPriceCalcTemp = watchPriceCalc - newPriceCalc;
		} 
		
		if(maxDownPriceCalcTemp > maxDownPriceCalc) {
			return Double.valueOf(df.format(maxDownPriceCalcTemp));
		}
		return maxDownPriceCalc;
	}
	
	public double compareWatchMarket(double newPriceCalc, double watchPriceCalc) {		
		return Double.valueOf(df.format(newPriceCalc - watchPriceCalc));
	}
	
	public double formatTo2Dec(double number) {
		return Double.valueOf(df.format(number));
		
	}
	
	public double round(double value) {
		      double d = Math.pow(10, 0);
		      return Math.round(value * d) / d;
		   }
	
}
