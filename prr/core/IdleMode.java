package prr.core;

import java.io.Serializable;

import prr.core.exception.TerminalStateAlreadySetException;

public class IdleMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new IdleMode();

	
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

	
	@Override
	public void setOnIdle(Terminal terminal) throws TerminalStateAlreadySetException {
		throw new TerminalStateAlreadySetException();
	}

	
	/** 
	 * Sets the terminal from Idle state to Silent state
	 * 
	 * @param terminal
	 */
	@Override
	public void setOnSilent(Terminal terminal) {
		terminal.setMode(SilenceMode.getMode());
	}

	
	/** 
	 * Sets the terminal from Idle state to Busy state
	 * 
	 * @param terminal
	 */
	@Override
	public void setOnBusy(Terminal terminal) {
	}

	
	/** 
	 * Sets the terminal from Idle state to Off state
	 * 
	 * @param terminal
	 */
	@Override
	public void turnOff(Terminal terminal) {
		terminal.setMode(OffMode.getMode());
	}

	
	/** 
	 * Creates a new Text Communication from an Idle state
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
	 * Creates a new Voice Communication from an Idle state
	 * 
	 * @param sender terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 */
	@Override
	public Communication makeVoiceCall(Terminal sender, Terminal receiver) {
		sender.setLastTerminalMode(this);
		sender.setMode(BusyMode.getMode());
		return new VoiceCommunication(sender, receiver);
	}

	
	/** 
	 * Accepts a received Voice Communication 
	 * 
	 * @param sender terminal that sends the communication
	 */
	@Override
    public Communication acceptVoiceCall(Terminal sender) {
        sender.getLastCommunicationMade().getTerminalReceiver().setLastTerminalMode(this);
		sender.getLastCommunicationMade().getTerminalReceiver().setMode(BusyMode.getMode());
        return sender.getLastCommunicationMade();
    }

	
	/** 
	 * Creates a new Video Communication from an Idle state
	 * 
	 * @param sender terminal that sends the communication
	 * @param receiver terminal that receives the communication
	 */
	@Override
	public Communication makeVideoCall(Terminal sender, Terminal receiver) {
		sender.setLastTerminalMode(this);
		sender.setMode(BusyMode.getMode());
		return new VideoCommunication(sender, receiver);
	}

	
	/** 
	 * Accepts a received Video Communication 
	 * 
	 * @param sender terminal that sends the communication
	 */
	@Override
    public Communication acceptVideoCall(Terminal sender) {
		sender.getLastCommunicationMade().getTerminalReceiver().setLastTerminalMode(this);
        sender.getLastCommunicationMade().getTerminalReceiver().setMode(BusyMode.getMode());
        return sender.getLastCommunicationMade();
    }

	
	@Override
	public void endOngoingCommunication(Terminal terminal) {
	}

	
	/** 
	 * toString implementation of the Terminal Mode
	 */
	@Override
	public String toString() {
		return "IDLE";
	}

	@Override
	public void handleFailedCommunication(Terminal terminal) {
	}
}
