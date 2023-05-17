package astue.service.implementation.report.tree;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import astue.model.Device;
import astue.model.PowerRecord;
import astue.service.implementation.report.ElectricReport;

class ElectricReportTest {

	private ElectricReport report_01;
	private ElectricReport report_02;
	private ElectricReport report_03;
	private ElectricReport report_div;

//	private final Clock clock;
	@BeforeEach
	void setUp() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		// device 1
		LocalDateTime dt11 = LocalDateTime.parse("1986-04-08 12:30", formatter);
		LocalDateTime dt12 = LocalDateTime.parse("1987-04-08 12:30", formatter);
		LocalDateTime dt13 = LocalDateTime.parse("1988-04-08 12:30", formatter);
		Device device_01 = Device.newBuilder().setName("test").setIp("192.168.0.1").build();

		PowerRecord record_01 = new PowerRecord(device_01, 100.0, 400.0);
		record_01.setCreated(dt11);
		PowerRecord record_02 = new PowerRecord(device_01, 200.0, 500.0);
		record_02.setCreated(dt12);
		PowerRecord record_03 = new PowerRecord(device_01, 300.0, 700.0);
		record_03.setCreated(dt13);
		device_01.setRecords(List.of(record_01, record_02, record_03));

		report_01 = ElectricReport.createFromDevice(device_01);
		// device 2
		LocalDateTime dt21 = LocalDateTime.parse("1986-05-08 12:30", formatter);
		LocalDateTime dt22 = LocalDateTime.parse("1987-05-08 12:30", formatter);
		LocalDateTime dt23 = LocalDateTime.parse("1988-04-08 12:30", formatter);
		Device device_02 = Device.newBuilder().setName("test").setIp("192.168.0.2").build();
		PowerRecord record_21 = new PowerRecord(device_02, 100.0, 450.0);
		record_21.setCreated(dt21);
		PowerRecord record_22 = new PowerRecord(device_02, 250.0, 550.0);
		record_22.setCreated(dt22);
		PowerRecord record_23 = new PowerRecord(device_02, 350.0, 750.0);
		record_23.setCreated(dt23);
		device_02.setRecords(List.of(record_21, record_22, record_23));
		report_02 = ElectricReport.createFromDevice(device_02);
		// device 3
		LocalDateTime dt31 = LocalDateTime.parse("1986-05-08 12:30", formatter);
		Device device_03 = Device.newBuilder().setName("test").setIp("192.168.0.3").build();
		PowerRecord record_31 = new PowerRecord(device_03, 350.0, 750.0);
		record_31.setCreated(dt31);
		device_03.setRecords(List.of(record_31));
		report_03 = ElectricReport.createFromDevice(device_03);
//		 division
		 report_div=new ElectricReport("division");
		 report_div.addChild(report_01);
		 report_div.addChild(report_02);
		 report_div.addChild(report_03);
System.out.println();
	}

	// Device
	@Test
	void test_device_validation_calculation() {
		assertThat(report_01.isValid()).isEqualTo(true);
	}

	@Test
	void test_device_activPowerConsumption_calculation() {
		assertThat(report_01.getActive()).isEqualTo(200.0);
	}

	@Test
	void test_device_reactivePowerConsumption_calculation() {
		assertThat(report_01.getReactive()).isEqualTo(300.0);
	}

	// Division

	@Test
	@Disabled
	void test_division_activPowerConsumption_calculation() {
		assertThat(report_div.getActive()).isEqualTo(450.0);
	}

	@Test
	@Disabled
	void test_division_reactivePowerConsumption_calculation() {
		assertThat(report_div.getReactive()).isEqualTo(600.0);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0,1, 4, 8, 2147395600, 15})
	void square(int x) {
		final long val=x;
		long j=val/2;
		long pow=2;
		 
//	            for(long i=0;i*i<=x;i++) {
//	            	j=(int) i;
//	            }
	           
	            while(true) {
	            	if(j*j<=val && (j+1)*(j+1)>val )
	            		break;
	            	
	            	if(val==0 ) {
	            		j=0;
	            		break;
	            	}
	            	
	            	if(val==1 ) {
	            		j=1;
	            		break;
	            	}
	            		
	            	long delta=val/pow<1?1:val/pow;
	            	if(j*j>val) {
	            		j=j-delta;
	            	}else {
	            		j=j+delta;
	            	}
	            	pow=pow*2;
	            };
	            
	            assertThat(j).isEqualTo( Double.valueOf(Math.pow(x, 0.5)).intValue() );
	}


	@AfterEach
	void tearDown() throws Exception {
		report_01=null;
		report_02=null;
		report_03=null;
		report_div=null;
	}

}
