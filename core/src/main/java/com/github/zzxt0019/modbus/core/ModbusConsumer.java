package com.github.zzxt0019.modbus.core;

import com.github.zzxt0019.codec.modbus.request.ModbusRequest;

@FunctionalInterface
public interface ModbusConsumer<REQ extends ModbusRequest<REQ, ?>> {
    void accept(REQ req) throws ModbusException;
}
