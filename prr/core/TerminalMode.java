package prr.core;

import prr.core.exception.ReceiverIsBusyException;
import prr.core.exception.ReceiverIsOffException;
import prr.core.exception.ReceiverIsSilentException;
import prr.core.exception.TerminalStateAlreadySetException;
/* Interface of the TerminalMode State pattern */
public interface TerminalMode {
	public boolean canEndCurrentCommunication(Terminal terminal);

	public boolean canStartCommunication();

	public void setOnIdle(Terminal terminal) throws TerminalStateAlreadySetException;

	public void setOnSilent(Terminal terminal) throws TerminalStateAlreadySetException;

	public void turnOff(Terminal terminal) throws TerminalStateAlreadySetException;

	Communication makeSMS(Terminal sender, Terminal receiver, String Message);

	Communication acceptSMS(Terminal sender) throws ReceiverIsOffException;

	Communication makeVoiceCall(Terminal sender, Terminal receiver);

	Communication acceptVoiceCall(Terminal sender) throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException;

	Communication makeVideoCall(Terminal sender, Terminal receiver);

	Communication acceptVideoCall(Terminal sender) throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException;

	void endOngoingCommunication(Terminal terminal);

	void handleFailedCommunication(Terminal terminal);
}
