package astue.aop;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import astue.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
		
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(
				MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
					Map<String, String> body = new HashMap<>();
					ex.getBindingResult().getAllErrors().forEach((error) -> {
						String fieldName = ((FieldError) error).getField();
						String errorMessage = error.getDefaultMessage();
						body.put(fieldName, errorMessage);
					});
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
		
		
		@ExceptionHandler(ConstraintViolationException.class)
		public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
			Map<String, Object> body = new LinkedHashMap<>();
		    body.put("timestamp", LocalDateTime.now());
		    body.put("message", ex.getConstraintViolations().stream().limit(1).peek(x->x.getPropertyPath()).collect(Collectors.toList()));
		    return new ResponseEntity<>(body.toString(), HttpStatus.BAD_REQUEST);
		}
//		(?<=messageTemplate=')(.*)(?=')
		@ExceptionHandler(ResourceNotFoundException.class)
		public ResponseEntity<Object> handleResourceNotFoundException( ResourceNotFoundException ex, WebRequest request) {

		    Map<String, Object> body = new LinkedHashMap<>();
		    body.put("timestamp", LocalDateTime.now());
		    body.put("message", "Resource not found");

		    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		}


}
