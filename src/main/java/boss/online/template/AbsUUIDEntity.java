package boss.online.template;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;



import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;



@MappedSuperclass
@Data
//@Audited
@EqualsAndHashCode(callSuper = true)
public abstract class AbsUUIDEntity extends AbsMainEntity {

	@Id
	@GeneratedValue(generator = "uuid")
	@Type(type = "org.hibernate.type.PostgresUUIDType")
	@GenericGenerator(name = "uuid",strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "uuid", updatable = false)
	private UUID id;
	
}
