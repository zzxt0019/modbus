package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.ReadWriteMultipleRegistersResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadWriteMultipleRegistersRequest extends ModbusRequest<ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse> {
    private int readAddress;
    private int readQuantity;
    private int writeAddress;
    private int writeQuantity;  // 用于校验
    private byte[] writeValue;

    public ReadWriteMultipleRegistersRequest(int readAddress, int readQuantity, int writeAddress, byte[] writeValue) {
        super.code = FunctionCode.ReadWriteMultipleRegisters;
        this.readAddress = readAddress;
        this.readQuantity = readQuantity;
        this.writeAddress = writeAddress;
        this.writeValue = writeValue;
    }
}
