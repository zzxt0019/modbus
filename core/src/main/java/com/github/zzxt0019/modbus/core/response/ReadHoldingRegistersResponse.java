package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.ReadHoldingRegistersRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadHoldingRegistersResponse extends ModbusResponse<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> {
    private byte[] value;

    public ReadHoldingRegistersResponse(byte[] value) {
        super.code = FunctionCode.ReadHoldingRegisters;
        this.value = value;
    }
}
