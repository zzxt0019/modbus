package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.SingleCoil;
import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.WriteSingleCoilResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteSingleCoilRequest extends ModbusRequest<WriteSingleCoilRequest, WriteSingleCoilResponse> {
    private int address;
    private SingleCoil value;

    public WriteSingleCoilRequest(int address, boolean data) {
        super.code = FunctionCode.WriteSingleCoil;
        this.address = address;
        this.value = SingleCoil.get(data);
    }
}
