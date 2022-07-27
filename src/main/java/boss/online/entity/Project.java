package boss.online.entity;

import java.awt.Color;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

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
public class Project extends AbsUUIDEntity {

	private String name;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Space space;
	
	private String accesType;
	
	private Boolean achived;
	
	private Color color;
}
