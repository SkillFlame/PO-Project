package prr.core.exception;

public class NotificationsAlreadyDisabledException extends Exception{
    
     /** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

    public NotificationsAlreadyDisabledException(){
        super("Notifications already disabled ");
    }
}
