package astue.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import astue.security.AuthenticationRequest;
import astue.security.AuthenticationResponse;
import astue.security.RegistrationRequest;
import astue.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthenticationService service;
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
		return ResponseEntity.ok(service.register(request));
		
	}
	@GetMapping("/username")
	public Map<String,String> getUsername(Principal principal){
		Map<String,String> body=new HashMap<>();
		body.put("name", Optional.ofNullable(principal).isPresent() ? principal.getName():"anon");
		
		return body;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(service.authenticate(request));
		
	}

}
