package prr.core.exception;

public class InvalidKeyException extends Exception{
    private String _key;

    public InvalidKeyException(String key) {
		super("Key is invalid:" + key);
        _key = key;
	}

    public String getKey(){
        return _key;
    }
}
