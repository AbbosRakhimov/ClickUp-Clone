package boss.online.template;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import boss.online.entity.User;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import lombok.Data;


@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
@Audited
public abstract class AbsMainEntity {

	@Column(updatable = false, nullable = false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(updatable = false)
	@UpdateTimestamp
	private Timestamp updateAt;
	
	@JoinColumn(updatable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@CreatedBy
	private User createdBy;
	
	@LastModifiedBy
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private User updateBy;
}
