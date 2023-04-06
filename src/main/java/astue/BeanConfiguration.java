package astue;

import astue.model.Device;
import astue.repository.UserRepository;
import astue.service.FieldDataModbusPlc4jService;
import astue.service.FieldDataService;
import astue.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
	
	private final UserRepository userRepository;
    
	
	
	
	// *** SECURITY BEANS ***\\
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
//		return userName->userRepository.findByUsername(userName).orElseThrow(()->new UsernameNotFoundException("user with name "+userName+" not found"));
		return new UserService(userRepository);
	}
	
	@Bean(name="password")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public Function<Device,FieldDataService> fieldDataServiceFactory() {
		return device-> fieldDataService(device);
	}
	
	@Bean
	@Scope(value="prototype")
	public FieldDataService fieldDataService(Device device) {
		return new FieldDataModbusPlc4jService(device);
	}
	

}
