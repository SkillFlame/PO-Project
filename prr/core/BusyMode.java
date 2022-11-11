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
	 * @return false if there is no communication made from the terminal, true if there is
	 */
	@Override
	public boolean canEndCurrentCommunication(Terminal terminal) {
		if(terminal.getLastCommunicationMade() == null) {
			return false;
		}
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
	public void turnOff(Terminal terminal) {
	}

	
	/*
	 * Text Communication cannot be made in this mode
	 */
	@Override
	public Communication makeSMS(Terminal sender, Terminal receiver, String Message) {
		return null;
	}

	
	/** 
	 * Text Communication can be received in this mode
	 * 
	 * @param sender terminal that sends the communication
	 */
	@Override
	public Communication acceptSMS(Terminal sender) {
		return sender.getLastCommunicationMade();
	}

	
	/** 
	 * Voice Communication cannot be made in this mode
	 */
	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		return null;
	}

	
	/** 
	 * Voice Communication cannot be received in this mode
	 * 
	 * @throws ReceiverIsBusyException because terminal is in Busy state
	 */
	@Override
	public Communication acceptVoiceCall(Terminal sender) throws ReceiverIsBusyException {
		throw new ReceiverIsBusyException();
	}

	
	/** 
	 * Video Communication cannot be made in this mode
	 */
	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		return null;
	}

	
	/** 
	 * Video Communication cannot be received in this mode
	 * 
	 * @throws ReceiverIsBusyException because terminal is in Busy state
	 */
	@Override
	public Communication acceptVideoCall(Terminal sender) throws ReceiverIsBusyException {
		throw new ReceiverIsBusyException();
	}

	
	/** 
	 * Ends the current ongoing Communication and updates Terminal
	 *		 Notifications also sets the Terminal to its last mode
	 * 
	 * @param terminal the terminal that made the interactive communication
	 */
	@Override
	public void endOngoingCommunication(Terminal terminal) {
		if(terminal.getLastTerminalMode().toString() == "IDLE") {
			terminal.updateNotifications("B2I");	
		}
		terminal.setMode(terminal.getLastTerminalMode());
	}


	/**
	 * Handles failed Communications of the Terminal
	 * 
	 * @param terminal 
	 */
	@Override
	public void handleFailedCommunication(Terminal terminal) {
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
