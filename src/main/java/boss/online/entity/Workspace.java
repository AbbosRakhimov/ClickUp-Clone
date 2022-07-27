package boss.online.entity;

import java.awt.Color;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import boss.online.template.AbsLongEntity;
import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
@Data
public class Workspace extends AbsLongEntity {

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String color;
	
	private String initialLater;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn
	private Attachment attachment;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn
	private User user;
	
	@PrePersist
	@PreUpdate
	public void setInitialLeterMyMethod() {
		this.initialLater=name.substring(0,1);
	}

	public Workspace(String name, String color, Attachment attachment, User user) {
		super();
		this.name = name;
		this.color = color;
		this.attachment = attachment;
		this.user = user;
	}
	
	
	
//	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	private List<UserAndWorkspace> userAndWorkspaces;
}
