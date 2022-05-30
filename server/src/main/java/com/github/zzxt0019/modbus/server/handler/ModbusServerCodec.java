package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.codec.modbus.ModbusDecoder;
import com.github.zzxt0019.codec.modbus.ModbusEncoder;
import com.github.zzxt0019.codec.modbus.ModbusMessage;
import com.github.zzxt0019.codec.modbus.errorres.ErrorResponse;
import com.github.zzxt0019.codec.modbus.response.ModbusResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class ModbusServerCodec extends MessageToMessageCodec<ByteBuf, ModbusMessage> {
    private final ModbusDecoder modbusDecoder = new ModbusDecoder();
    private final ModbusEncoder modbusEncoder = new ModbusEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ModbusMessage message, List<Object> list) throws Exception {
        if (message instanceof ModbusResponse) {
            list.add(modbusEncoder.encode((ModbusResponse<?, ?>) message));
        } else if (message instanceof ErrorResponse) {
            list.add(modbusEncoder.encode((ErrorResponse) message));
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        ModbusMessage modbusMessage = modbusDecoder.reqDecode(buf);
        if (modbusMessage != null) {
            list.add(modbusMessage);
        }
    }
}
