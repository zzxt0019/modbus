package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusConsumer;
import com.github.zzxt0019.modbus.core.request.WriteMultipleCoilsRequest;
import com.github.zzxt0019.modbus.core.response.WriteMultipleCoilsResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteMultipleCoilsServerHandler extends MessageServerHandler<WriteMultipleCoilsRequest, WriteMultipleCoilsResponse> {
    public WriteMultipleCoilsServerHandler(ModbusConsumer<WriteMultipleCoilsRequest> consumer) {
        super(request -> {
            WriteMultipleCoilsResponse response = new WriteMultipleCoilsResponse(request.getAddress(), request.getQuantity());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
