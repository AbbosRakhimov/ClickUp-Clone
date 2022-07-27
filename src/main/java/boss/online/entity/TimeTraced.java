package boss.online.entity;

import java.util.Date;

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
public class TimeTraced extends AbsUUIDEntity {
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)		
	private Task task;
	
	private Date startedAt;
	
	private Date stoppedAt;
}
