package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.WriteMultipleCoilsRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteMultipleCoilsResponse extends ModbusResponse<WriteMultipleCoilsRequest, WriteMultipleCoilsResponse> {
    private int address;
    private int quantity;

    public WriteMultipleCoilsResponse(int address, int quantity) {
        super.code = FunctionCode.WriteMultipleCoils;
        this.address = address;
        this.quantity = quantity;
    }
}
