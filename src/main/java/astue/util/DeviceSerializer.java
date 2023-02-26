package astue.util;

import astue.model.Device;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;

public class DeviceSerializer extends StdSerializer<Device> {

    public DeviceSerializer() {
        this(null);
    }

    public DeviceSerializer(Class<Device> t) {
        super(t);
    }

    @Override
    public void serialize(
            Device device, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", device.getId());
        jgen.writeStringField("name", device.getName());
        jgen.writeStringField("description", device.getDescription());
        jgen.writeStringField("hostAddress", device.getHostAddress());
        jgen.writeStringField("line", device.getLine());
        jgen.writeNumberField("drawerColumn", device.getDrawerColumn());
        jgen.writeStringField("drawerRow", device.getDrawerRow().toString());
        jgen.writeBooleanField("incomer", device.isIncomer());
        jgen.writeBooleanField("consumer", device.isConsumer());
        jgen.writeNumberField("power", device.getPower());
        jgen.writeStringField("voltage", device.getVoltage().toString());
        jgen.writeStringField("ied", device.getIed().toString());
        jgen.writeStringField("switchgear", device.getSwitchgear().getName());
        jgen.writeStringField("division", device.getDivision().getName());
        jgen.writeEndObject();
    }
}
