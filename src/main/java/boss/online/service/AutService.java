package boss.online.service;

import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import boss.online.entity.User;
import boss.online.entity.enums.SystemRoleName;
import boss.online.exeption.EmailNotSendExeption;
import boss.online.payload.ApiResponse;
import boss.online.payload.LoginDto;
import boss.online.payload.RegsiterDto;
import boss.online.repository.UserRepository;
import boss.online.security.JwtProvider;


@Service
public class AutService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	MethodeHelp methodeHelp;
	
	/*
	 * this method registered user and saves his Data in Database
	 * each user receives a system_Role
	 */
	public ResponseEntity<?> registerUser( RegsiterDto registerDto) throws EmailNotSendExeption {
		if(userRepo.existsByEmail(registerDto.getEmail()))
			return ResponseEntity.ok(new ApiResponse("user with email is alread exist",false));
		String itialLetter=registerDto.getFullName().charAt(0)+""+""+registerDto.getFullName().charAt(registerDto.getFullName().length()-1);
		User user = new User(registerDto.getFullName(),registerDto.getEmail(),passwordEncoder.encode(registerDto.getPassword()),
				itialLetter.toUpperCase(),SystemRoleName.SYSTEM_USER);
		int code = new Random().nextInt(999999);
		user.setEmailCode(String.valueOf(code).substring(0,4));
		userRepo.save(user);
		 boolean sendOrNot= methodeHelp.sendEmail(user.getEmail(), user.getEmailCode());
		if(!sendOrNot)
		  throw new EmailNotSendExeption("Email could not be sent", false);
		 
		return ResponseEntity.ok(new ApiResponse("user saved",true));
	}

	public ApiResponse login( LoginDto loginDto) {
		try {
			Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginDto.getEmail(), loginDto.getPassword()));
			User user=(User)authenticate.getPrincipal();
			String token = jwtProvider.generateToken(user.getEmail());
			return new ApiResponse("sucessfuly token genered", true, token);
		} catch (Exception e) {
			return new ApiResponse("Password or username is wrong", false);
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException(username+"notfound"));
	}

	public ApiResponse verifyEmail(String email, String emailCode) {
		Optional<User> user = userRepo.findByEmailAndEmailCode(email, emailCode);
		if(user.isPresent()) {
			User user2 = user.get();
			user2.setEnabled(true);
			user2.setMailCode(false);
			userRepo.save(user2);
			return new ApiResponse("Account has been activated ", true);
		}
		if(!user.get().getMailCode())
			return new ApiResponse("Code has been already set", false);
		return new ApiResponse("User was not found. It's because either email or password is wrong", false);
	}

}
