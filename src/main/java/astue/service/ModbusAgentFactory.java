package astue.service;

import astue.model.Device;

public class ModbusAgentFactory {
       public  ModbusAgent create(Device device){
           return new ModbusAgent(device);
       }
}
