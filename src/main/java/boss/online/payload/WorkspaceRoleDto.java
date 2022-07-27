package boss.online.payload;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import boss.online.entity.enums.AddType;
import boss.online.entity.enums.WorkspacePermissonName;
import boss.online.entity.enums.WorkspaceRoleName;
import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkspaceRoleDto {


	private UUID id; //roleId
	
	 // Field for Role
	private String name;
	
	private WorkspaceRoleName extendsRole;
	
	private WorkspacePermissonName permissonName;
	
	private AddType addType;
	
}
