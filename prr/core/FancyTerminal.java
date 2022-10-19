package prr.core;

public class FancyTerminal extends Terminal {

	public FancyTerminal(String terminalID, String clientID) {
		super(terminalID, clientID);
	}
	
	public void makeVideoCall(Terminal terminalTo){

	}

	void acceptVideoCall(Terminal terminalFrom){
		
	}
	
	@Override
	public String saveToString() {
		String output = "FANCY" + "|" + super.saveToString();
		return output;
	}
	
	@Override
	public String toString() {
		String output = "FANCY" + "|" + super.toString();
		return output;
	}

}	
