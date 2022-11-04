package prr.core;

import java.io.Serializable;

public class IdleMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new IdleMode();

	public static TerminalMode getMode() {
		return _mode;
	}

	@Override
	public boolean canEndCurrentCommunication(Terminal terminal) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStartCommunication() {
		return true;
	}

	@Override
	public void setOnIdle(Terminal terminal) {
	}

	@Override
	public void setOnSilent(Terminal terminal) {
		terminal.setMode(SilenceMode.getMode());
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
	public Communication makeSMS(Terminal sender, Terminal receiver, String Message) {
		Communication SMS = new TextCommunication(sender, receiver, Message);
		return SMS;
	}

	@Override
	public Communication acceptSMS(Terminal sender) {
		return sender.getLastCommunicationMade();
	}

	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		sender.setMode(BusyMode.getMode());
		return new VoiceCommunication(sender, receiver);
	}

	@Override
	public Communication acceptVoiceCall(Terminal sender) {
		sender.setMode(BusyMode.getMode());
		return sender.getLastCommunicationMade();
	}

	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		sender.setMode(BusyMode.getMode());
		return new VideoCommunication(sender, receiver);
	}

	@Override
	public Communication acceptVideoCall(Terminal sender) {
		sender.setMode(BusyMode.getMode());
		return sender.getLastCommunicationMade();
	}

	@Override
	public void endOngoingCommunication(int duration, Terminal terminal) {
	}

	@Override
	public String toString() {
		return "IDLE";
	}

	@Override
	public void handleFailedCommunication(Terminal terminal) {
	}
}
