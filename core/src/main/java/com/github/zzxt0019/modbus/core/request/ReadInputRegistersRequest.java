package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.ReadInputRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadInputRegistersRequest extends ModbusRequest<ReadInputRegistersRequest, ReadInputRegistersResponse> {
    private int address;
    private int quantity;

    public ReadInputRegistersRequest(int address, int quantity) {
        super.code = FunctionCode.ReadInputRegisters;
        this.address = address;
        this.quantity = quantity;
    }
}
