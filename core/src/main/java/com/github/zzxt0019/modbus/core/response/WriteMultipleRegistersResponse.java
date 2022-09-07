package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.WriteMultipleRegistersRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteMultipleRegistersResponse extends ModbusResponse<WriteMultipleRegistersRequest, WriteMultipleRegistersResponse> {
    private int address;
    private int quantity;

    public WriteMultipleRegistersResponse(int address, int quantity) {
        super.code = FunctionCode.WriteMultipleRegisters;
        this.address = address;
        this.quantity = quantity;
    }
}
