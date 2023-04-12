package astue.exception.fieldbus;

public class DeviceTypeNotSupported extends RuntimeException{
    private static final long serialVersionUID = -7335788434441339506L;

	public DeviceTypeNotSupported(String message) {
        super(message );
    }

}
