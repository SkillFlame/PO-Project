package prr.core;

import java.io.Serializable;

public class OffMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new OffMode();

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
		terminal.setMode(IdleMode.getMode());
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

}
