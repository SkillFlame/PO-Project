package prr.core;

import java.io.Serializable;

public class IdleMode implements TerminalMode, Serializable {
	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	final static TerminalMode _mode = new IdleMode();

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
		return true;
	}

	@Override
	public void setOnIdle(Terminal terminal) {
	}

	@Override
	public void setOnSilent(Terminal terminal) {
		terminal.setMode(SilenceMode.getMode());
	}

	@Override
	public void setOnBusy(Terminal terminal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void turnOff(Terminal terminal) {
		terminal.setMode(OffMode.getMode());
	}

}
