package boss.online.service;

import java.nio.file.ReadOnlyFileSystemException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import boss.online.payload.WorkspaceRoleDto;
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
	
	@Autowired
	MethodeHelp methodeHelp;
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
		 * Role gets Permissons
		 */
		WorkspacePermissonName[] values = WorkspacePermissonName.values();
		List<WorkspacePermisson> workspacePermissons = new ArrayList<>();
		for (WorkspacePermissonName workspacePermissonName : values) {
			if(workspacePermissonName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_OWNER)) {
				workspacePermissons.add(new WorkspacePermisson(ownerRole,workspacePermissonName));
			}
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
	/**
	 * This Method add the User to workspace or edit user like example change Role and remove user from workspace
	 */
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
			//TODO send Link an Email
			methodeHelp.sendEmailForJoin(userAndWorkspace.getUser().getEmail());
		}else if(memberDto.getAddType().equals(AddType.EDIT)) {
			UserAndWorkspace userAndWorkspace=userAndWorkspaceRepo.findByUserIdAndWorkspaceId(memberDto.getMemberId(), id).orElseGet(UserAndWorkspace::new);
			userAndWorkspace.setWorkspaceRole(workspaceRoleRepo.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("roleId")));
			userAndWorkspaceRepo.save(userAndWorkspace);
	
		}else if(memberDto.getAddType().equals(AddType.REMOVE)) {
			userAndWorkspaceRepo.deleteByUserIdAndWorkspaceId(memberDto.getMemberId(), id);
		}
		return new ApiResponse("sucessfuly",true);
	}
	
// After the user has received a link, they can join the workspace
	@Override
	public ApiResponse joinToWorkspace(Long id, User user) {
		Optional<UserAndWorkspace> uOptional = userAndWorkspaceRepo.findByUserIdAndWorkspaceId(user.getId(), id);
		if(uOptional.isPresent()) {
			UserAndWorkspace userAndWorkspace = uOptional.get();
			userAndWorkspace.setDateJoined(new Timestamp(System.currentTimeMillis()));
			userAndWorkspaceRepo.save(userAndWorkspace);
			return new ApiResponse("successfuly joined", true);
					
		}
		return new ApiResponse("Join failed", false);
	}
/**
 * this method return all User from Workspace
 */
@Override
public List<MemberDto> getMemberAndGuest(Long id) {
	List<UserAndWorkspace> userAndWorkspaces = userAndWorkspaceRepo.findAllByWorkspaceId(id);
//		List<MemberDto> memberDtos = new ArrayList<>();
//		for (UserAndWorkspace userAndWorkspace : userAndWorkspaces) {
////			memberDtos.add(methodeHelp.mapWorkspaceToMemberDto(userAndWorkspace));
//			return null;
//	return	userAndWorkspaceses.stream().map(userAndWorkspaces -> methodeHelp.mapWorkspaceToMemberDto(userAndWorkspaces)).collect(Collectors.toList());
	// if mapWorkspaceToMemberDto in this Class were, can this with Lamda replaced
	// as like this(userAndWorkspaces )::mapWorkspaceToMemberDto
//		userAndWorkspaceses.stream().map(this::mapWorkspaceToMemberDto).collect(Collectors.toList());
	return userAndWorkspaces.stream().map(userAndWorkspace -> methodeHelp.mapWorkspaceToMemberDto(userAndWorkspace))
			.collect(Collectors.toList());
}

	@Override
	public List<WorkspaceDto> getMyWorkspaces(User user) {
		List<UserAndWorkspace> userAndWorkspaces = userAndWorkspaceRepo.findAllByUserId(user.getId());

		return userAndWorkspaces.stream()
				.map(userAndWorkspace -> methodeHelp.mapWorkspaceToWorkspaceDto(userAndWorkspace.getWorkspace()))
				.collect(Collectors.toList());
	}

	@Override
	public ApiResponse addOrRemovePermissionRole(WorkspaceRoleDto workspaceRoleDto) {
		WorkspaceRole workspaceRole = workspaceRoleRepo.findById(workspaceRoleDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("workspaceRoleId"));
		Optional<WorkspacePermisson> workPermissOptional =workspacePerRepo.findByWorkspaceRoleIdAndPermissionName(workspaceRole.getId(), workspaceRoleDto.getPermissonName());
		if(workspaceRoleDto.getAddType().equals(AddType.ADD)) {
			if(workPermissOptional.isPresent())
				return new ApiResponse("User with "+ workspaceRoleDto.getId()+" already exist", false);
			WorkspacePermisson workspacePermisson = new WorkspacePermisson(workspaceRole, workspaceRoleDto.getPermissonName());
			workspacePerRepo.save(workspacePermisson);
			return new ApiResponse("Succesfully added", true);
		}else if (workspaceRoleDto.getAddType().equals(AddType.REMOVE)) {
			if(workPermissOptional.isPresent()) {
				workspacePerRepo.delete(workPermissOptional.get());
				return new ApiResponse("sucessfuly removed", true);
			}
			return new ApiResponse("removed failed", false);
		}
		
		return new ApiResponse("This Kind of Commend not exist", false);
	}
/**
 * This Methode add new Role and gives Role all Permission from extendsRole
 */
	@Override
	public ApiResponse addRole(Long worksapceId, WorkspaceRoleDto workspaceRoleDto, User user) {
		if(workspaceRoleRepo.existsByWorkspaceIdAndName(worksapceId, workspaceRoleDto.getName()))
		return new ApiResponse("eror", false);
		WorkspaceRole workspaceRole = workspaceRoleRepo.save(new WorkspaceRole(getWorksapceAfterThrow(worksapceId),
				workspaceRoleDto.getName(),workspaceRoleDto.getExtendsRole()));
		List<WorkspacePermisson> permissons = workspacePerRepo.findAllByWorkspaceRole_NameAndWorkspaceRole_WorkspaceId(workspaceRoleDto.getName(),
				worksapceId);
		List<WorkspacePermisson> workspacePermissons= new ArrayList<>();
		for (WorkspacePermisson workspacePermisson : permissons) {
			WorkspacePermisson newWorkspacePermisson = new WorkspacePermisson(workspaceRole, workspacePermisson.getPermissionName());
			workspacePermissons.add(newWorkspacePermisson);				
		}
		workspacePerRepo.saveAll(workspacePermissons);
		return new ApiResponse("saved", true);
	}

	public Workspace getWorksapceAfterThrow(Long workspaceId) {
		return workspRepo.findById(workspaceId).orElseThrow(() -> new ResourceNotFoundException("worspaceId"));
	}

	@Override
	public ApiResponse editWorkspace(WorkspaceDto workspaceDto, User user) {
		Workspace workspace= workspRepo.findById(workspaceDto.getId()).orElseThrow(() -> new ResourceNotFoundException("workspceId"));
		workspace.setColor(workspaceDto.getColor());
		workspace.setName(workspaceDto.getName());
		workspace.setAttachment(workspaceDto.getAttachmentId()==null?null:attachRepo.findById(workspaceDto.getAttachmentId()).orElseThrow(
				() -> new ResourceNotFoundException("attactmenId") ));
		workspRepo.save(workspace);
		return new ApiResponse("Workspce edited", true);
	}
}
