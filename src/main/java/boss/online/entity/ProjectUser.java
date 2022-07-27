package boss.online.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import boss.online.entity.enums.WorkspacePermissonName;
import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectUser extends AbsUUIDEntity {

	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	@JsonIgnore
	private Project project;
	
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<WorkspacePermissonName> permissions;
}
