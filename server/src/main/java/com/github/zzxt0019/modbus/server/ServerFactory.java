package com.github.zzxt0019.modbus.server;


import com.github.zzxt0019.modbus.core.ModbusConsumer;
import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.modbus.core.request.*;
import com.github.zzxt0019.modbus.core.response.*;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ServerFactory {
    public static Builder builder(int port) {
        return new Builder(port);
    }

    @Accessors(chain = true)
    @Setter
    public static class Builder {
        private final int port;
        private ModbusFunction<ReadCoilsRequest, ReadCoilsResponse> readCoils;
        private ModbusFunction<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> readDiscreteInputs;
        private ModbusFunction<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> readHoldingRegisters;
        private ModbusFunction<ReadInputRegistersRequest, ReadInputRegistersResponse> readInputRegisters;
        private ModbusConsumer<WriteSingleCoilRequest> writeSingleCoil;
        private ModbusConsumer<WriteSingleRegisterRequest> writeSingleRegister;
        private ModbusConsumer<WriteMultipleCoilsRequest> writeMultipleCoils;
        private ModbusConsumer<WriteMultipleRegistersRequest> writeMultipleRegisters;
        private ModbusConsumer<MaskWriteRegisterRequest> maskWriteRegister;
        private ModbusFunction<ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse> readWriteMultipleRegisters;

        private Builder(int port) {
            this.port = port;
        }

        public Server build() {
            return new Server(port, readCoils, readDiscreteInputs, readHoldingRegisters, readInputRegisters, writeSingleCoil, writeSingleRegister, writeMultipleCoils, writeMultipleRegisters, maskWriteRegister, readWriteMultipleRegisters);
        }
    }
}
