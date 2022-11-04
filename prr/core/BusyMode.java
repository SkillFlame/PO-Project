package prr.core;

import java.io.Serializable;

import prr.core.exception.ReceiverIsBusyException;

public class BusyMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new BusyMode();

	public static TerminalMode getMode() {
		return _mode;
	}

	@Override
	public boolean canEndCurrentCommunication(Terminal terminal) {
		return terminal.getLastCommunicationMade().getTerminalSender().getId() == terminal.getId();
	}

	@Override
	public boolean canStartCommunication() {
		return false;
	}

	@Override
	public void setOnIdle(Terminal terminal) {
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
		return sender.getLastCommunicationMade();
	}

	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		return null;
	}

	@Override
	public Communication acceptVoiceCall(Terminal sender) throws ReceiverIsBusyException {
		throw new ReceiverIsBusyException();
	}

	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		return null;
	}

	@Override
	public Communication acceptVideoCall(Terminal sender) throws ReceiverIsBusyException {
		throw new ReceiverIsBusyException();
	}

	@Override
	public void endOngoingCommunication(int duration, Terminal terminal) {
		terminal.setMode(IdleMode.getMode());
	}

	@Override
	public String toString() {
		return "BUSY";
	}

	@Override
	public void handleFailedCommunication(Terminal terminal) {
		terminal.setMode(IdleMode.getMode());
	}

}
