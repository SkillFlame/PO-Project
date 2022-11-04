package prr.core;

import java.io.Serializable;

import prr.core.exception.ReceiverIsSilentException;

public class SilenceMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new SilenceMode();
	private NotificationDeliveryMethod _method;
	
	public static TerminalMode getMode() {
		return _mode;
	}

	
	/** 
	 * Checks if the Terminal can end the current ongoing Communication
	 * 
	 * @param terminal 
	 * @return false since communication cannot be ended in this mode
	 */
	@Override
	public boolean canEndCurrentCommunication(Terminal terminal) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/** 
	 * Checks if the Terminal can start a new Communication
	 * 
	 * @return true since communication can be started in this mode
	 */
	@Override
	public boolean canStartCommunication() {
		return true;
	}

	
	/** 
	 * Sets the terminal from Silence state to Idle state
	 * 
	 * @param terminal
	 */
	@Override
	public void setOnIdle(Terminal terminal) {
		terminal.setMode(IdleMode.getMode());
		_method.notifyTerminalS2I(terminal);
	}

	
	@Override
	public void setOnSilent(Terminal terminal) {
	}

	
	@Override
	public void setOnBusy(Terminal terminal) {
	}

	
	/** 
	 * Sets the terminal from Silence state to Off state
	 * 
	 * @param terminal
	 */
	@Override
	public void turnOff(Terminal terminal) {
		terminal.setMode(OffMode.getMode());
	}

	
	/** 
	 * Creates a new Text Communication from a Silent state
	 * 
	 * @param sender terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 * @param Message text content of the sent message
	 */
	@Override
	public Communication makeSMS(Terminal sender, Terminal receiver, String Message) {
		Communication SMS = new TextCommunication(sender, receiver, Message);
		return SMS;
	}

	
	/** 
	 * Accepts a received Text Communication 
	 * 
	 * @param sender terminal that sends the communication
	 */
	@Override
	public Communication acceptSMS(Terminal sender) {
		return sender.getLastCommunicationMade();
	}

	
	/** 
	 * Creates a new Voice Communication from a Silent state
	 * 
	 * @param sender terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 */
	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		sender.setMode(BusyMode.getMode());
		return new VoiceCommunication(sender, receiver);
	}

	
	/** 
	 * Voice Communication cannot be accepted
	 * 
	 * @throws ReceiverIsSilentException because terminal is in Silent state
	 */
	@Override
	public Communication acceptVoiceCall(Terminal sender) throws ReceiverIsSilentException {
		throw new ReceiverIsSilentException();
	}

	
	/** 
	 * Creates a new Video Communication from a Silent state
	 * 
	 * @param sender terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 */
	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		sender.setMode(BusyMode.getMode());
		return new VoiceCommunication(sender, receiver);
	}

	
	/** 
	 * Video Communication cannot be accepted
	 * 
	 * @throws ReceiverIsSilentException because terminal is in Silent state
	 */
	@Override
	public Communication acceptVideoCall(Terminal sender) throws ReceiverIsSilentException {
		throw new ReceiverIsSilentException();
	}

	
	@Override
	public void endOngoingCommunication(int duration, Terminal terminal) {
	}

	
	/** 
	 * toString implementation of the Terminal Mode
	 */
	@Override
	public String toString() {
		return "SILENCE";
	}

	@Override
	public void handleFailedCommunication(Terminal terminal) {	
	}
}
