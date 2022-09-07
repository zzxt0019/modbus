package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.WriteSingleRegisterResponse;
import com.github.zzxt0019.modbus.core.utils.ModbusUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteSingleRegisterRequest extends ModbusRequest<WriteSingleRegisterRequest, WriteSingleRegisterResponse> {
    private int address;
    private short value;

    public WriteSingleRegisterRequest(int address, short value) {
        super.code = FunctionCode.WriteSingleRegister;
        this.address = address;
        this.value = value;
    }

    public WriteSingleRegisterRequest(int address, byte[] bytes) {
        this(address, ModbusUtil.bytesToShort(bytes));
    }
}
