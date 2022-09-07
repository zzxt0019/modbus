package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.WriteSingleRegisterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteSingleRegisterResponse extends ModbusResponse<WriteSingleRegisterRequest, WriteSingleRegisterResponse> {
    private int address;
    private short data;

    public WriteSingleRegisterResponse(int address, short data) {
        super.code = FunctionCode.WriteSingleRegister;
        this.address = address;
        this.data = data;
    }
}
