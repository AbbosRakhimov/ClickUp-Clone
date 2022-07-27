package boss.online.entity;

import javax.persistence.CascadeType;
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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class SpaceClickApps extends AbsUUIDEntity {

	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn
	private Space space;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn
	private ClickApps clickApps;
}
