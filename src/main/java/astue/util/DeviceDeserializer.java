package astue.util;


import astue.model.Device;
import astue.model.Division;
import astue.model.Switchgear;

import java.io.IOException;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.*;

public class DeviceDeserializer extends StdDeserializer<Device> {
    private static final long serialVersionUID = -7917697352645646875L;

	public DeviceDeserializer() {
        this(null);
    }
    public DeviceDeserializer(Class<?> vc) {
        super(vc);
    }
    @Override
    public Device deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = (Long) ( node.get("id")).longValue();
        String name = node.get("name").asText();
        System.out.println(name);
        String description = node.get("description").asText();
        String hostAddress = node.get("hostAddress").asText();
        String line = node.get("line").asText();
        Integer drawerColumn = (Integer) ((IntNode) node.get("drawerColumn")).numberValue();
        Character drawerRow = node.get("drawerRow").asText().charAt(0);
        Boolean incomer = (Boolean) ((BooleanNode) node.get("incomer")).booleanValue();
        Boolean consumer = (Boolean) ((BooleanNode) node.get("consumer")).booleanValue();
        Double power =  (node.get("power")).doubleValue();
        Character voltage = node.get("voltage").asText().charAt(0);
        String iedName = node.get("ied").asText();
        String switchgearName = Optional.of(node.get("switchgear").asText()).orElse("AUX");
        String divisionName = node.get("division").asText();

        return Device.newBuilder().setName(name)
                .setDescription(description)
                .setIp(hostAddress)
                .setLine(line)
                .setDrawerColumn(drawerColumn)
                .setDrawerRow(drawerRow)
                .setIncomer(incomer)
                .setConsumer(consumer)
                .setPower(power)
                .setVoltage(voltage)
                .setIed(iedName)
                .setSwitchgear(new Switchgear(switchgearName))
                .setDivision(new Division(divisionName))
                .build();
    }
}