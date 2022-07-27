package boss.online.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import boss.online.entity.WorkspacePermisson;
import boss.online.entity.enums.WorkspacePermissonName;

public interface WorkspacePermissonRepository extends JpaRepository<WorkspacePermisson, UUID> {
	Optional<WorkspacePermisson> findByWorkspaceRoleIdAndPermissionName(UUID id, WorkspacePermissonName permissonName);
	
	List<WorkspacePermisson> findAllByWorkspaceRole_NameAndWorkspaceRole_WorkspaceId(String workspaceRole_name, Long workspaceRole_workspace_id);
}
