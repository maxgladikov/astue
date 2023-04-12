package astue.exception.fieldbus;

public class ResponseTimeOutException extends RuntimeException{
    private static final long serialVersionUID = -7335788434441339506L;

	public ResponseTimeOutException(String message) {
        super(message );
    }
	public ResponseTimeOutException() {
		super();
	}

}
