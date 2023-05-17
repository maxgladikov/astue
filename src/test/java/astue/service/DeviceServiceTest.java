package astue.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import astue.model.Device;
import astue.repository.DeviceRepository;
import astue.repository.DivisionRepository;
import astue.repository.SwitchgearRepository;
import astue.service.implementation.DeviceServiceImpl;
@ExtendWith(MockitoExtension.class)
@Disabled
class DeviceServiceTest {
	
	@Mock private DeviceRepository deviceRepository;
	@Mock private DivisionService divisionService;
	@Mock private SwitchgearService switchgearService;
	private DeviceService underTest;

	@BeforeEach
	void setUp()  {
		underTest=new DeviceServiceImpl(deviceRepository,divisionService,switchgearService);
		
	}
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test_getAll() {
		// when
		underTest.getAll();
		// then
		verify(deviceRepository).findAll();
	}
	@Test
	void test_add() {
		// given
		var name="device_1";
		var iedType="TESYS";
		var ip="192.168.0.1";
		Device testDevice=Device.newBuilder().setName(name).setIp(ip).setIed(iedType).build();
		// when
		underTest.add(testDevice);
		// then
		ArgumentCaptor<Device> deviceArgumentCaptor=ArgumentCaptor.forClass(Device.class);
		verify(deviceRepository).save(deviceArgumentCaptor.capture());
		Device capturedDevice=deviceArgumentCaptor.getValue();
		assertThat(capturedDevice).isEqualTo(testDevice);
		
		
	}

}
