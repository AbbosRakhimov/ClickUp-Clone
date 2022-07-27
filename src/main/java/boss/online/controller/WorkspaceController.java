package boss.online.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boss.online.entity.User;
import boss.online.entity.UserAndWorkspace;
import boss.online.payload.ApiResponse;
import boss.online.payload.MemberDto;
import boss.online.payload.WorkspaceDto;
import boss.online.payload.WorkspaceRoleDto;
import boss.online.security.CurrentUser;
import boss.online.service.WorkspaceService;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {

	@Autowired
	WorkspaceService workspSer;
	
	@PostMapping
	public HttpEntity<?> addWorkspace(@Valid @RequestBody WorkspaceDto workspaceDto, @CurrentUser User user){
		ApiResponse apiResponse = workspSer.addWorkspace(workspaceDto, user);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	/**
	 * Name, Color and Attachment will be change here
	 * @param id
	 * @param workspaceDto
	 * @return
	 */
	
	@PutMapping("/edit")
	public HttpEntity<?> editWorkspace(@RequestBody WorkspaceDto workspaceDto, @CurrentUser User user){
		ApiResponse apiResponse = workspSer.editWorkspace(workspaceDto, user);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	
	@PutMapping("/changeOwner/{id}")
	public HttpEntity<?> editWorkspace(@PathVariable Long id, @RequestParam UUID ownerId){
		ApiResponse apiResponse = workspSer.changeOwner(id, ownerId);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	
	@PutMapping("/delete/{id}")
	public HttpEntity<?> deleteWorkspace(@PathVariable Long id){
		ApiResponse apiResponse = workspSer.deleteWorkspace(id);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	
	@PutMapping("addOrEditOrRemoveMember/{id}")
	public HttpEntity<?> addOrEditOrRemoveWorkspace(@PathVariable Long id, @RequestBody MemberDto memberDto){
		ApiResponse apiResponse = workspSer.addOrEditOrRemoveWorksapce(id, memberDto);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	@PutMapping("joinToWorkspace/{id}")
	public HttpEntity<?> joinToWorkspace(@RequestParam Long id, @CurrentUser User user){
		ApiResponse apiResponse = workspSer.joinToWorkspace(id, user);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	@GetMapping("member/{id}")
	public HttpEntity<?> getMemberAndGuest(@PathVariable Long id){
		List<MemberDto> memberDtos = workspSer.getMemberAndGuest(id);
		return ResponseEntity.status(memberDtos.isEmpty()?200:501).body(memberDtos);
	}
	@GetMapping
	public HttpEntity<?> getMyWorkspaces(@CurrentUser User user){
		List<WorkspaceDto> workspaceDtos = workspSer.getMyWorkspaces(user);
		return ResponseEntity.status(workspaceDtos.isEmpty()?200:501).body(workspaceDtos);
	}
	@GetMapping("/addOrRemovePermission")
	public HttpEntity<?> addOrRemovePermissionRole(@RequestBody WorkspaceRoleDto workspaceRoleDto){
		ApiResponse apiResponse = workspSer.addOrRemovePermissionRole(workspaceRoleDto);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	@PostMapping("/role")
	public HttpEntity<?> addRole(@RequestParam Long worksapceId, @RequestBody WorkspaceRoleDto workspaceRoleDto, @CurrentUser User user ){
		ApiResponse apiResponse = workspSer.addRole(worksapceId, workspaceRoleDto, user);
		return ResponseEntity.ok(apiResponse);
	}
}
