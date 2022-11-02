package prr.core;

import java.io.Serializable;

public class BusyMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new BusyMode();

	public static TerminalMode getMode() {
		return _mode;
	}

	@Override
	public boolean canEndCurrentCommunication() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canStartCommunication() {
		return false;
	}

	@Override
	public void setOnIdle(Terminal terminal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOnSilent(Terminal terminal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOnBusy(Terminal terminal) {
	}

	@Override
	public void turnOff(Terminal terminal) {
	}

}
