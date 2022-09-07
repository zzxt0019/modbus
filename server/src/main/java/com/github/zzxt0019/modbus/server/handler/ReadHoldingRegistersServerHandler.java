package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.modbus.core.request.ReadHoldingRegistersRequest;
import com.github.zzxt0019.modbus.core.response.ReadHoldingRegistersResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadHoldingRegistersServerHandler extends MessageServerHandler<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> {
    public ReadHoldingRegistersServerHandler(ModbusFunction<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> function) {
        super(function);
    }
}
