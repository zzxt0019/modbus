package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.ReadInputRegistersRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadInputRegistersResponse extends ModbusResponse<ReadInputRegistersRequest, ReadInputRegistersResponse> {
    private byte[] value;

    public ReadInputRegistersResponse(byte[] value) {
        super.code = FunctionCode.ReadInputRegisters;
        this.value = value;
    }
}
