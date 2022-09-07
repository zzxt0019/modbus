package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.ReadDiscreteInputsResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadDiscreteInputsRequest extends ModbusRequest<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> {
    private int address;
    private int quantity;

    public ReadDiscreteInputsRequest(int address, int quantity) {
        super.code = FunctionCode.ReadDiscreteInputs;
        this.address = address;
        this.quantity = quantity;
    }
}
