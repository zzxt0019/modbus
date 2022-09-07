package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.ReadHoldingRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadHoldingRegistersRequest extends ModbusRequest<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> {
    private int address;
    private int quantity;

    public ReadHoldingRegistersRequest(int address, int quantity) {
        super.code = FunctionCode.ReadHoldingRegisters;
        this.address = address;
        this.quantity = quantity;
    }
}
