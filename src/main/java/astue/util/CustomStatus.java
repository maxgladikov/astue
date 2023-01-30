package astue.util;

import lombok.Getter;
@Getter
public enum CustomStatus {
	SUCCESS(0,"Success"),
	NOT_FOUND(1,"Not found"),
	EXCEPTION(2,"Exception");
	
	
	private final int code;
	private final String message;
	
	private CustomStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	int getCode() {
		return code;
	}

	String getMessage() {
		return message;
	}
	
	
}
