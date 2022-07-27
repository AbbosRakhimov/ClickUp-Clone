package boss.online.entity;

import java.awt.Color;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import boss.online.entity.enums.WorkspacePermissonName;
import boss.online.entity.enums.AddType;
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
public class Status extends AbsUUIDEntity {

	@Column(nullable = false)
	private String name;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Space space;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Project project;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Category category;
	
	private Color color;
	
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<AddType> statusTypes;
}
