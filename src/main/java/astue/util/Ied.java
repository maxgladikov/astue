package astue.util;

import java.util.HashMap;
import java.util.Map;

public enum Ied {
    TESYS(1,144,4,"UINT"),
    F650(1,0xF45,8,"UINT"),
    ACS6109(1,0xF44,8,"INT"),
    ACS1000(1,0xF44,8,"INT"),
    UPS_V(1,0xF44,8,"INT"),
    UPS_U(1,0xF44,8,"INT"),
    UPS_DC(1,0xF44,8,"INT"),
    ACS880(1,0xF44,8,"INT"),
    ACS800(1,0xF44,8,"INT"),
    G180(1,0xF44,8,"INT"),
    ATV630(1,0xF44,8,"INT"),
    REG615(1,0xF44,8,"INT");
    private static final Map<Integer, Ied> BY_SLAVE_ID = new HashMap<>();
    private static final Map<Integer, Ied> BY_START_ADDRESS = new HashMap<>();
    private static final Map<Integer, Ied> BY_QUANTITY = new HashMap<>();
    private static final Map<String, Ied> BY_DATA_TYPE = new HashMap<>();

    static {
        for (Ied e : values()) {
            BY_SLAVE_ID.put(e.slaveId, e);
            BY_START_ADDRESS.put(e.address, e);
            BY_QUANTITY.put(e.quantity, e);
            BY_DATA_TYPE.put(e.dataType, e);
        }
    }
    public final Integer slaveId;
    public final Integer address;
    public final Integer quantity;
    public final String dataType;

    Ied(Integer slaveId, Integer address, Integer quantity, String dataType) {
        this.slaveId = slaveId;
        this.address = address;
        this.quantity = quantity;
        this.dataType = dataType;
    }

    public static Ied valueOfSlaveId(Integer slaveId) {
        return  BY_SLAVE_ID.get(slaveId);
    }
    public static Ied valueOfStartAddress(Integer address) {
        return  BY_START_ADDRESS.get(address);
    }
    public static Ied valueOfQuantity(Integer quantity) {
        return  BY_QUANTITY.get(quantity);
    }
    public static Ied valueOfDataType(String dataType) {
        return  BY_DATA_TYPE.get(dataType);
    }
    
}

