package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.ReadWriteMultipleRegistersRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadWriteMultipleRegistersResponse extends ModbusResponse<ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse> {
    private byte[] readValue;

    public ReadWriteMultipleRegistersResponse(byte[] readValue) {
        super.code = FunctionCode.ReadWriteMultipleRegisters;
        this.readValue = readValue;
    }
}
