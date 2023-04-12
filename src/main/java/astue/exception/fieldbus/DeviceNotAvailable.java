package astue.exception.fieldbus;

public class DeviceNotAvailable extends RuntimeException{
    private static final long serialVersionUID = -7335788434441339506L;

	public DeviceNotAvailable(String message) {
        super(message );
    }
	public DeviceNotAvailable() {
		super();
	}

}
