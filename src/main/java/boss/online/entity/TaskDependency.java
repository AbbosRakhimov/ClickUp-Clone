package boss.online.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import boss.online.entity.enums.DepencyType;
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
public class TaskDependency extends AbsUUIDEntity {

	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Task task;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection
	private List<DepencyType> depencyTypes;
}
