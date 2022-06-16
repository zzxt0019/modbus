package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.codec.modbus.request.MaskWriteRegisterRequest;
import com.github.zzxt0019.codec.modbus.response.MaskWriteRegisterResponse;
import com.github.zzxt0019.modbus.core.ModbusConsumer;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MaskWriteRegisterServerHandler extends MessageServerHandler<MaskWriteRegisterRequest, MaskWriteRegisterResponse> {
    public MaskWriteRegisterServerHandler(ModbusConsumer<MaskWriteRegisterRequest> consumer) {
        super(request -> {
            MaskWriteRegisterResponse response = new MaskWriteRegisterResponse(request.getAddress(), request.getAndMask(), request.getOrMask());
            if (consumer != null) {
                consumer.accept(request);
            }
            return response;
        });
    }
}
