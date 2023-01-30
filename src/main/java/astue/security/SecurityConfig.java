package astue.security;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
public class SecurityConfig{}
/*
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//Authorization set up
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().
//		.antMatchers("/admin").hasRole("ADMIN")
//		.antMatchers("/user").hasAnyRole("ADMIN","USER")
		//.antMatchers("/").hasAnyRole("ADMIN","USER")
//		.antMatchers("/").permitAll()
//		.anyRequest().authenticated()
//		.and().formLogin()
//		.loginPage("/login")
//		.permitAll()
//		.and()
//		.logout().permitAll()
//		;
		
		
		http
        .authorizeRequests()
        	.antMatchers("/admin").hasRole("ADMIN")
            .antMatchers("/", "/home","/listusers/*","/finduser/*")
//            .defaultSuccessUrl("/", false);
            .permitAll();
            
//            .anyRequest().authenticated();
    http
        .formLogin()
            	.loginPage("/login").permitAll()
            .and()
            	.logout().permitAll()
            .and()
            	.exceptionHandling().accessDeniedPage("/403");
           
		
		
		
//		.defaultSuccessUrl("/index.html", true);
	}
	
	//Lambda DSL way
	
	
	
	
	//Authentication set up
	@Autowired
	UserDetailsService userDetailsService;
	
	//Get MyUserDetailsService
	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailsService();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		
	}

//	@Bean
//	public PasswordEncoder getPasswordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
*/
