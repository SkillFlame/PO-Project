package prr.core.exception;

public class UnsupportedCommunicationException extends Exception{
     /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private String _terminalId;

    /**
	 * @param terminalId id of terminal that cannot perform action
	 */
    public UnsupportedCommunicationException(String terminaId){
        super("Communication type is not supported by the terminal: " + terminaId);
        _terminalId = terminaId;
    }

    /**
	 * @return the requested terminalId
	 */
	public String getTerminalId(){
		return _terminalId;
	}
}
