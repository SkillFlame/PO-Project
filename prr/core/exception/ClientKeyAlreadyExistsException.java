package prr.core.exception;

public class ClientKeyAlreadyExistsException extends Exception{

    /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    private String _key;
	/** @param key the duplicated key */
	public ClientKeyAlreadyExistsException(String key) {
		super("Client key already exists:" + key);
        _key = key;
	}

    public String getKey(){
        return _key;
    }
}
