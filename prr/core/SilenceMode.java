package prr.core;

import java.io.Serializable;

public class SilenceMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new SilenceMode();

	public static TerminalMode getMode() {
		return _mode;
	}

	@Override
	public boolean canEndCurrentCommunication() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStartCommunication() {
		return true;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void turnOff(Terminal terminal) {
		terminal.setMode(OffMode.getMode());
	}

	@Override
	public void makeSMS(Terminal receiver, String Message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptSMS(Terminal sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeVoiceCall(Terminal receiver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptVoiceCall(Terminal sender) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endOngoingCommunication(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeVideoCall(Terminal terminalTo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptVideoCall(Terminal terminalFrom) {
		// TODO Auto-generated method stub
		
	}

}
