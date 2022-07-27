package boss.online.service;

import java.util.List;
import java.util.UUID;

import boss.online.entity.User;
import boss.online.payload.ApiResponse;
import boss.online.payload.MemberDto;
import boss.online.payload.WorkspaceDto;
import boss.online.payload.WorkspaceRoleDto;


public interface WorkspaceService {

	ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user);

	ApiResponse changeOwner(Long id, UUID ownerId);

	ApiResponse deleteWorkspace(Long id);

	ApiResponse addOrEditOrRemoveWorksapce(Long id, MemberDto memberDto);

	ApiResponse joinToWorkspace(Long id, User user);

	List<MemberDto> getMemberAndGuest(Long id);

	List<WorkspaceDto> getMyWorkspaces(User user);

	ApiResponse addOrRemovePermissionRole(WorkspaceRoleDto workspaceRoleDto);

	ApiResponse addRole(Long worksapceId, WorkspaceRoleDto workspaceRoleDto, User user);

	ApiResponse editWorkspace(WorkspaceDto workspaceDto, User user);
	
}
