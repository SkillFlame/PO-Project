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

	
	/** 
	 * Checks if the Terminal can end the current ongoing Communication
	 * 
	 * @param terminal 
	 * @return false since communication cannot be ended in this mode
	 */
	@Override
	public boolean canEndCurrentCommunication(Terminal terminal) {
		return terminal.getLastCommunicationMade().getTerminalSender().getId() == terminal.getId();
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

	
	/*
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
	public Communication acceptSMS(Terminal sender) {
<<<<<<< HEAD
		// TODO Auto-generated method stub
		return null;
=======
		return sender.getLastCommunicationMade();
>>>>>>> 080155909e3a02384cc62f59fdfb164b276ecaa7
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
	 * @throws ReceiverIsBusyException because terminal is in Busy state
	 */
	@Override
	public Communication acceptVoiceCall(Terminal sender) throws ReceiverIsBusyException {
		throw new ReceiverIsBusyException();
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
	 * @throws ReceiverIsBusyException because terminal is in Busy state
	 */
	@Override
	public Communication acceptVideoCall(Terminal sender) throws ReceiverIsBusyException {
		throw new ReceiverIsBusyException();
	}

	
	/** 
	 * Ends the current ongoing Communication
	 * 
	 * @param duration the duration of the interactive communication made
	 */
	@Override
	public void endOngoingCommunication(int duration, Terminal terminal) {
		terminal.setMode(IdleMode.getMode());
	}

	
	/** 
	 * toString implementation of the Terminal Mode
	 */
	@Override
	public String toString() {
		return "BUSY";
	}

}
