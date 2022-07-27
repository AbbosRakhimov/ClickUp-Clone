package boss.online.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class Task extends AbsUUIDEntity {

	private String name;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Status status;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Category category;
	
	@ManyToOne(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
	private Priority priority;
	
	@OneToMany
	private List<Task> parenTasks;
	
	private Date startDate;
	
	private Date startTimeHas;
	
	private Long estimateTime;
	
	private Boolean activeDate;
}
