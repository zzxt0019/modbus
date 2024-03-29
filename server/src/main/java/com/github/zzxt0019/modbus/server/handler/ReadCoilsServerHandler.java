package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.modbus.core.request.ReadCoilsRequest;
import com.github.zzxt0019.modbus.core.response.ReadCoilsResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadCoilsServerHandler extends MessageServerHandler<ReadCoilsRequest, ReadCoilsResponse> {
    public ReadCoilsServerHandler(ModbusFunction<ReadCoilsRequest, ReadCoilsResponse> function) {
        super(function);
    }
}
