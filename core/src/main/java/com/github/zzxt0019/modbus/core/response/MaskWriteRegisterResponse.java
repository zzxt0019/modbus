package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.MaskWriteRegisterRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MaskWriteRegisterResponse extends ModbusResponse<MaskWriteRegisterRequest, MaskWriteRegisterResponse> {
    private int address;
    private short andMask;
    private short orMask;

    public MaskWriteRegisterResponse(int address, short andMask, short orMask) {
        super.code = FunctionCode.MaskWriteRegister;
        this.address = address;
        this.andMask = andMask;
        this.orMask = orMask;
    }
}
