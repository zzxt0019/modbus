package com.github.zzxt0019.modbus.core;

import com.github.zzxt0019.modbus.core.enums.ExceptionCode;
import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.enums.SingleCoil;
import com.github.zzxt0019.modbus.core.errorres.ErrorResponse;
import com.github.zzxt0019.modbus.core.request.ReadInputRegistersRequest;
import com.github.zzxt0019.modbus.core.response.ReadInputRegistersResponse;
import io.netty.buffer.ByteBuf;

public class ModbusDecoder {
    public ModbusMessage resDecode(ByteBuf buf) {
        int msgCount = buf.readUnsignedShort();
        buf.readUnsignedShort();  // 00 00
        buf.readUnsignedShort();  // length
        byte slaveId = buf.readByte();
        byte code = buf.readByte();
        switch (FunctionCode.get(code)) {
            case ReadCoils: {
                byte[] bytes = new byte[buf.readUnsignedByte()];
                buf.readBytes(bytes);
                com.github.zzxt0019.modbus.core.response.ReadCoilsResponse response = new com.github.zzxt0019.modbus.core.response.ReadCoilsResponse(bytes);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                // 正确解析后需要response.setQuantity(request.getQuantity());
                // 这样 response的bool数组的长度就可以是quantity而不是8*n, 更精准
                // 如 查询10个, 如果不调用setQuantity(int), 获取到的只能是16位bit, 如果调用了, 这里可以获取10位bit(舍去后面6位无意义的)
                return response;
            }
            case ReadDiscreteInputs: {
                byte[] bytes = new byte[buf.readUnsignedByte()];
                buf.readBytes(bytes);
                com.github.zzxt0019.modbus.core.response.ReadDiscreteInputsResponse response = new com.github.zzxt0019.modbus.core.response.ReadDiscreteInputsResponse(bytes);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case ReadHoldingRegisters: {
                byte[] value = new byte[buf.readUnsignedByte()];
                buf.readBytes(value);
                com.github.zzxt0019.modbus.core.response.ReadHoldingRegistersResponse response = new com.github.zzxt0019.modbus.core.response.ReadHoldingRegistersResponse(value);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case ReadInputRegisters: {
                byte[] value = new byte[buf.readUnsignedByte()];
                buf.readBytes(value);
                com.github.zzxt0019.modbus.core.response.ReadInputRegistersResponse response = new ReadInputRegistersResponse(value);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case WriteSingleCoil: {
                int address = buf.readUnsignedShort();
                int value = buf.readUnsignedShort();
                switch (SingleCoil.get(value)) {
                    case ON:
                    case OFF:
                        com.github.zzxt0019.modbus.core.response.WriteSingleCoilResponse response = new com.github.zzxt0019.modbus.core.response.WriteSingleCoilResponse(address, SingleCoil.get(value).getBoolValue());
                        response.setMsgCount(msgCount);
                        response.setSlaveId(slaveId);
                        return response;
                    case None:
                        return null;
                }
            }
            case WriteSingleRegister: {
                int address = buf.readUnsignedShort();
                short value = buf.readShort();
                com.github.zzxt0019.modbus.core.response.WriteSingleRegisterResponse response = new com.github.zzxt0019.modbus.core.response.WriteSingleRegisterResponse(address, value);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case WriteMultipleCoils: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();
                com.github.zzxt0019.modbus.core.response.WriteMultipleCoilsResponse response = new com.github.zzxt0019.modbus.core.response.WriteMultipleCoilsResponse(address, quantity);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case WriteMultipleRegisters: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();
                com.github.zzxt0019.modbus.core.response.WriteMultipleRegistersResponse response = new com.github.zzxt0019.modbus.core.response.WriteMultipleRegistersResponse(address, quantity);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case MaskWriteRegister: {
                int address = buf.readUnsignedShort();
                short andMask = buf.readShort();
                short orMask = buf.readShort();
                com.github.zzxt0019.modbus.core.response.MaskWriteRegisterResponse response = new com.github.zzxt0019.modbus.core.response.MaskWriteRegisterResponse(address, andMask, orMask);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case ReadWriteMultipleRegisters: {
                byte[] writeValue = new byte[buf.readUnsignedByte()];
                buf.readBytes(writeValue);
                com.github.zzxt0019.modbus.core.response.ReadWriteMultipleRegistersResponse response = new com.github.zzxt0019.modbus.core.response.ReadWriteMultipleRegistersResponse(writeValue);
                response.setMsgCount(msgCount);
                response.setSlaveId(slaveId);
                return response;
            }
            case ReadCoilsError:
            case ReadDiscreteInputsError:
            case ReadHoldingRegistersError:
            case ReadInputRegistersError:
            case WriteSingleCoilError:
            case WriteSingleRegisterError:
            case WriteMultipleCoilsError:
            case WriteMultipleRegistersError:
            case MaskWriteRegisterError:
            case ReadWriteMultipleRegistersError: {
                byte exceptionCode = buf.readByte();
                ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.get(exceptionCode));
                errorResponse.setCode(FunctionCode.get(code));
                errorResponse.setMsgCount(msgCount);
                errorResponse.setSlaveId(slaveId);
                return errorResponse;
            }
            case None:
                return null;
        }
        return null;
    }

    public ModbusMessage reqDecode(ByteBuf buf) {
        int msgCount = buf.readUnsignedShort();
        buf.readUnsignedShort();  // 00
        buf.readUnsignedShort();  // length
        byte slaveId = buf.readByte();
        byte code = buf.readByte();
        switch (FunctionCode.get(code)) {
            case ReadCoils: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();
                com.github.zzxt0019.modbus.core.request.ReadCoilsRequest request = new com.github.zzxt0019.modbus.core.request.ReadCoilsRequest(address, quantity);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case ReadDiscreteInputs: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();
                com.github.zzxt0019.modbus.core.request.ReadDiscreteInputsRequest request = new com.github.zzxt0019.modbus.core.request.ReadDiscreteInputsRequest(address, quantity);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case ReadHoldingRegisters: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();
                com.github.zzxt0019.modbus.core.request.ReadHoldingRegistersRequest request = new com.github.zzxt0019.modbus.core.request.ReadHoldingRegistersRequest(address, quantity);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case ReadInputRegisters: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();
                com.github.zzxt0019.modbus.core.request.ReadInputRegistersRequest request = new ReadInputRegistersRequest(address, quantity);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case WriteSingleCoil: {
                int address = buf.readUnsignedShort();
                int value = buf.readUnsignedShort();
                com.github.zzxt0019.modbus.core.request.WriteSingleCoilRequest request = new com.github.zzxt0019.modbus.core.request.WriteSingleCoilRequest(address, SingleCoil.get(value).getBoolValue());
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case WriteSingleRegister: {
                int address = buf.readUnsignedShort();
                short value = buf.readShort();
                com.github.zzxt0019.modbus.core.request.WriteSingleRegisterRequest request = new com.github.zzxt0019.modbus.core.request.WriteSingleRegisterRequest(address, value);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case WriteMultipleCoils: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();
                byte[] value = new byte[buf.readUnsignedByte()];
                buf.readBytes(value);
                com.github.zzxt0019.modbus.core.request.WriteMultipleCoilsRequest request = new com.github.zzxt0019.modbus.core.request.WriteMultipleCoilsRequest(address, quantity, value);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case WriteMultipleRegisters: {
                int address = buf.readUnsignedShort();
                int quantity = buf.readUnsignedShort();  // quantity
                byte[] value = new byte[buf.readUnsignedByte()];
                buf.readBytes(value);
                com.github.zzxt0019.modbus.core.request.WriteMultipleRegistersRequest request = new com.github.zzxt0019.modbus.core.request.WriteMultipleRegistersRequest(address, value);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                request.setQuantity(quantity);
                return request;
            }
            case MaskWriteRegister: {
                int address = buf.readUnsignedShort();
                short andMask = buf.readShort();
                short orMask = buf.readShort();
                com.github.zzxt0019.modbus.core.request.MaskWriteRegisterRequest request = new com.github.zzxt0019.modbus.core.request.MaskWriteRegisterRequest(address, andMask, orMask);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                return request;
            }
            case ReadWriteMultipleRegisters: {
                int readAddress = buf.readUnsignedShort();
                int readQuantity = buf.readUnsignedShort();
                int writeAddress = buf.readUnsignedShort();
                int writeQuantity = buf.readUnsignedShort();  // writeQuantity
                byte[] writeValue = new byte[buf.readUnsignedByte()];
                buf.readBytes(writeValue);
                com.github.zzxt0019.modbus.core.request.ReadWriteMultipleRegistersRequest request = new com.github.zzxt0019.modbus.core.request.ReadWriteMultipleRegistersRequest(readAddress, readQuantity, writeAddress, writeValue);
                request.setMsgCount(msgCount);
                request.setSlaveId(slaveId);
                request.setReadQuantity(writeQuantity);
                return request;
            }
            case None:
                break;
        }
        return null;
    }
}
