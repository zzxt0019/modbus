package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.codec.modbus.request.ReadHoldingRegistersRequest;
import com.github.zzxt0019.codec.modbus.response.ReadHoldingRegistersResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadHoldingRegistersServerHandler extends MessageServerHandler<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> {
    public ReadHoldingRegistersServerHandler(ModbusFunction<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> function) {
        super(function);
    }
}
