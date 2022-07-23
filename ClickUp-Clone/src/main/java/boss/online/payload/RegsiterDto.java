package boss.online.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class RegsiterDto {

	@NotNull
	private String fullName;

	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String password;
}
