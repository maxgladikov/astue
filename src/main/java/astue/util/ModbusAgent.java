package astue.util;

import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Modbus {

        private int slaveId = 1;
        private int offset = 0;
        private int quantity = 10;
        private String ip ;
        private int port=502 ;
        private boolean keepAlive=true ;
        public List<Integer> call() {
            List<Integer> result;
                try {
                    ModbusMaster client =
                            ModbusMasterFactory.createModbusMasterTCP(new TcpParameters(InetAddress.getByName(ip),port,keepAlive));
                    com.intelligt.modbus.jlibmodbus.Modbus.setAutoIncrementTransactionId(true);
                    try {
                        if (!client.isConnected()) {
                            client.connect();
                        }
                        result= Arrays.stream(client.readHoldingRegisters(slaveId, offset, quantity)).boxed().collect(Collectors.toList());

                    } catch(ModbusProtocolException | ModbusNumberException | ModbusIOException e)
                    {
                        e.printStackTrace();
                    }finally
                    {
                        try {
                            client.disconnect();
                        } catch (ModbusIOException e) {
                            e.printStackTrace();
                        }
                    }}catch(RuntimeException e)
                {
                    throw e;
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            return result;
        }

}
