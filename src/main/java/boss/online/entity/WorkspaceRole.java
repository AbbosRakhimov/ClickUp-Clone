package boss.online.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import boss.online.entity.enums.WorkspacePermissonName;
import boss.online.entity.enums.WorkspaceRoleName;
import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"workspace_id","name"}))
public class WorkspaceRole extends AbsUUIDEntity {

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinColumn
	private Workspace workspace;
	
	@Column(nullable = false) // Field for Role
	private String name;
	
	@Enumerated(EnumType.STRING)
	private WorkspaceRoleName extensRole;
	
	
	
}
