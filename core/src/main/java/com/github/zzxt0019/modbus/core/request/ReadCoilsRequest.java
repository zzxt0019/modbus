package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.ReadCoilsResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadCoilsRequest extends ModbusRequest<ReadCoilsRequest, ReadCoilsResponse> {
    private int address;
    private int quantity;

    public ReadCoilsRequest(int address, int quantity) {
        super.code = FunctionCode.ReadCoils;
        this.address = address;
        this.quantity = quantity;
    }
}
