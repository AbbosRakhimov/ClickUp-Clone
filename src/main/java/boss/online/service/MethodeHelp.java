package boss.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import boss.online.entity.UserAndWorkspace;
import boss.online.entity.Workspace;
import boss.online.payload.MemberDto;
import boss.online.payload.WorkspaceDto;


@Component
public class MethodeHelp {

	@Autowired 
	JavaMailSender javaMailSender;
	
	/*
	 * this Method send four didgit code to email
	 */
	public Boolean sendEmail(String email, String emailCode) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom("Ali@pdp.com");
			mailMessage.setTo(email);
			mailMessage.setSubject("Confirm your email");
			mailMessage.setText(emailCode);
			javaMailSender.send(mailMessage);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Boolean sendEmailForJoin(String email) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom("Ali@pdp.com");
			mailMessage.setTo(email);
			mailMessage.setSubject("Confirm your email");
			mailMessage.setText("if you join the workspace, please press the Link below");
			mailMessage.setText("<a href='http://localhost:8080/api/auth/login'>Login</a>");
			javaMailSender.send(mailMessage);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
//	mailMessage.setText("<a href='http://localhost:8080/api/auth/login?emailCode="+emailCode+"&email="+email+"'>Varify</a>");
	
	public MemberDto mapWorkspaceToMemberDto(UserAndWorkspace userAndWorkspace) {
		MemberDto memberDto = new MemberDto();
		memberDto.setEmail(userAndWorkspace.getUser().getEmail());
		memberDto.setFullName(userAndWorkspace.getUser().getFullName());
		memberDto.setLastActive(userAndWorkspace.getUser().getLastActive());
		memberDto.setRoleName(userAndWorkspace.getWorkspaceRole().getName());
		memberDto.setMemberId(userAndWorkspace.getUser().getId());
		
		return memberDto;
	}
	
	public WorkspaceDto mapWorkspaceToWorkspaceDto(Workspace workspace) {
		WorkspaceDto workspaceDto = new WorkspaceDto();
		workspaceDto.setId(workspace.getId());
		workspaceDto.setInitialLater(workspace.getInitialLater());
		workspaceDto.setName(workspace.getName());
		workspaceDto.setAttachmentId(workspace.getAttachment()==null?null:workspace.getAttachment().getId());
		
		return workspaceDto;
	}
}
