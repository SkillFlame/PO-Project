package prr.core;

import java.io.Serializable;

import prr.core.exception.ReceiverIsSilentException;
import prr.core.exception.TerminalStateAlreadySetException;

public class SilenceMode implements TerminalMode, Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new SilenceMode();


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
	 * @return true since communication can be started in this mode
	 */
	@Override
	public boolean canStartCommunication() {
		return true;
	}


	/**
	 * Sets the terminal from Silence state to Idle state 
	 * 				   and updates Terminal Notifications
	 * 
	 * @param terminal
	 */
	@Override
	public void setOnIdle(Terminal terminal) {
		terminal.updateNotifications("S2I");
		terminal.setMode(IdleMode.getMode());
	}


	/**
	 * @throws TerminalStateAlreadySetException since the terminal is already Silent
	 */
	@Override
	public void setOnSilent(Terminal terminal) throws TerminalStateAlreadySetException {
		throw new TerminalStateAlreadySetException();
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
	 * @param sender   terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 * @param Message  text content of the sent message
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
	 * @param sender   terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 */
	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		sender.setLastTerminalMode(this);
		sender.setMode(BusyMode.getMode());
		return new VoiceCommunication(sender, receiver);
	}


	/**
	 * Voice Communication cannot be accepted in this mode
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
	 * @param sender   terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 */
	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		sender.setLastTerminalMode(this);
		sender.setMode(BusyMode.getMode());
		return new VideoCommunication(sender, receiver);
	}


	/**
	 * Video Communication cannot be accepted in this mode
	 * 
	 * @throws ReceiverIsSilentException because terminal is in Silent state
	 */
	@Override
	public Communication acceptVideoCall(Terminal sender) throws ReceiverIsSilentException {
		throw new ReceiverIsSilentException();
	}

	@Override
	public void endOngoingCommunication(Terminal terminal) {
	}

	@Override
	public void handleFailedCommunication(Terminal terminal) {
	}


	/**
	 * toString implementation of the Terminal Mode
	 */
	@Override
	public String toString() {
		return "SILENCE";
	}

}
