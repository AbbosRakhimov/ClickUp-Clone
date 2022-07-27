package boss.online.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import boss.online.exeption.EmailNotSendExeption;
import boss.online.payload.ApiResponse;
import boss.online.payload.LoginDto;
import boss.online.payload.RegsiterDto;
import boss.online.service.AutService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AutService autService;
	
	@Transactional(rollbackFor = EmailNotSendExeption.class)
	@PostMapping("/register")
	public HttpEntity<?> registerUSer(@Valid @RequestBody RegsiterDto registerDto) throws EmailNotSendExeption{
		return autService.registerUser(registerDto);
	}
	
	@PostMapping("/login")
	public HttpEntity<?> login(@Valid @RequestBody LoginDto loginDto){
		ApiResponse apiResponse = autService.login(loginDto);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	
	@PutMapping("/verifyEmail")
	public HttpEntity<?> verifyEmail(@RequestParam String email,@RequestParam String emailCode){
		ApiResponse apiResponse = autService.verifyEmail(email,emailCode);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
	}
	
//	@RestControllerAdvice()
//	public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//		@ExceptionHandler(value = { IllegalArgumentException.class, EmailNotSendExeption.class })
//		protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
////			String bodyOfResponse = "You are not allowed to execute";
//			log.info("In RestResponseEntityExceptionHandler");
//			return handleExceptionInternal(ex, new EmailNotSendExeption("Email could not be sent", false), new HttpHeaders(), HttpStatus.CONFLICT, request);
//		}
//	}
}
