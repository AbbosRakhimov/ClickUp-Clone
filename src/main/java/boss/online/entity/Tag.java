package boss.online.entity;

import java.awt.Color;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Tag extends AbsUUIDEntity {

	@Column(nullable = false)
	private String name;
	
	private Color color;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch =FetchType.LAZY )
	private Workspace workspace;
}
