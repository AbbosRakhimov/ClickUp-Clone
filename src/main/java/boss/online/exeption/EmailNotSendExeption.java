package boss.online.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class EmailNotSendExeption extends Exception {

	private String message; 
	private boolean success;
	
	public EmailNotSendExeption(String message, boolean success) {
		super();
		this.message = message;
		this.success = success;
	}
	
	
}
