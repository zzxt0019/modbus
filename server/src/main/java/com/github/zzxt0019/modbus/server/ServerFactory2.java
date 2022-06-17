package com.github.zzxt0019.modbus.server;

import com.github.zzxt0019.codec.modbus.response.*;
import com.github.zzxt0019.modbus.core.function.*;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ServerFactory2 {
    public static Builder builder(int port) {
        return new Builder(port);
    }

    @Accessors(chain = true)
    @Setter
    public static class Builder {
        private final int port;
        private ReadCoilsFunction readCoils;
        private ReadDiscreteInputsFunction readDiscreteInputs;
        private ReadHoldingRegistersFunction readHoldingRegisters;
        private ReadInputRegistersFunction readInputRegisters;
        private WriteSingleCoilFunction writeSingleCoil;
        private WriteSingleRegisterFunction writeSingleRegister;
        private WriteMultipleCoilsFunction writeMultipleCoils;
        private WriteMultipleRegistersFunction writeMultipleRegisters;

        private Builder(int port) {
            this.port = port;
        }

        public Server build() {
            if (writeSingleCoil != null && writeMultipleCoils == null) {
                writeMultipleCoils = (address, value) -> {
                    for (int i = 0; i < value.length; i++) {
                        writeSingleCoil.write(address + i, value[i]);
                    }
                };
            }
            if (writeSingleRegister != null && writeMultipleRegisters == null) {
                writeMultipleRegisters = (address, value) -> {
                    for (int i = 0; i < value.length; i += 2) {
                        writeSingleRegister.write(address + i / 2, (short) ((value[i] << 8) | value[i + 1]));
                    }
                };
            }
            return new Server(port,
                    readCoils == null ? null : req -> new ReadCoilsResponse(readCoils.read(req.getAddress(), req.getQuantity())),
                    readDiscreteInputs == null ? null : req -> new ReadDiscreteInputsResponse(readDiscreteInputs.read(req.getAddress(), req.getQuantity())),
                    readHoldingRegisters == null ? null : req -> new ReadHoldingRegistersResponse(readHoldingRegisters.read(req.getAddress(), req.getQuantity())),
                    readInputRegisters == null ? null : req -> new ReadInputRegistersResponse(readInputRegisters.read(req.getAddress(), req.getQuantity())),
                    writeSingleCoil == null ? null : req -> writeSingleCoil.write(req.getAddress(), req.getValue().getBoolValue()),
                    writeSingleRegister == null ? null : req -> writeSingleRegister.write(req.getAddress(), req.getValue()),
                    writeMultipleCoils == null ? null : req -> writeMultipleCoils.write(req.getAddress(), req.getBits()),
                    writeMultipleRegisters == null ? null : req -> writeMultipleRegisters.write(req.getAddress(), req.getValue()),
                    writeSingleRegister == null || readHoldingRegisters == null ? null : req -> {
                        byte[] read = readHoldingRegisters.read(req.getAddress(), 1);
                        int current = (read[0] << 8) | read[1];
                        int andMask = Short.toUnsignedInt(req.getAndMask());
                        int orMask = Short.toUnsignedInt(req.getOrMask());
                        int result = (current & andMask) | (orMask & (~andMask));
                        writeSingleRegister.write(req.getAddress(), (short) result);
                    },
                    readHoldingRegisters == null || writeMultipleRegisters == null ? null : req -> {
                        writeMultipleRegisters.write(req.getWriteAddress(), req.getWriteValue());
                        return new ReadWriteMultipleRegistersResponse(readHoldingRegisters.read(req.getReadAddress(), req.getReadQuantity()));
                    }
            );
        }
    }

}
