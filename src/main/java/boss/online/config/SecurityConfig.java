package boss.online.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import boss.online.security.JwtFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig {

	@Autowired
	JwtFilter jwtFilter;

	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.
				csrf()
				.disable()
				.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.anyRequest()
				.authenticated();
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return  http.build();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
		mailsender.setHost("smtp.gmail.com");
		mailsender.setPort(465);
		mailsender.setUsername("");
		mailsender.setPassword("");
		Properties properties=mailsender.getJavaMailProperties();
		properties.put("mail.transport.protocol","smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.enable", "true");
//		System.setProperty("java.net.preferIPv4Stack" , "true");
//		properties.put("mail.smtp.starttls.required", "false");
		properties.put("mail.debug", "true");
		return  mailsender;
	}	
	
//	@Bean
//	public JavaMailSender javaMailSender() {
//		JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
//		mailsender.setHost("smtp-relay.sendinblue.com");
//		mailsender.setPort(587);
//		mailsender.setUsername("feruzaobidjonova91@gmail.com");
//		mailsender.setPassword("Feruza1989.");
//		Properties properties=mailsender.getJavaMailProperties();
//		properties.put("mail.transport.protocol","smtp");
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.starttls.required", "true");
////		properties.put("mail.smtp.ssl.enable", "true");
//		properties.put("mail.smtp.connectiontimeout", 5000);
//		properties.put("mail.smtp.timeout", 5000);
//		properties.put("mail.writetimeout", 5000);
////		System.setProperty("java.net.preferIPv4Stack" , "true");
////		properties.put("mail.smtp.starttls.required", "false");
//		properties.put("mail.debug", "true");
//		return  mailsender;
//	}	
	
}
