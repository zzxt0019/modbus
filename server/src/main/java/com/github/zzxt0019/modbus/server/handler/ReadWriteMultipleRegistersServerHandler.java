package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.codec.modbus.request.ReadWriteMultipleRegistersRequest;
import com.github.zzxt0019.codec.modbus.response.ReadWriteMultipleRegistersResponse;
import com.github.zzxt0019.modbus.core.ModbusFunction;

public class ReadWriteMultipleRegistersServerHandler extends MessageServerHandler<ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse> {
    public ReadWriteMultipleRegistersServerHandler(ModbusFunction<ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse> function) {
        super(function);
    }
}
