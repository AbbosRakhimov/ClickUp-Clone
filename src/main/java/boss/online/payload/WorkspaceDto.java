package boss.online.payload;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import boss.online.entity.Attachment;
import boss.online.entity.User;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkspaceDto {

	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String color;
		
	private UUID attachmentId;
	
	private UUID ownerId;
	
	private String initialLater;
}
