package astue.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class TesysDateTest {

	@BeforeEach
	void setUp() throws Exception {
		
	}

	@Test
	void test() {
		//DT#2023-12-23-13:51:32.
				int reg655=(int) (3*Math.pow(2,12)+2*Math.pow(2,8)+0*Math.pow(2,4)+0*Math.pow(2,0));// seconds
				int reg656=(int) (1*Math.pow(2,12)+3*Math.pow(2,8)+5*Math.pow(2,4)+1*Math.pow(2,0));// hours and minutes
				int reg657=(int) (1*Math.pow(2,12)+2*Math.pow(2,8)+2*Math.pow(2,4)+3*Math.pow(2,0));// month and date
				int reg658=(int) (2*Math.pow(2,12)+0*Math.pow(2,8)+2*Math.pow(2,4)+3*Math.pow(2,0));// year
		TesysDate dt=TesysDate.getProcessor(LocalDateTime.of(2023,12,23,13,51,32));
		assertThat(dt.getDateRegs().length).isEqualTo(4);
		assertThat(dt.getDateRegs()[0]).isEqualTo(reg655);
		assertThat(dt.getDateRegs()[1]).isEqualTo(reg656);
		assertThat(dt.getDateRegs()[2]).isEqualTo(reg657);
		assertThat(dt.getDateRegs()[3]).isEqualTo(reg658);
		
	}

}
