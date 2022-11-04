package prr.core;

import prr.core.exception.ReceiverIsBusyException;
import prr.core.exception.ReceiverIsOffException;
import prr.core.exception.ReceiverIsSilentException;

public interface TerminalMode {
	public boolean canEndCurrentCommunication(Terminal terminal);

	public boolean canStartCommunication();

	public void setOnIdle(Terminal terminal);

	public void setOnSilent(Terminal terminal);

	public void setOnBusy(Terminal terminal);

	public void turnOff(Terminal terminal);

	Communication makeSMS(Terminal sender, Terminal receiver, String Message);

	Communication acceptSMS(Terminal sender);

	Communication makeVoiceCall(Terminal sender, Terminal receiver);

	Communication acceptVoiceCall(Terminal sender) throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException;

	Communication makeVideoCall(Terminal sender, Terminal receiver);

	Communication acceptVideoCall(Terminal sender) throws ReceiverIsBusyException, ReceiverIsOffException, ReceiverIsSilentException;

	void endOngoingCommunication(int duration);
}
