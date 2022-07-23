package boss.online.payload;

import java.util.UUID;

import boss.online.entity.enums.AddType;
import boss.online.entity.enums.WorkspaceRoleName;
import lombok.Data;

@Data
public class MemberDto {

	private UUID memberId;
	
	private UUID roleId;
	
	private AddType addType;
}
