package prr.core;

public interface TerminalMode {
	public boolean canEndCurrentCommunication(Terminal terminal);

	public boolean canStartCommunication();

	public void setOnIdle(Terminal terminal);

	public void setOnSilent(Terminal terminal);

	public void setOnBusy(Terminal terminal);

	public void turnOff(Terminal terminal);

	Communication makeSMS(Terminal sender, Terminal receiver, String Message);

	void acceptSMS(Terminal sender);

	Communication makeVoiceCall(Terminal sender, Terminal receiver);

	void acceptVoiceCall(Terminal sender);

	Communication makeVideoCall(Terminal sender, Terminal receiver);

	void acceptVideoCall(Terminal terminalFrom);

	void endOngoingCommunication(int size);
}
