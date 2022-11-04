package prr.core;

import prr.core.exception.InvalidKeyException;
import prr.core.exception.ReceiverIsBusyException;
import prr.core.exception.ReceiverIsOffException;
import prr.core.exception.ReceiverIsSilentException;
import prr.core.exception.ReceiverTerminalDoesNotSupportCommunicationException;

/**
 * Specialization of a Terminal by its type being "FANCY"
 */
public class FancyTerminal extends Terminal {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	FancyTerminal(String terminalId, Client client) throws InvalidKeyException {
		super(terminalId, client);
	}

	/**
	 * toString implementation of a Fancy Terminal
	 * terminalType|terminalId|clientId|terminalStatus|balance-paId|balance-debts|friend1,...,friend
	 */
	@Override
	public String toString() {
		String output = "FANCY" + "|" + super.toString();
		return output;
	}

	
	
	/** 
	 * Creates a Video Communication and adds it to the Map of made Communications
	 * 
	 * @param receiver the terminal that receives the communication
	 * 
	 * @throws SenderTerminalDoesNotSupportCommunicationException if the sender terminal does not support a
	 * 															Video Communication
	 * 
	 * @throws ReceiverTerminalDoesNotSupportCommunicationException if the receiver terminal does not support
	 * 															a Video Communication
	 * 
	 * @throws ReceiverIsBusyException if the receiver terminal is in Busy state
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is in Off state
	 * 
	 * @throws ReceiverIsSilentException if the receiver terminal is in Silent state
	 */
	@Override
	public void makeVideoCall(Terminal receiver) throws ReceiverTerminalDoesNotSupportCommunicationException, ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException {
		Communication communication = getMode().makeVideoCall(this, receiver);
		addMadeCommunication(communication);
		setLastCommunicationMade(communication);
		setLastInteractiveCommunication(communication);
		receiver.acceptVideoCall(this);
	}
	
	/** 
	 /**
	 * Accepts a received Video Communication addind it to the Map of received Communications
	 * 
	 * @param sender the terminal that sends the communication
	 * 
	 * @throws ReceiverTerminalDoesNotSupportCommunicationException if the receiver terminal does not support
	 * 																a Video Communication
	 * 
	 * @throws ReceiverIsBusyException if the receiver terminal is in Busy state
	 * 
	 * @throws ReceiverIsOffException if the receiver terminal is in Off state
	 * 
	 * @throws ReceiverIsSilentException if the receiver terminal is in Silent state
	 */
	@Override
	void acceptVideoCall(Terminal sender) throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException {
		addReceivedCommunication(getMode().acceptVideoCall(sender));
	}

}
