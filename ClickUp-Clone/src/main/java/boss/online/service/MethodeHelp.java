package boss.online.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


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
	
	
}
