package boss.online.payload;

import java.sql.Timestamp;
import java.util.UUID;

import javax.validation.constraints.Email;

import boss.online.entity.enums.AddType;
import boss.online.entity.enums.WorkspaceRoleName;
import lombok.Data;

@Data
public class MemberDto {

	private UUID memberId;
	
	private String fullName;
	
	@Email
	private String email;
	
	private String roleName;
	
	private Timestamp lastActive;
	
	private UUID roleId;
	
	private AddType addType;
	
}
