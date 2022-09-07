package com.github.zzxt0019.modbus.core;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import lombok.Data;

@Data
public abstract class ModbusMessage {
    protected int msgCount;
    protected byte slaveId;
    protected FunctionCode code;
}













