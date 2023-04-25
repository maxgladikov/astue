package astue.service.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import astue.model.User;
import astue.repository.UserRepository;
import astue.security.AuthenticationRequest;
import astue.security.AuthenticationResponse;
import astue.security.JwtService;
import astue.security.RegistrationRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final AuthenticationManager authenticationManager; 
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	public AuthenticationResponse register(RegistrationRequest request) {
		var user=User.builder()
				.username(request.getName())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(request.getRole())
				.build();
				repository.save(user);
				var jwtToken=jwtService.generateToken(user);
			return AuthenticationResponse.builder().token(jwtToken)
					.build();
		}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getName(), request.getPassword()));
		var user=repository.findByUsername(request.getName()).orElseThrow();
		var jwtToken=jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken)
		.build();
	}

}
