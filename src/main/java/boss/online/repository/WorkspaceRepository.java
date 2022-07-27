package boss.online.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boss.online.entity.Workspace;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

	boolean existsByUserIdAndName(UUID iUuid, String name);
}
