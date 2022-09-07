package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.WriteMultipleRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteMultipleRegistersRequest extends ModbusRequest<WriteMultipleRegistersRequest, WriteMultipleRegistersResponse> {
    private int address;
    private int quantity;  // 用于校验
    private byte[] value;

    public WriteMultipleRegistersRequest(int address, byte[] value) {
        super.code = FunctionCode.WriteMultipleRegisters;
        this.address = address;
        this.value = value;
    }
}
