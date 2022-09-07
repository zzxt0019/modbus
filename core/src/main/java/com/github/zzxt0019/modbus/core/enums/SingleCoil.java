package com.github.zzxt0019.modbus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum SingleCoil {
    ON(0xFF00, true),
    OFF(0x0000, false),
    None(null, null),
    ;
    @Getter
    private final Integer intValue;
    @Getter
    private final Boolean boolValue;
    private static final Map<Integer, SingleCoil> intMap = Arrays.stream(SingleCoil.values()).collect(Collectors.toMap(SingleCoil::getIntValue, singleCoil -> singleCoil));
    private static final Map<Boolean, SingleCoil> boolMap = Arrays.stream(SingleCoil.values()).collect(Collectors.toMap(SingleCoil::getBoolValue, singleCoil -> singleCoil));

    public static SingleCoil get(int value) {
        if (intMap.containsKey(value)) {
            return intMap.get(value);
        }
        return None;
    }

    public static SingleCoil get(boolean value) {
        if (boolMap.containsKey(value)) {
            return boolMap.get(value);
        }
        return None;
    }
}
