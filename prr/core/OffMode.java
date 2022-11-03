package prr.core;

import java.io.Serializable;

public class OffMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new OffMode();

	public static TerminalMode getMode() {
		return _mode;
	}

	@Override
	public boolean canEndCurrentCommunication() {
		return false;
	}

	@Override
	public boolean canStartCommunication() {
		return false;
	}

	@Override
	public void setOnIdle(Terminal terminal) {
		terminal.setMode(IdleMode.getMode());
	}

	@Override
	public void setOnSilent(Terminal terminal) {
	}

	@Override
	public void setOnBusy(Terminal terminal) {
	}

	@Override
	public void turnOff(Terminal terminal) {
	}

	@Override
	public void makeSMS(Terminal receiver, String Message) {
	}

	@Override
	public void acceptSMS(Terminal sender) {		
	}

	@Override
	public void makeVoiceCall(Terminal receiver) {
	}

	@Override
	public void acceptVoiceCall(Terminal sender) {
	}

	@Override
	public void endOngoingCommunication(int size) {
	}

	@Override
	public void makeVideoCall(Terminal terminalTo) {
	}

	@Override
	public void acceptVideoCall(Terminal terminalFrom) {	
	}

}
