package boss.online.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserAndWorkspace extends AbsUUIDEntity{

	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "workspace_id")
	private Workspace workspace;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private WorkspaceRole workspaceRole;
	
	@Column(nullable = false)
	private Timestamp dateInvited;
	
	private Timestamp dateJoined;
}
