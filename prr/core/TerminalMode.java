package prr.core;

public interface TerminalMode {
	public boolean canEndCurrentCommunication();

	public boolean canStartCommunication();

	public void setOnIdle(Terminal terminal);

	public void setOnSilent(Terminal terminal);

	public void setOnBusy(Terminal terminal);

	public void turnOff(Terminal terminal);

	void makeSMS(Terminal receiver, String Message);

	void acceptSMS(Terminal sender);

	void makeVoiceCall(Terminal receiver);

	void acceptVoiceCall(Terminal sender);

	void endOngoingCommunication(int size);

	public void makeVideoCall(Terminal terminalTo);

	void acceptVideoCall(Terminal terminalFrom);
}
