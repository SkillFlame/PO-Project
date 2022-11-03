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
	public Communication makeSMS(Terminal sender, Terminal receiver, String Message) {
		Communication SMS = new TextCommunication(sender, receiver, Message);
		return SMS;
	}

	@Override
	public void acceptSMS(Terminal sender) {
		// TODO Auto-generated method stub

	}

	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		Communication call = new VoiceCommunication(sender, receiver);
		return call;
	}

	@Override
	public void acceptVoiceCall(Terminal sender) {
	}

	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		Communication call = new VideoCommunication(sender, receiver);
		return call;
	}

	@Override
	public void acceptVideoCall(Terminal terminalFrom) {
	}

	@Override
	public void endOngoingCommunication(int size) {
	}

}
