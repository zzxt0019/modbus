package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusConsumer;
import com.github.zzxt0019.codec.modbus.request.WriteSingleCoilRequest;
import com.github.zzxt0019.codec.modbus.response.WriteSingleCoilResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteSingleCoilServerHandler extends MessageServerHandler<WriteSingleCoilRequest, WriteSingleCoilResponse> {
    public WriteSingleCoilServerHandler(ModbusConsumer<WriteSingleCoilRequest> consumer) {
        super(request -> {
            WriteSingleCoilResponse response = new WriteSingleCoilResponse(request.getAddress(), request.getValue().getBoolValue());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
