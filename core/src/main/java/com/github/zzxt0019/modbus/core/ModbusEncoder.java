package com.github.zzxt0019.modbus.core;

import com.github.zzxt0019.modbus.core.errorres.ErrorResponse;
import com.github.zzxt0019.modbus.core.request.ReadInputRegistersRequest;
import com.github.zzxt0019.modbus.core.response.ReadInputRegistersResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ModbusEncoder {
    public ByteBuf encode(com.github.zzxt0019.modbus.core.request.ModbusRequest<?, ?> message) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeShort(message.getMsgCount());
        buf.writeBytes(new byte[]{0, 0, 0, 0});
        buf.writeByte(message.getSlaveId());
        buf.writeByte(message.getCode().getCode());
        switch (message.getCode()) {
            case ReadCoils: {
                com.github.zzxt0019.modbus.core.request.ReadCoilsRequest request = (com.github.zzxt0019.modbus.core.request.ReadCoilsRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getQuantity());
            }
            break;
            case ReadDiscreteInputs: {
                com.github.zzxt0019.modbus.core.request.ReadDiscreteInputsRequest request = (com.github.zzxt0019.modbus.core.request.ReadDiscreteInputsRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getQuantity());
            }
            break;
            case ReadHoldingRegisters: {
                com.github.zzxt0019.modbus.core.request.ReadHoldingRegistersRequest request = (com.github.zzxt0019.modbus.core.request.ReadHoldingRegistersRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getQuantity());
            }
            break;
            case ReadInputRegisters: {
                com.github.zzxt0019.modbus.core.request.ReadInputRegistersRequest request = (ReadInputRegistersRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getQuantity());
            }
            break;
            case WriteSingleCoil: {
                com.github.zzxt0019.modbus.core.request.WriteSingleCoilRequest request = (com.github.zzxt0019.modbus.core.request.WriteSingleCoilRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getValue().getIntValue());
            }
            break;
            case WriteSingleRegister: {
                com.github.zzxt0019.modbus.core.request.WriteSingleRegisterRequest request = (com.github.zzxt0019.modbus.core.request.WriteSingleRegisterRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getValue());
            }
            break;
            case WriteMultipleCoils: {
                com.github.zzxt0019.modbus.core.request.WriteMultipleCoilsRequest request = (com.github.zzxt0019.modbus.core.request.WriteMultipleCoilsRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getQuantity());
                buf.writeByte(request.getBytes().length);
                buf.writeBytes(request.getBytes());
            }
            break;
            case WriteMultipleRegisters: {
                com.github.zzxt0019.modbus.core.request.WriteMultipleRegistersRequest request = (com.github.zzxt0019.modbus.core.request.WriteMultipleRegistersRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getValue().length / 2);
                buf.writeByte(request.getValue().length);
                buf.writeBytes(request.getValue());
            }
            break;
            case MaskWriteRegister: {
                com.github.zzxt0019.modbus.core.request.MaskWriteRegisterRequest request = (com.github.zzxt0019.modbus.core.request.MaskWriteRegisterRequest) message;
                buf.writeShort(request.getAddress());
                buf.writeShort(request.getAndMask());
                buf.writeShort(request.getOrMask());
            }
            break;
            case ReadWriteMultipleRegisters: {
                com.github.zzxt0019.modbus.core.request.ReadWriteMultipleRegistersRequest request = (com.github.zzxt0019.modbus.core.request.ReadWriteMultipleRegistersRequest) message;
                buf.writeShort(request.getReadAddress());
                buf.writeShort(request.getReadQuantity());
                buf.writeShort(request.getWriteAddress());
                buf.writeShort(request.getWriteValue().length / 2);
                buf.writeByte(request.getWriteValue().length);
                buf.writeBytes(request.getWriteValue());
            }
            break;
        }
        buf.setShort(4, buf.readableBytes() - 6);
        return buf;
    }

    public ByteBuf encode(com.github.zzxt0019.modbus.core.response.ModbusResponse<?, ?> message) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeShort(message.getMsgCount());
        buf.writeBytes(new byte[]{0, 0, 0, 0});
        buf.writeByte(message.getSlaveId());
        buf.writeByte(message.getCode().getCode());
        switch (message.getCode()) {
            case ReadCoils: {
                com.github.zzxt0019.modbus.core.response.ReadCoilsResponse response = (com.github.zzxt0019.modbus.core.response.ReadCoilsResponse) message;
                buf.writeByte(response.getBytes().length);
                buf.writeBytes(response.getBytes());
            }
            break;
            case ReadDiscreteInputs: {
                com.github.zzxt0019.modbus.core.response.ReadDiscreteInputsResponse response = (com.github.zzxt0019.modbus.core.response.ReadDiscreteInputsResponse) message;
                buf.writeByte(response.getBytes().length);
                buf.writeBytes(response.getBytes());
            }
            break;
            case ReadHoldingRegisters: {
                com.github.zzxt0019.modbus.core.response.ReadHoldingRegistersResponse response = (com.github.zzxt0019.modbus.core.response.ReadHoldingRegistersResponse) message;
                buf.writeByte(response.getValue().length);
                buf.writeBytes(response.getValue());
            }
            break;
            case ReadInputRegisters: {
                com.github.zzxt0019.modbus.core.response.ReadInputRegistersResponse response = (ReadInputRegistersResponse) message;
                buf.writeByte(response.getValue().length);
                buf.writeBytes(response.getValue());
            }
            break;
            case WriteSingleCoil: {
                com.github.zzxt0019.modbus.core.response.WriteSingleCoilResponse response = (com.github.zzxt0019.modbus.core.response.WriteSingleCoilResponse) message;
                buf.writeShort(response.getAddress());
                buf.writeShort(response.getValue().getIntValue());
            }
            break;
            case WriteSingleRegister: {
                com.github.zzxt0019.modbus.core.response.WriteSingleRegisterResponse response = (com.github.zzxt0019.modbus.core.response.WriteSingleRegisterResponse) message;
                buf.writeShort(response.getAddress());
                buf.writeShort(response.getData());
            }
            break;
            case WriteMultipleCoils: {
                com.github.zzxt0019.modbus.core.response.WriteMultipleCoilsResponse response = (com.github.zzxt0019.modbus.core.response.WriteMultipleCoilsResponse) message;
                buf.writeShort(response.getAddress());
                buf.writeShort(response.getQuantity());
            }
            break;
            case WriteMultipleRegisters: {
                com.github.zzxt0019.modbus.core.response.WriteMultipleRegistersResponse response = (com.github.zzxt0019.modbus.core.response.WriteMultipleRegistersResponse) message;
                buf.writeShort(response.getAddress());
                buf.writeShort(response.getQuantity());
            }
            break;
            case MaskWriteRegister: {
                com.github.zzxt0019.modbus.core.response.MaskWriteRegisterResponse response = (com.github.zzxt0019.modbus.core.response.MaskWriteRegisterResponse) message;
                buf.writeShort(response.getAddress());
                buf.writeShort(response.getAndMask());
                buf.writeShort(response.getOrMask());
            }
            break;
            case ReadWriteMultipleRegisters: {
                com.github.zzxt0019.modbus.core.response.ReadWriteMultipleRegistersResponse response = (com.github.zzxt0019.modbus.core.response.ReadWriteMultipleRegistersResponse) message;
                buf.writeByte(response.getReadValue().length);
                buf.writeBytes(response.getReadValue());
            }
            break;
        }
        buf.setShort(4, buf.readableBytes() - 6);
        return buf;
    }

    public ByteBuf encode(ErrorResponse message) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeShort(message.getMsgCount());
        buf.writeBytes(new byte[]{0, 0, 0, 0});
        buf.writeByte(message.getSlaveId());
        buf.writeByte(message.getCode().getCode());

        buf.writeByte(message.getExceptionCode().getCode());

        buf.setShort(4, buf.readableBytes() - 6);
        return buf;
    }
}
