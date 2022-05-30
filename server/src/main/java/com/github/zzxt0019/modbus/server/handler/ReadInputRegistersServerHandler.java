package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.codec.modbus.request.ReadInputRegistersRequest;
import com.github.zzxt0019.codec.modbus.response.ReadInputRegistersResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadInputRegistersServerHandler extends MessageServerHandler<ReadInputRegistersRequest, ReadInputRegistersResponse> {
    public ReadInputRegistersServerHandler(ModbusFunction<ReadInputRegistersRequest, ReadInputRegistersResponse> function) {
        super(function);
    }
}
