package com.github.zzxt0019.modbus.server.handler;

import com.github.zzxt0019.modbus.core.ModbusMessage;
import com.github.zzxt0019.modbus.core.enums.ExceptionCode;
import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.enums.SingleCoil;
import com.github.zzxt0019.modbus.core.errorres.ErrorResponse;
import com.github.zzxt0019.modbus.core.request.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class CheckInputServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof ModbusMessage) {
            ModbusMessage msg = (ModbusMessage) obj;
            switch (msg.getCode()) {
                case ReadCoils: {
                    ReadCoilsRequest request = (ReadCoilsRequest) msg;
                    if (0x0001 <= request.getQuantity() && request.getQuantity() <= 0x07D0) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                case ReadDiscreteInputs: {
                    ReadDiscreteInputsRequest request = (ReadDiscreteInputsRequest) msg;
                    if (0x0001 <= request.getQuantity() && request.getQuantity() <= 0x07D0) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                case ReadHoldingRegisters: {
                    ReadHoldingRegistersRequest request = (ReadHoldingRegistersRequest) msg;
                    if (0x0001 <= request.getQuantity() && request.getQuantity() <= 0x007D) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                case ReadInputRegisters: {
                    ReadInputRegistersRequest request = (ReadInputRegistersRequest) msg;
                    if (0x0001 <= request.getQuantity() && request.getQuantity() <= 0x007D) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                case WriteSingleCoil: {
                    WriteSingleCoilRequest request = (WriteSingleCoilRequest) msg;
                    if (request.getValue() == SingleCoil.ON || request.getValue() == SingleCoil.OFF) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                case WriteSingleRegister: {  // 0x0000 <= value <= 0xFFFF  // always true
                    super.channelRead(ctx, msg);
                    return;
                }
//                break;
                case WriteMultipleCoils: {
                    WriteMultipleCoilsRequest request = (WriteMultipleCoilsRequest) msg;
                    if (0x0001 <= request.getQuantity() && request.getQuantity() <= 0x7B0 && request.getBytes().length == request.getQuantity()) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                case WriteMultipleRegisters: {
                    WriteMultipleRegistersRequest request = (WriteMultipleRegistersRequest) msg;
                    if (0x0001 <= request.getValue().length && request.getQuantity() <= 0x07B && request.getValue().length == request.getQuantity() << 1) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                case MaskWriteRegister: {
                    MaskWriteRegisterRequest request = (MaskWriteRegisterRequest) msg;
                    super.channelRead(ctx, request);
                    return;
                }
//                break;
                case ReadWriteMultipleRegisters: {
                    ReadWriteMultipleRegistersRequest request = (ReadWriteMultipleRegistersRequest) msg;
                    if (0x0001 <= request.getReadQuantity() && request.getReadQuantity() <= 0x007D
                            && 0x0001 <= request.getWriteQuantity() && request.getWriteQuantity() <= 0x0079) {
                        super.channelRead(ctx, request);
                        return;
                    }
                }
                break;
                default: {
                    // FunctionCode NotFound
                    ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.FunctionCode);
                    errorResponse.setCode(FunctionCode.get((byte) (msg.getCode().getCode() + 0x80)));
                    errorResponse.setMsgCount(msg.getMsgCount());
                    errorResponse.setSlaveId(msg.getSlaveId());
                    ctx.channel().writeAndFlush(errorResponse);
                }
//                break;
            }
            // Input Check Error
            ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.Input);
            errorResponse.setCode(FunctionCode.get((byte) (msg.getCode().getCode() + 0x80)));
            errorResponse.setMsgCount(msg.getMsgCount());
            errorResponse.setSlaveId(msg.getSlaveId());
            ctx.channel().writeAndFlush(errorResponse);
        }
    }

}
