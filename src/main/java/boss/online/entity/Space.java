package boss.online.entity;

import java.awt.Color;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Space extends AbsUUIDEntity {

	private String name;
	
	private Color color;
	
	private String initialLetter;
	
	@ManyToOne(cascade = CascadeType.REMOVE, fetch =FetchType.LAZY )
	private Icon icon;
	
	private String accessType;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "space")
//	private List<SpaceClickApps> spaceClickApps;
//	
//	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	private List<SpaceView> spaceViews;
//	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<SpaceUser> spaceUsers;
}
