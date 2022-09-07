package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.enums.SingleCoil;
import com.github.zzxt0019.modbus.core.request.WriteSingleCoilRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteSingleCoilResponse extends ModbusResponse<WriteSingleCoilRequest, WriteSingleCoilResponse> {
    private int address;
    private SingleCoil value;

    public WriteSingleCoilResponse(int address, boolean data) {
        super.code = FunctionCode.WriteSingleCoil;
        this.address = address;
        this.value = SingleCoil.get(data);
    }
}
