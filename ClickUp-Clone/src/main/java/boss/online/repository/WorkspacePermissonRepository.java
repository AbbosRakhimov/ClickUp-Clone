package boss.online.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import boss.online.entity.WorkspacePermisson;

public interface WorkspacePermissonRepository extends JpaRepository<WorkspacePermisson, UUID> {

}
