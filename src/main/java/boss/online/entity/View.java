package boss.online.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
public class View extends AbsUUIDEntity {

	private String name;
	
	@ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY )
	private Icon icon;
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch =FetchType.LAZY )
	private List<SpaceView> spaceViews;
}
