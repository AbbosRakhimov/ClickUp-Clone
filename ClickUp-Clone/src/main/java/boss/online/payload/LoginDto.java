package boss.online.payload;

import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NonNull;


@Data
public class LoginDto {

	@NonNull
	@Email
	private String email;
	@NonNull
	private String password;
	
}
