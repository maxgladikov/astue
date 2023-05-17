package astue.service;


import static org.assertj.core.api.Assertions.assertThat;

import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.mock.connection.MockConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import astue.model.Device;
import astue.service.implementation.FieldDataModbusPlc4jService;

@TestInstance(Lifecycle.PER_CLASS)
class FieldDataModbusPlc4jServiceTest {
	 FieldDataModbusPlc4jService serviceTesys;
	 FieldDataModbusPlc4jService serviceF650;
	@BeforeAll
	void setUp() throws Exception {
		Device tesys=Device.newBuilder()
				.setName("TestDevice")
				.setIp("192.168.0.1")
				.setIed("TESYS")
				.build();
		Device f650=Device.newBuilder()
				.setName("TestDevice")
				.setIp("192.168.0.2")
				.setIed("F650")
				.build();
		 serviceTesys=new FieldDataModbusPlc4jService(tesys);
		serviceF650=new FieldDataModbusPlc4jService(f650);
	
	}
//@Disabled("Disabled because method is private")
	@Test
	@DisplayName("Check if connection string is correct")
	@Disabled
	void buildConnectionStringTest() {
		String strTesys=serviceTesys.buildConnectionString();
		String strF650=serviceF650.buildConnectionString();
		assertThat(strTesys).isEqualTo("modbus-tcp:tcp://192.168.0.1:502");
		assertThat(strF650).isEqualTo("modbus-tcp:tcp://192.168.0.2:502");
	}
	
	@Test
	@DisplayName("Check if address string is correct")
	@Disabled
	void buildAddressStringTest(){
		String strTesys=serviceTesys.buildAddressString();
		String strF650=serviceF650.buildAddressString();
		assertThat(strTesys).isEqualTo("40143:UINT[4]");
		assertThat(strF650).isEqualTo("43908:INT[8]");
	}
	
//	
//	@Test
//	@DisplayName("Check if address string is correct")
//	void mockTesys() throws PlcConnectionException{
//		PlcDriverManager driverManager = new PlcDriverManager();
//		MockConnection connection = (MockConnection) driverManager.getConnection("mock:my-mock-connection");
//		connection.setDevice(mockDevice);
////		PlcMockConnection connection = (PlcMockConnection) driverManager.getConnection("mock:my-mock-connection");
//		
//		
//	}
	
	
//	assertThatThrownBy(() -> {
//	    List<String> list = Arrays.asList("String one", "String two");
//	    list.get(2);
//	}).isInstanceOf(IndexOutOfBoundsException.class)
//	  .hasMessageContaining("Index: 2, Size: 2");
	
//	MockDevice mockDevice = new MockDevice() {
//
//	    Pair<PlcResponseCode, PlcValue> read(String fieldQuery) {
//	        System.out.println("I got a read to " + fieldQuery);
//	        return Pair.of(PlcResponseCode.OK, new PlcString("hello"));
//	    }
//
//	    PlcResponseCode write(String fieldQuery, Object value) {
//	        System.out.println("I got a write to " + fieldQuery + " with the value " + value);
//	        return PlcResponseCode.OK;
//	    }
//	};
	

}
