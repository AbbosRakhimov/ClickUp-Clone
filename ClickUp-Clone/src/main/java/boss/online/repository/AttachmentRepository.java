package boss.online.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import boss.online.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

}
