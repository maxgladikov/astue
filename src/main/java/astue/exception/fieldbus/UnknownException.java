package astue.exception.fieldbus;

public class UnknownException extends RuntimeException{
    private static final long serialVersionUID = -7335788434441339506L;

	public UnknownException(String message) {
        super(message );
    }
	public UnknownException() {
		super();
	}

}
