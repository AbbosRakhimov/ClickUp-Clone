package boss.online.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import boss.online.template.AbsUUIDEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class Attachment extends AbsUUIDEntity {

	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private String originalName;
	
	private Long size;
	
	private String contentType;
}
