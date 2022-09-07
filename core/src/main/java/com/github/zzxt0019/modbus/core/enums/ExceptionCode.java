package com.github.zzxt0019.modbus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum ExceptionCode {
    FunctionCode((byte) 0x01, "功能码错误"),
    Input((byte) 0x03, "输入错误"),
    Address((byte) 0x02, "地址错误"),
    Process((byte) 0x04, "运行错误"),
    None(null, "其余错误"),
    ;
    @Getter
    private final Byte code;
    private final String name;
    private static final Map<Byte, ExceptionCode> map = Arrays.stream(ExceptionCode.values()).collect(Collectors.toMap(ExceptionCode::getCode, exceptionCode -> exceptionCode));

    public static ExceptionCode get(byte code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return None;
    }
}