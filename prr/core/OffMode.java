package prr.core;

import java.io.Serializable;

import prr.core.exception.ReceiverIsOffException;

public class OffMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new OffMode();

	public static TerminalMode getMode() {
		return _mode;
	}

	@Override
	public boolean canEndCurrentCommunication(Terminal terminal) {
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
	public Communication makeSMS(Terminal sender, Terminal receiver, String Message) {
		return null;
	}

	@Override
	public Communication acceptSMS(Terminal sender) {
		return null;
	}

	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		return null;
	}

	@Override
	public Communication acceptVoiceCall(Terminal sender) throws ReceiverIsOffException {
		throw new ReceiverIsOffException();
	}

	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		return null;
	}

	@Override
	public Communication acceptVideoCall(Terminal sender) throws ReceiverIsOffException {
		throw new ReceiverIsOffException();
	}

	@Override
	public String toString() {
		return "OFF";
	}

	@Override
	public void endOngoingCommunication(int duration) {
	}
}
