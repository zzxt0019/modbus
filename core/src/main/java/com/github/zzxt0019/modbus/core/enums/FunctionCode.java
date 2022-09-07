package com.github.zzxt0019.modbus.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum FunctionCode {
    ReadCoils((byte) 0x01, "读线圈状态"),
    ReadDiscreteInputs((byte) 0x02, "读离散输入状态"),
    ReadHoldingRegisters((byte) 0x03, "读保持寄存器"),
    ReadInputRegisters((byte) 0x04, "读输入寄存器"),
    WriteSingleCoil((byte) 0x05, "写单个线圈"),
    WriteSingleRegister((byte) 0x06, "写单个保持寄存器"),
    WriteMultipleCoils((byte) 0x0F, "写多个线圈"),
    WriteMultipleRegisters((byte) 0x10, "写多个保持寄存器"),
    MaskWriteRegister((byte) 0x16, "掩码写入保持寄存器"),
    ReadWriteMultipleRegisters((byte) 0x17, "读写多个保持寄存器"),

    ReadCoilsError((byte) 0x81, "读线圈状态错误"),
    ReadDiscreteInputsError((byte) 0x82, "读离散输入状态错误"),
    ReadHoldingRegistersError((byte) 0x83, "读保持寄存器错误"),
    ReadInputRegistersError((byte) 0x84, "读输入寄存器错误"),
    WriteSingleCoilError((byte) 0x85, "写单个线圈错误"),
    WriteSingleRegisterError((byte) 0x86, "写单个保持寄存器错误"),
    WriteMultipleCoilsError((byte) 0x8F, "写多个线圈错误"),
    WriteMultipleRegistersError((byte) 0x90, "写多个保持寄存器错误"),
    MaskWriteRegisterError((byte) 0x96, "掩码写入保持寄存器错误"),
    ReadWriteMultipleRegistersError((byte) 0x97, "读写多个保持寄存器错误"),
    None(null, "其余指令"),
    ;
    @Getter
    private final Byte code;
    private final String name;
    private static final Map<Byte, FunctionCode> map = Arrays.stream(FunctionCode.values()).collect(Collectors.toMap(FunctionCode::getCode, functionCode -> functionCode));

    public static FunctionCode get(byte code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return None;
    }
}