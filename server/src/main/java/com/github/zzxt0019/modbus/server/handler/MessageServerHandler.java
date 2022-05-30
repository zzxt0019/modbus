package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusException;
import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.codec.modbus.enums.ExceptionCode;
import com.github.zzxt0019.codec.modbus.enums.FunctionCode;
import com.github.zzxt0019.codec.modbus.errorres.ErrorResponse;
import com.github.zzxt0019.codec.modbus.request.ModbusRequest;
import com.github.zzxt0019.codec.modbus.response.ModbusResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@ChannelHandler.Sharable
public abstract class MessageServerHandler<REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> extends SimpleChannelInboundHandler<REQ> {
    protected ModbusFunction<REQ, RES> function;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, REQ request) throws Exception {
        if (function != null) {
            RES response = null;
            try {
                response = function.apply(request);
                if (response != null) {
                    response.setCode(request.getCode());
                    response.setMsgCount(request.getMsgCount());
                    response.setSlaveId(request.getSlaveId());
                    ctx.channel().writeAndFlush(response);
                }
            } catch (ModbusException e) {
                ErrorResponse errorResponse = e.getErrorMessage();
                errorResponse.setCode(FunctionCode.get((byte) (request.getCode().getCode() + 0x80)));
                errorResponse.setMsgCount(request.getMsgCount());
                errorResponse.setSlaveId(request.getSlaveId());
                ctx.channel().writeAndFlush(errorResponse);
            }
        } else {
            ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.FunctionCode);
            errorResponse.setCode(FunctionCode.get((byte) (request.getCode().getCode() + 0x80)));
            errorResponse.setMsgCount(request.getMsgCount());
            errorResponse.setSlaveId(request.getSlaveId());
            ctx.channel().writeAndFlush(errorResponse);
        }
    }
}
