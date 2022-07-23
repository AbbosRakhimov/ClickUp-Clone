package boss.online.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import boss.online.entity.User;
import boss.online.payload.ApiResponse;
import boss.online.payload.MemberDto;
import boss.online.payload.WorkspaceDto;


public interface WorkspaceService {

	ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user);

	ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto);

	ApiResponse changeOwner(Long id, UUID ownerId);

	ApiResponse deleteWorkspace(Long id);

	ApiResponse addOrEditOrRemoveWorksapce(Long id, MemberDto memberDto);

	
}
