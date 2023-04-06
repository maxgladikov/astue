package astue.service;

import astue.model.Device;
import astue.model.Record;
import astue.util.Ied;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;

public class FieldDataModbusPlc4jService implements FieldDataService{
        private final Device device;
        private int timeOut = 3000;
        private String transport="modbus-tcp:tcp://";
        private final String connectionString ;//= "modbus-tcp:tcp://127.0.0.1:502";
        private final String PORT = "502";
        private final String fieldName="fieldData";
        private StringBuilder sb=new StringBuilder();
    public FieldDataModbusPlc4jService(Device device) {
    	this.device=device;
    	connectionString=buildConnectionString();
    }
    
    public String buildConnectionString() {
    	sb.setLength(0);
    	sb.append(transport);
    	sb.append(device.getHostAddress());
    	sb.append(":");
    	sb.append(PORT);
    	return sb.toString();
    }
    
    public String buildAddressString() {
    	Ied ied=device.getIed();
    	int fullAddress=40_000+ied.address;
    	sb.setLength(0);
    	sb.append(fullAddress);
    	sb.append(":");
    	sb.append(ied.dataType);
    	sb.append("[");
    	sb.append(ied.quantity);
    	sb.append("]");
    	return sb.toString();
    }
    
    
    @SuppressWarnings("unchecked")
	private List<Integer> readFromField(Device device)  {
    	List<Integer> result=null;
    	try {
	    	try (PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionString)) {
				
				if (!plcConnection.getMetadata().canRead()) {
					throw new Exception("This connection doesn't support reading.");
					}
				
				// Build request
				PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
				builder.addItem(fieldName, buildAddressString());
				PlcReadRequest readRequest = builder.build();
				
				// Execute request
				PlcReadResponse response = readRequest.execute().get(timeOut, TimeUnit.MILLISECONDS);
				result=(List<Integer>) response.getObject(fieldName);
	    	}
    }catch(Exception e) {}
    	
    	
    	return Optional.ofNullable(result).orElseThrow();
    }

    
    
    @Override
    public Record get()    {
            Double activePower;
            Double reactivePower;
            Ied ied=device.getIed();
            Record record=null;
            List<Integer> list= readFromField(device);
            list=list.stream().peek(x->{
                        x = (x < 0) ? (x + 32768) : x;
                    }).collect(Collectors.toList());
	            try {
		            switch (device.getIed()){
		                case TESYS->{
		                    activePower=Double.valueOf((list.get(0))<<16)+list.get(1);
		                    reactivePower=Double.valueOf((list.get(2))<<16)+list.get(3);}
		                case F650->{
		                    activePower=(Double.valueOf((list.get(0))<<16)+list.get(1))/1000;
		                    reactivePower=(Double.valueOf((list.get(4))<<16)+list.get(5))/1000;}
		               
		                default-> throw new Exception("Device type not supported"); 
		               
		            	}
		            record=new Record(device,activePower,reactivePower);
	            }catch(Exception e) {}
            return record;
        }

		

}
