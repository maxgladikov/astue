package astue;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import astue.model.Device;
import astue.repository.UserRepository;
import astue.service.DivisionService;
import astue.service.FieldDataService;
import astue.service.PlantService;
import astue.service.ReportService;
import astue.service.implementation.FieldDataModbusPlc4jService;
import astue.service.implementation.UserService;
import astue.service.RecordService;
import astue.service.implementation.report.ReportCsv;
import astue.service.implementation.report.ReportProcessService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
	
	private final UserRepository userRepository;
	private final PlantService plantService;
	private final DivisionService divisionService;
	private final RecordService recordService;
    
	
	
	
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
	
	// Report services
//	@Bean(name="reportPdf")
//	public ReportService getReportPdf() {
//		return new ReportServiceRecordsPdf(recordService);
//	}
	
	@Bean(name="processPdf")
	public ReportService getReportPdf() {
		return new ReportProcessService(plantService,divisionService,recordService);
	}
	
	@Bean(name="reportCsv")
	public ReportService getReportCsv() {
		return new ReportCsv(recordService);
	}
	

}
