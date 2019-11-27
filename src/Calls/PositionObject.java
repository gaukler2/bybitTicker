package Calls;

public class PositionObject {

	double liq_price;
	double position_value;
	double entry_price;
	String tradeDirection;
	double profitBTC;
	double profitPercent;
	double unrealised_pnl;
	int leverage;
	double positionMargin;
	
	public double getLiq_price() {
		return liq_price;
	}
	public void setLiq_price(double liq_price) {
		this.liq_price = liq_price;
	}
	public double getPosition_value() {
		return position_value;
	}
	public void setPosition_value(double position_value) {
		this.position_value = position_value;
	}
	public double getEntry_price() {
		return entry_price;
	}
	public void setEntry_price(double entry_price) {
		this.entry_price = entry_price;
	}
	public String getTradeDirection() {
		return tradeDirection;
	}
	public void setTradeDirection(String tradeDirection) {
		this.tradeDirection = tradeDirection;
	}
	public double getProfitBTC() {
		return profitBTC;
	}
	public void setProfitBTC(double profitBTC) {
		this.profitBTC = profitBTC;
	}
	public double getProfitPercent() {
		return profitPercent;
	}
	public void setProfitPercent(double profitPercent) {
		this.profitPercent = profitPercent;
	}
	public double getUnrealised_pnl() {
		return unrealised_pnl;
	}
	public void setUnrealised_pnl(double unrealised_pnl) {
		this.unrealised_pnl = unrealised_pnl;
	}
	public int getLeverage() {
		return leverage;
	}
	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}
	public double getPositionMargin() {
		return positionMargin;
	}
	public void setPositionMargin(double positionMargin) {
		this.positionMargin = positionMargin;
	}

	
	
}
