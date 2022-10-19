package prr.core;

public class BasicTerminal extends Terminal {

	public BasicTerminal(String terminalID, String clientID) {
		super(terminalID, clientID);
	}

	@Override
	public String toString() {
		String output = "BASIC" + "|" + super.toString();
		return output;
	}
	
}
