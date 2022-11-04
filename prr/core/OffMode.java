package prr.core;

import java.io.Serializable;

import prr.core.exception.ReceiverIsOffException;
import prr.core.exception.TerminalStateAlreadySetException;

public class OffMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new OffMode();
	
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
		return false;
	}

	
	/** 
	 * Checks if the Terminal can start a new Communication
	 * 
	 * @return false since communication cannot be started in this mode
	 */
	@Override
	public boolean canStartCommunication() {
		return false;
	}

	
	/** 
	 * Sets the terminal from Off state to Idle state and notifies client
	 * 
	 * @param terminal
	 */
	@Override
	public void setOnIdle(Terminal terminal) {
		terminal.setMode(IdleMode.getMode());
		terminal.getOwner().addNotification(new Notification(terminal, new NotificationDeliveryMethod(), "O2I"));
	}

	/** 
	 * Sets the terminal from Off state to Silent state and notifies client
	 * 
	 * @param terminal
	 */
	@Override
	public void setOnSilent(Terminal terminal) {
		terminal.setMode(SilenceMode.getMode());
		terminal.getOwner().addNotification(new Notification(terminal, new NotificationDeliveryMethod(), "O2S"));
	}


	/**
	 * @throws TerminalStateAlreadySetException because terminal is already Off
	 */
	@Override
	public void turnOff(Terminal terminal) throws TerminalStateAlreadySetException {
		throw new TerminalStateAlreadySetException();
	}

	
	/** 
	 * Text Communication cannot be made
	 */
	@Override
	public Communication makeSMS(Terminal sender, Terminal receiver, String Message) {
		return null;
	}

	
	/** 
	 * Text Communication can be received
	 * 
	 * @param sender terminal that sends the communication
	 */
	@Override
	public Communication acceptSMS(Terminal sender) throws ReceiverIsOffException {
		throw new ReceiverIsOffException();
	}

	
	/** 
	 * Voice Communication cannot be made
	 */
	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		return null;
	}

	
	/** 
	 * Voice Communication cannot be made
	 * 
	 * @throws ReceiverIsOffException because terminal is in Off state
	 */
	@Override
	public Communication acceptVoiceCall(Terminal sender) throws ReceiverIsOffException {
		throw new ReceiverIsOffException();
	}

	
	/** 
	 * Video Communication cannot be made
	 */
	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		return null;
	}

	
	/** 
	 * Video Communication cannot be made
	 * 
	 * @throws ReceiverIsOffException because terminal is in Off state
	 */
	@Override
	public Communication acceptVideoCall(Terminal sender) throws ReceiverIsOffException {
		throw new ReceiverIsOffException();
	}

	
	/** 
	 * toString implementation of the Terminal Mode
	 */
	@Override
	public String toString() {
		return "OFF";
	}

	
	@Override
	public void endOngoingCommunication(Terminal terminal) {
	}

	@Override
	public void handleFailedCommunication(Terminal terminal) {	
	}
}
