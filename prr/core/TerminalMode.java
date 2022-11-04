package prr.core;

public interface TerminalMode {
	public boolean canEndCurrentCommunication(Terminal terminal);

	public boolean canStartCommunication();

	public void setOnIdle(Terminal terminal);

	public void setOnSilent(Terminal terminal);

	public void setOnBusy(Terminal terminal);

	public void turnOff(Terminal terminal);

	Communication makeSMS(Terminal sender, Terminal receiver, String Message);

	Communication acceptSMS(Terminal sender);

	Communication makeVoiceCall(Terminal sender, Terminal receiver);

	Communication acceptVoiceCall(Terminal sender);

	Communication makeVIdeoCall(Terminal sender, Terminal receiver);

	Communication acceptVideoCall(Terminal sender);

	void endOngoingCommunication(int duration);
}
