package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusConsumer;
import com.github.zzxt0019.modbus.core.request.WriteMultipleRegistersRequest;
import com.github.zzxt0019.modbus.core.response.WriteMultipleRegistersResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteMultipleRegistersServerHandler extends MessageServerHandler<WriteMultipleRegistersRequest, WriteMultipleRegistersResponse> {
    public WriteMultipleRegistersServerHandler(ModbusConsumer<WriteMultipleRegistersRequest> consumer) {
        super(request -> {
            WriteMultipleRegistersResponse response = new WriteMultipleRegistersResponse(request.getAddress(), request.getValue().length >>> 1);
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
