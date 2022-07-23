package boss.online.service;

import java.nio.file.ReadOnlyFileSystemException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import boss.online.entity.Attachment;
import boss.online.entity.User;
import boss.online.entity.UserAndWorkspace;
import boss.online.entity.Workspace;
import boss.online.entity.WorkspacePermisson;
import boss.online.entity.WorkspaceRole;
import boss.online.entity.enums.AddType;
import boss.online.entity.enums.WorkspacePermissonName;
import boss.online.entity.enums.WorkspaceRoleName;
import boss.online.payload.ApiResponse;
import boss.online.payload.MemberDto;
import boss.online.payload.WorkspaceDto;
import boss.online.repository.AttachmentRepository;
import boss.online.repository.UserAndWorkspaceRepository;
import boss.online.repository.UserRepository;
import boss.online.repository.WorkspacePermissonRepository;
import boss.online.repository.WorkspaceRepository;
import boss.online.repository.WorkspaceRoleRepository;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

	@Autowired
	WorkspaceRepository workspRepo;
	
	@Autowired
	AttachmentRepository attachRepo;
	
	@Autowired
	UserAndWorkspaceRepository userAndWorkspaceRepo;
	
	@Autowired
	WorkspaceRoleRepository workspaceRoleRepo;
	
	@Autowired
	WorkspacePermissonRepository workspacePerRepo;
	
	@Autowired
	UserRepository userRepository;
	/**
	 * create workspace and take current User from SecurityContextHolder
	 */
	@Override
	public ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user) {
		if(workspRepo.existsByUserIdAndName(user.getId(), workspaceDto.getName() ))
			return new ApiResponse("you have already exist worspace with "+workspaceDto.getName(), false);
		Workspace workspace = new Workspace(workspaceDto.getName(),workspaceDto.getColor(),
				workspaceDto.getAttachmentId()==null?null:attachRepo.findById(workspaceDto.getAttachmentId()).orElseThrow(()-> new ResourceNotFoundException("attachment")),
						user);
		workspRepo.save(workspace);
		
		/**
		 * assign Role to User, who first created workspace 
		 */
		WorkspaceRole ownerRole = workspaceRoleRepo.save(new WorkspaceRole(workspace,WorkspaceRoleName.ROLE_OWNER.name(),null));
		WorkspaceRole adminRole = workspaceRoleRepo.save(new WorkspaceRole(workspace,WorkspaceRoleName.ROLE_ADMIN.name(),null));
		WorkspaceRole memberRole = workspaceRoleRepo.save(new WorkspaceRole(workspace,WorkspaceRoleName.ROLE_MEMBER.name(),null));
		WorkspaceRole guestRole = workspaceRoleRepo.save(new WorkspaceRole(workspace,WorkspaceRoleName.ROLE_GUEST.name(),null));
		
		/**
		 * ownerRole get Permissons
		 */
		WorkspacePermissonName[] values = WorkspacePermissonName.values();
		List<WorkspacePermisson> workspacePermissons = new ArrayList<>();
		for (WorkspacePermissonName workspacePermissonName : values) {
			WorkspacePermisson workspacePermisson = new WorkspacePermisson(ownerRole,workspacePermissonName);
			workspacePermissons.add(workspacePermisson);
			if(workspacePermissonName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
				workspacePermissons.add(new WorkspacePermisson(adminRole,workspacePermissonName));
			}
			if(workspacePermissonName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
				workspacePermissons.add(new WorkspacePermisson(memberRole,workspacePermissonName));
			}
			if(workspacePermissonName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
				workspacePermissons.add(new WorkspacePermisson(guestRole,workspacePermissonName));
			}
		}
		workspacePerRepo.saveAll(workspacePermissons);
		
		/**
		 * assign user to the Workspace
		 */
		userAndWorkspaceRepo.save(new UserAndWorkspace(user, workspace,ownerRole, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
		
		return new ApiResponse("workspace auccesfuly saved", true);
	}

	@Override
	public ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse changeOwner(Long id, UUID ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApiResponse deleteWorkspace(Long id) {

		try {
			workspRepo.deleteById(id);
			return new ApiResponse("successfuly deleted", true);
		} catch ( Exception e) {
			return new ApiResponse("Conflict", false);
		}
	}

	@Override
	public ApiResponse addOrEditOrRemoveWorksapce(Long id, MemberDto memberDto) {
		if(userAndWorkspaceRepo.existsByUserIdAndWorkspaceId(memberDto.getMemberId(), id))
			return new ApiResponse("User is alread exist in the Workspace", false);
		if(memberDto.getAddType().equals(AddType.ADD)) {
			UserAndWorkspace userAndWorkspace = new UserAndWorkspace(
					userRepository.findById(memberDto.getMemberId()).orElseThrow(()->new ResourceNotFoundException("mmberId")),
					workspRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("id")),
						workspaceRoleRepo.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("roleId")),						new Timestamp(System.currentTimeMillis()),
						null);
			userAndWorkspaceRepo.save(userAndWorkspace);
		}else if(memberDto.getAddType().equals(AddType.EDIT)) {
			UserAndWorkspace userAndWorkspace=userAndWorkspaceRepo.findByUserIdAndWorkspaceId(memberDto.getMemberId(), id).orElseGet(UserAndWorkspace::new);
			userAndWorkspace.setWorkspaceRole(workspaceRoleRepo.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("roleId")));
			userAndWorkspaceRepo.save(userAndWorkspace);
	
		}else if(memberDto.getAddType().equals(AddType.REMOVE)) {
			userAndWorkspaceRepo.deleteByUserIdAndWorkspaceId(memberDto.getMemberId(), id);
		}
		return new ApiResponse("sucessfuly",true);
	}

}
