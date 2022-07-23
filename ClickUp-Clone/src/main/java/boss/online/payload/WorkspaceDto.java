package boss.online.payload;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import boss.online.entity.Attachment;
import boss.online.entity.User;
import lombok.Data;

@Data
public class WorkspaceDto {

	@NotNull
	private String name;
	
	@NotNull
	private String color;
		
	private UUID attachmentId;
}
