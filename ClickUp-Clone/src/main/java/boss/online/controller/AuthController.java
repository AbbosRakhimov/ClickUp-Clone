package boss.online.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import boss.online.payload.ApiResponse;
import boss.online.payload.LoginDto;
import boss.online.payload.RegsiterDto;
import boss.online.service.AutService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AutService autService;
	
	@PostMapping("/register")
	public HttpEntity<?> registerUSer(@Valid @RequestBody RegsiterDto registerDto){
		ApiResponse apiResponse = autService.registerUser(registerDto);
		return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
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
}
