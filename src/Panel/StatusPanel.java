package Panel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Calculator.FormaterAndCalc;
import Calls.HttpGetCalls;
import Calls.PositionObject;

public class StatusPanel extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Color DARK_GREEN = new Color(0,153,0);
	private JLabel markPrice = new JLabel("NaN");
	private JLabel maxUpPrice = new JLabel("NaN");
	private JLabel maxDownPrice = new JLabel("NaN");
	private JLabel watchPrice = new JLabel("NaN");
	private JLabel diffPrice = new JLabel("NaN");
	private JLabel alarmView = new JLabel("+-10");
    private JLabel value = new JLabel("0.0");
    private JLabel entryPrice = new JLabel("0.0");
    private JLabel liquidationPrice = new JLabel("0.0");
    private JLabel leverage = new JLabel("0");
    private JLabel profitPercent = new JLabel("0");
    private JLabel profitBTC = new JLabel("0");
    private JLabel positionMargin = new JLabel("0");
	private JTextField newWatchPrice = new JTextField("");
	private JTextField setAlarmValue = new JTextField("");
	private JPanel statusPanel = new JPanel();
	private FormaterAndCalc fr = new FormaterAndCalc();
	private double lastPriceCalc = 0.0;
	private double newPriceCalc = 0.0;
	private double watchPriceCalc = 0.0;
	private double maxUpPriceCalc = 0.0;
	private double maxDownPriceCalc = 0.0;
	private double currentPriceDivCalc =  0.0;
	private double alarm = 10;
	private String apikey;
	private String apiSecret;
	private String apiurl;
	private PositionObject currentPosition = new PositionObject();
	private HttpGetCalls httpGetCalls = new HttpGetCalls();




	@Override
	public void update(Observable o, Object price) {
		updatePanelPrice(price.toString());
	}
	
	public void updatePanelPrice(String price) {
		lastPriceCalc = newPriceCalc;
		newPriceCalc = fr.stringToDouble(price);
				
		if(fr.setPriceColor(lastPriceCalc, newPriceCalc)) {
			markPrice.setForeground(DARK_GREEN);
		} else {
			markPrice.setForeground(Color.red);
		}
		
		if(String.valueOf(watchPriceCalc).equals("0.0")) {
			watchPriceCalc = newPriceCalc;
		}
		
		maxUpPriceCalc = fr.calculateMaxUp(watchPriceCalc, newPriceCalc, maxUpPriceCalc);
		maxDownPriceCalc = fr.calculateMaxDown(watchPriceCalc, newPriceCalc, maxDownPriceCalc);
		currentPriceDivCalc = fr.compareWatchMarket(newPriceCalc, watchPriceCalc);
		
		maxDownPrice.setText(Double.toString(maxDownPriceCalc));
		maxUpPrice.setText(Double.toString(maxUpPriceCalc));
		markPrice.setText(Double.toString(newPriceCalc));
		watchPrice.setText(Double.toString(watchPriceCalc));
		diffPrice.setText(Double.toString(currentPriceDivCalc));
		
		if(currentPriceDivCalc > 0) {
			diffPrice.setForeground(DARK_GREEN);
		} else {
			diffPrice.setForeground(Color.red);
		}
		
		if(maxUpPriceCalc > alarm || maxDownPriceCalc > alarm) {
			statusPanel.setBackground(Color.yellow);
		}
		updateProfitPercent();
		statusPanel.repaint();
	}
	
	public void updatePanelWatchPrice() {
		if(! newWatchPrice.getText().equals("")) {
			watchPriceCalc = Double.valueOf(newWatchPrice.getText());
		} else {
			watchPriceCalc = newPriceCalc;
		}
		maxUpPriceCalc = 0.0;
		maxDownPriceCalc = 0.0;
		maxUpPriceCalc = fr.calculateMaxUp(watchPriceCalc, newPriceCalc, maxUpPriceCalc);
		maxDownPriceCalc = fr.calculateMaxDown(watchPriceCalc, newPriceCalc, maxDownPriceCalc);
		currentPriceDivCalc = fr.compareWatchMarket(newPriceCalc, watchPriceCalc);
		
		maxDownPrice.setText(Double.toString(maxDownPriceCalc));
		maxUpPrice.setText(Double.toString(maxUpPriceCalc));
		markPrice.setText(Double.toString(newPriceCalc));
		watchPrice.setText(Double.toString(watchPriceCalc));
		diffPrice.setText(Double.toString(currentPriceDivCalc));
		if(currentPriceDivCalc > 0) {
			diffPrice.setForeground(DARK_GREEN);
		} else {
			diffPrice.setForeground(Color.red);
		}
		
		statusPanel.repaint();		
	}
	
	public void updateAlarm() {
		alarm = Double.valueOf(setAlarmValue.getText());
		alarmView.setText("+-"+Double.toString(alarm));
		statusPanel.repaint();	
	}
	
    public JFrame createPanel(JFrame jFrame, String apiKey, String apiSecret, String apiurl) {
	    

    	this.apikey = apiKey;
    	this.apiSecret = apiSecret;
    	this.apiurl = apiurl;
    	
	    statusPanel.setBackground(Color.white);
   
	    Container cont = getContentPane();
	    cont.setLayout(new GridBagLayout());
	    add(statusPanel);
	    
	    // Labes, Buttons, etc erstellen
	    JLabel markPriceLabel = new JLabel("Mark Price:");
	    JLabel maxUpPriceLabel = new JLabel("Max Up:");
	    JLabel maxDownPriceLabel = new JLabel("Max Down:");
	    JLabel watchPriceLabel = new JLabel("Watch Price:");
	    JLabel alarmLabel = new JLabel("Alarm:");
	    JLabel userAction = new JLabel("Wait until Market Price is set");
	    JLabel unterstrich = new JLabel("-----------------------------------------------------------------------------------------------------------------");
	    JLabel unterstrich2 = new JLabel("-----------------------------------------------------------------------------------------------------------------");
	    JLabel unterstrich3 = new JLabel("-----------------------------------------------------------------------------------------------------------------");
	    JLabel bindeStrich = new JLabel(" ");
	    JLabel entryPriceLabel = new JLabel("Entry Pr.");
	    JLabel liquidationPriceLabel = new JLabel("Liq. Pr.");
	    JLabel valueLabel = new JLabel("Value:");
	    JLabel leverageLabel = new JLabel("Leverage:");
	    JLabel profitPercentLabel = new JLabel("Percent:");
	    JLabel profitBTCLabel = new JLabel("Profit:");
	    JLabel positionMarginLabel = new JLabel("Margin:");
	    JButton resetWatchPrice = new JButton();
	    JButton setAlarm = new JButton();


	    
	    // Labels, Buttons, etc Design
	    Font font5 = new Font("Verdana", Font.BOLD, 5);
	    Font font12 = new Font("Verdana", Font.BOLD, 12);
	    Font font14 = new Font("Verdana", Font.BOLD, 14);
	    
	    markPrice.setFont(new Font("Verdana", Font.BOLD, 18));
	    markPriceLabel.setFont(new Font("Verdana", Font.BOLD, 18));
	    userAction.setFont(new Font("Verdana", Font.BOLD, 10));
	    
	    maxUpPriceLabel.setFont(font14);
	    maxDownPriceLabel.setFont(font14);
	    watchPriceLabel.setFont(font14);
	    bindeStrich.setFont(font14);
	    unterstrich.setFont(font5);
	    unterstrich2.setFont(font5);
	    unterstrich3.setFont(font5);
	    alarmLabel.setFont(font14);
	    watchPrice.setFont(font12);
	    maxUpPrice.setFont(font12);
	    maxDownPrice.setFont(font12);
	    diffPrice.setFont(font12);
	    newWatchPrice.setFont(font12);
	    setAlarmValue.setFont(font12);
	    alarmView.setFont(font12);
	    entryPriceLabel.setFont(font12);
	    liquidationPriceLabel.setFont(font12);
	    valueLabel.setFont(font12);
	    leverageLabel.setFont(font12);
	    profitPercentLabel.setFont(font12);
	    profitBTCLabel.setFont(font12);
	    positionMarginLabel.setFont(font12);
	    
	    liquidationPrice.setForeground(Color.MAGENTA);
	    entryPrice.setForeground(Color.MAGENTA);
	    value.setForeground(Color.MAGENTA);
	    leverage.setForeground(Color.MAGENTA);
	    profitPercent.setForeground(Color.MAGENTA);
	    profitBTC.setForeground(Color.MAGENTA);
	    positionMargin.setForeground(Color.MAGENTA);
	    maxUpPrice.setForeground(DARK_GREEN);
	    maxDownPrice.setForeground(Color.red);
	    
	    setAlarmValue.setMaximumSize(new Dimension(80,27));
	    newWatchPrice.setMaximumSize(new Dimension(80,27));
	    alarmLabel.setMinimumSize(new Dimension(100,17));
	    watchPriceLabel.setMinimumSize(new Dimension(100,17));	    
	    maxDownPriceLabel.setMinimumSize(new Dimension(100,17));	    
	    maxUpPriceLabel.setMinimumSize(new Dimension(100,17));	    
	    markPriceLabel.setMinimumSize(new Dimension(128,25));
	    resetWatchPrice.setMinimumSize(new Dimension(80,15));		
	    
	    resetWatchPrice.setText("Set Watch Price");

	    
	    resetWatchPrice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				statusPanel.setBackground(Color.white);					
				updatePanelWatchPrice();
			}
		});
	    
	    setAlarm.setText("Set Alarm");
	    setAlarm.setMinimumSize(new Dimension(80,15));		
	    
	    setAlarm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				statusPanel.setBackground(Color.white);					
				updateAlarm();
			}
		});
	    
	    
	    
	    GroupLayout statusPanelLayout = new GroupLayout(statusPanel);
		statusPanel.setLayout(statusPanelLayout);

		
		// Panel Layout
		statusPanelLayout.setAutoCreateGaps(true);
		statusPanelLayout.setAutoCreateContainerGaps(true);
				
		statusPanelLayout.setHorizontalGroup(statusPanelLayout.createSequentialGroup()
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(markPriceLabel)
								.addComponent(markPrice))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(unterstrich))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(maxUpPriceLabel)
								.addComponent(maxUpPrice))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(watchPriceLabel)
								.addComponent(watchPrice)
								.addComponent(bindeStrich)
								.addComponent(diffPrice))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(maxDownPriceLabel)
								.addComponent(maxDownPrice))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(alarmLabel)
								.addComponent(alarmView))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(unterstrich2))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(entryPriceLabel)
								.addComponent(entryPrice)
								.addComponent(liquidationPriceLabel)
								.addComponent(liquidationPrice))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(positionMarginLabel)
								.addComponent(positionMargin)
								.addComponent(leverageLabel)
								.addComponent(leverage))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(profitBTCLabel)
								.addComponent(profitBTC)
								.addComponent(profitPercentLabel)
								.addComponent(profitPercent))
						.addGroup(statusPanelLayout.createSequentialGroup()								
								.addComponent(unterstrich3))						
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(userAction))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(resetWatchPrice)
								.addComponent(newWatchPrice))
						.addGroup(statusPanelLayout.createSequentialGroup()
								.addComponent(setAlarm)
								.addComponent(setAlarmValue))
			));
		
		statusPanelLayout.setVerticalGroup(statusPanelLayout.createSequentialGroup()
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(markPriceLabel)
						.addComponent(markPrice))
				.addGroup(statusPanelLayout.createSequentialGroup()
						.addComponent(unterstrich))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(maxUpPriceLabel)
						.addComponent(maxUpPrice))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(watchPriceLabel)
						.addComponent(watchPrice)
						.addComponent(bindeStrich)
						.addComponent(diffPrice))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(maxDownPriceLabel)
						.addComponent(maxDownPrice))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(alarmLabel)
						.addComponent(alarmView))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)						
						.addComponent(unterstrich2))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(entryPriceLabel)
						.addComponent(entryPrice)
						.addComponent(liquidationPriceLabel)
						.addComponent(liquidationPrice))									
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(positionMarginLabel)
						.addComponent(positionMargin)
						.addComponent(leverageLabel)
						.addComponent(leverage))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(profitBTCLabel)
						.addComponent(profitBTC)
						.addComponent(profitPercentLabel)
						.addComponent(profitPercent))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(unterstrich3))				
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(userAction))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(resetWatchPrice)
						.addComponent(newWatchPrice))
				.addGroup(statusPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(setAlarm)
						.addComponent(setAlarmValue))						
		);
		
		
	    
	    jFrame.add(statusPanel);
	   
	   
	return jFrame;
   }  
    
    private void updateProfitPercent() {
    	
		currentPosition = httpGetCalls.requestPosition(apikey, apiSecret, apiurl);
		positionMargin.setText(String.format("%.8f",Double.valueOf(currentPosition.getPositionMargin())));
//	    value.setText(String.format("%.8f",Double.valueOf(currentPosition.getPosition_value())));
	    entryPrice.setText(String.valueOf(fr.formatTo2Dec(currentPosition.getEntry_price())));
	    liquidationPrice.setText(String.valueOf(currentPosition.getLiq_price()));
	    leverage.setText(Integer.toString(currentPosition.getLeverage()));
	    profitBTC.setText(String.format("%.8f",Double.valueOf(currentPosition.getUnrealised_pnl())));

	    double profit34 = (currentPosition.getUnrealised_pnl()/currentPosition.getPositionMargin()*100);
	    profitPercent.setText(String.valueOf(fr.formatTo2Dec(profit34))+" %");
    }
}
