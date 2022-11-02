package prr.core;

public interface TerminalMode {
	public boolean canEndCurrentCommunication();

	public boolean canStartCommunication();

	public void setOnIdle(Terminal terminal);

	public void setOnSilent(Terminal terminal);

	public void setOnBusy(Terminal terminal);

	public void turnOff(Terminal terminal);
}
