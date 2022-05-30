package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.codec.modbus.request.ReadDiscreteInputsRequest;
import com.github.zzxt0019.codec.modbus.response.ReadDiscreteInputsResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadDiscreteInputsServerHandler extends MessageServerHandler<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> {
    public ReadDiscreteInputsServerHandler(ModbusFunction<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> function) {
        super(function);
    }
}
