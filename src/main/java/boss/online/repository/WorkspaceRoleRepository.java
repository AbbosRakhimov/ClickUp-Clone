package boss.online.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import boss.online.entity.WorkspacePermisson;
import boss.online.entity.WorkspaceRole;

public interface WorkspaceRoleRepository extends JpaRepository<WorkspaceRole, UUID>{

	boolean existsByWorkspaceIdAndName(Long workspaceId, String name);
	
}
