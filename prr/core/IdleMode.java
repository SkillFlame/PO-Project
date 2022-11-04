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
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		Communication call = new VoiceCommunication(sender, receiver);
		return call;
	}

	@Override
	public Communication acceptVoiceCall(Terminal sender) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Communication makeVIdeoCall(Terminal sender, Terminal receiver) {
		Communication call = new VideoCommunication(sender, receiver);
		return call;
	}

	@Override
	public Communication acceptVideoCall(Terminal terminalFrom) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public void endOngoingCommunication(int duration) {
	}

	@Override
	public String toString() {
		return "IDLE";
	}
}
