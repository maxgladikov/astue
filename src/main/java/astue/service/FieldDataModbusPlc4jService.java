package astue.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.apache.plc4x.java.PlcDriverManager;
import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.exceptions.PlcConnectionException;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.messages.PlcWriteRequest;
import org.apache.plc4x.java.api.messages.PlcWriteResponse;
import org.apache.plc4x.java.api.value.PlcValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import astue.exception.fieldbus.DeviceNotAvailable;
import astue.exception.fieldbus.DeviceTypeNotSupported;
import astue.exception.fieldbus.ResponseTimeOutException;
import astue.exception.fieldbus.UnknownException;
import astue.model.Device;
import astue.model.Record;
import astue.util.Ied;
import astue.util.TesysDate;

public class FieldDataModbusPlc4jService implements FieldDataService{
		Logger log = LoggerFactory.getLogger(this.getClass());
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
    
    
	private List<Integer> fieldRequest(Device device) throws PlcConnectionException{
    	List<Integer>  result=null;
    	log.info("Connection is started for Device {} with ip {} ",device.getName(),device.getHostAddress());
	    	try ( PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionString) ) {
//				if (!plcConnection.getMetadata().canRead()) {
//					throw new Exception("Device "+device.getName()+" unavailable");
//					}
//				System.out.println(!plcConnection.getMetadata().canRead());
	    		
				// Build read request
				PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
				builder.addItem(fieldName, buildAddressString());
				PlcReadRequest readRequest = builder.build();
				
				// Execute request
				//Synchronous
				PlcReadResponse response;
					response = readRequest.execute().get(timeOut, TimeUnit.MILLISECONDS);
				
				List<PlcValue> intermediate=(List<PlcValue>) response.getObject(fieldName);
				result=intermediate.stream().map(x->x.getInteger()).collect(Collectors.toList());
	    	}catch(PlcConnectionException e) {
	    		log.error("Device {} with ip {} is unreachable with message {}:",device.getName(),device.getHostAddress(),e.getMessage());
	    	} 
	    	catch (TimeoutException ex) {
	    		log.error("Device {} with ip {} is exeeded respone timeout",device.getName(),device.getHostAddress());
			} catch (Exception e1) {
				log.error("Unknown error occured during poll of Device {} with ip {}",device.getName(),device.getHostAddress());
			}
	    	
	    	return Optional.ofNullable(result).orElseThrow(()->new PlcConnectionException("Error ocurred during connection with "+device.getName()+"("+device.getHostAddress()+")"));
    }
	@Override
	public void execute() {
		log.info("Connection is started for Device {} with ip {} ",device.getName(),device.getHostAddress());
    	try ( PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionString) ) {
    		
    		// Build request
			PlcWriteRequest.Builder builder = plcConnection.writeRequestBuilder();
			TesysDate dt=TesysDate.getProcessor();
			int[] arr=dt.getDateRegs();
 			builder.addItem(fieldName, "40656:UINT[4]",arr[0],arr[1],arr[2],arr[3]);
			PlcWriteRequest writeRequest = builder.build();

			// Execute request
			//Synchronous
			PlcWriteResponse response = writeRequest.execute().get();
			
    		
    	}catch(PlcConnectionException e) {
    		log.error("Device {} with ip {} is unreachable with message {}:",device.getName(),device.getHostAddress(),e.getMessage());
    	} 
    	catch (TimeoutException ex) {
    		log.error("Device {} with ip {} is exeeded respone timeout",device.getName(),device.getHostAddress());
		} catch (Exception e) {
			log.error("Unknown error occured during poll of Device {} with ip {} message: {}",device.getName(),device.getHostAddress(),e.getMessage());
		}
	}
	
	
//    public void readFromFieldTest(Device device)   {
//    	List<Integer>  result=null;
//    	try (
//    			PlcConnection plcConnection = new PlcDriverManager().getConnection(connectionString)
//    																								) {
//    		System.out.println(connectionString);
////				if (!plcConnection.getMetadata().canRead()) {
////					throw new Exception("This connection doesn't support reading.");
////					}
//    		
//    		// Build request
//    		PlcReadRequest.Builder builder = plcConnection.readRequestBuilder();
//    		builder.addItem(fieldName, buildAddressString());
//    		System.out.println(buildAddressString());
//    		PlcReadRequest readRequest = builder.build();
//    		
//    		// Execute request
////    		Synchronous
//				PlcReadResponse response = readRequest.execute().get(timeOut, TimeUnit.MILLISECONDS);
//				result=(List<Integer>) response.getObject(fieldName);
//				
//    	}
//    	
    		//Synchronous
//    		CompletableFuture<? extends PlcReadResponse> asyncResponse = readRequest.execute();
//    		asyncResponse.whenComplete((response, throwable) -> {
//    			
//			    			System.out.println(response.getResponseCode(fieldName));
//			    			var rsp=(List<Integer>) response.getObject(fieldName);
//			    			System.out.println(rsp);
				
//    			
//    		});
//    	}catch(PlcConnectionException e) {
//    		System.out.println("Couldn't connect to "+device.getHostAddress());
//    	} catch (Exception ex) {
//    		ex.printStackTrace();
//    	}
    	
//    }

    
    
    @Override
    public Record get() throws PlcConnectionException{
            Double activePower;
            Double reactivePower;
            Ied ied=device.getIed();
            Record record=null;
            if (ied.equals(Ied.TESYS))
            		;
            List<Integer> list= fieldRequest(device);
            list=list.stream().peek(x->{
                        x = (x < 0) ? (x + 32768) : x;
                    }).collect(Collectors.toList());
		            switch (device.getIed()){
		                case TESYS->{
		                    activePower=Double.valueOf((list.get(0))<<16)+list.get(1);
		                    reactivePower=Double.valueOf((list.get(2))<<16)+list.get(3);}
		                case F650->{
		                    activePower=(Double.valueOf((list.get(0))<<16)+list.get(1))/1000;
		                    reactivePower=(Double.valueOf((list.get(4))<<16)+list.get(5))/1000;}
		               
		                default-> throw new DeviceTypeNotSupported("Device type not supported"); 
		               
		            	}
		            record=new Record(device,activePower,reactivePower);
            return record;
        }

    
    

		

}
