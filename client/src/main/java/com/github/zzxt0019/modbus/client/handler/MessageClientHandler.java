package com.github.zzxt0019.modbus.client.handler;

import com.github.zzxt0019.modbus.core.ModbusMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

import java.util.concurrent.CountDownLatch;

@ChannelHandler.Sharable
public abstract class MessageClientHandler<T extends ModbusMessage> extends SimpleChannelInboundHandler<T> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        ctx.channel().attr(AttributeKey.<ModbusMessage>valueOf(String.valueOf(msg.getMsgCount()))).set(msg);
        CountDownLatch latch = ctx.channel().attr(AttributeKey.<CountDownLatch>valueOf("com.github.zzxt0019.modbus.Lock-" + msg.getMsgCount())).get();
        if (latch != null) {
            latch.countDown();
        }
    }
}
