package astue.service;

import astue.exception.NoRequestedIed;
import astue.model.Device;
import astue.service.RecordService;
import astue.util.Ied;
import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModbusAgent {
        private int numOfRetries = 0;
        private int maxNumOfRetries = 3;
        private int timeOut = 3000;
        private final int PORT = Modbus.TCP_PORT;
        private  final boolean KEEPALIVE =true;
        private Device device;

    public ModbusAgent(Device device) {
        this.device = device;
    }

    public List<Integer> call() {
        Ied ied=device.getIed();
        int slaveId=ied.slaveId;
        int offset=ied.address;
        int quantity= ied.quantity;
        String IP=device.getHostAddress();
            List<Integer> result=null;
            while(numOfRetries<maxNumOfRetries) {
                try {
                    ModbusMaster client =
                            ModbusMasterFactory.createModbusMasterTCP(new TcpParameters(InetAddress.getByName(IP), PORT, KEEPALIVE));
                    com.intelligt.modbus.jlibmodbus.Modbus.setAutoIncrementTransactionId(true);
                    try {
                        if (!client.isConnected()) {
                            client.connect();
                        }
                        result = Arrays.stream(client.readHoldingRegisters(slaveId, offset, quantity)).boxed().collect(Collectors.toList());

                    } catch (ModbusIOException e) {
//                        e.printStackTrace();
                        System.out.println("**** device is unreacheble "+device.getName()+" atemp #"+numOfRetries);
                        Thread.sleep(timeOut);
                        numOfRetries++;
                        continue;
                    } catch (ModbusProtocolException | ModbusNumberException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            client.disconnect();
                        } catch (ModbusIOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        public Map<String,Double> getMetering()  {
            Map<String,Double> map=new HashMap<>();
            Double activePower;
            Double reactivePower;
            Ied ied=device.getIed();
            List<Integer> list= this.call();
            if(list==null)
                throw new RuntimeException();
            list=list.stream().peek(x->{
                        x = (x < 0) ? (x + 32768) : x;
                    }).collect(Collectors.toList());
            System.out.println(list);
            switch (device.getIed()){
                case TESYS:
                    activePower=Double.valueOf((list.get(0))<<16)+list.get(1);
                    reactivePower=Double.valueOf((list.get(2))<<16)+list.get(3);
                    map.put("active",activePower);
                    map.put("reactive",reactivePower);
                    break;
                case F650:
                    activePower=(Double.valueOf((list.get(0))<<16)+list.get(1))/1000;
                    reactivePower=(Double.valueOf((list.get(4))<<16)+list.get(5))/1000;
                    map.put("active",activePower);
                    map.put("reactive",reactivePower);
                    break;
                default:
                    System.out.println("No ied with such name");
            }
            return map;
        }

}
