package astue.util;

import java.util.Collection;

import lombok.Data;
@Data
public class CustomResponse<T> {
	
	private int code;
	private String message;
	private Collection<T> responseList;
	
	public CustomResponse(Collection<T> response, CustomStatus customStatus) {
		
		this.code=customStatus.getCode();
		this.message=customStatus.getMessage();
		this.responseList = response;
	}
	
	
}
