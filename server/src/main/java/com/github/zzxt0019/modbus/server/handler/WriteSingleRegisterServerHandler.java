package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusConsumer;
import com.github.zzxt0019.codec.modbus.request.WriteSingleRegisterRequest;
import com.github.zzxt0019.codec.modbus.response.WriteSingleRegisterResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WriteSingleRegisterServerHandler extends MessageServerHandler<WriteSingleRegisterRequest, WriteSingleRegisterResponse> {
    public WriteSingleRegisterServerHandler(ModbusConsumer<WriteSingleRegisterRequest> consumer) {
        super(request -> {
            WriteSingleRegisterResponse response = new WriteSingleRegisterResponse(request.getAddress(), request.getValue());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
