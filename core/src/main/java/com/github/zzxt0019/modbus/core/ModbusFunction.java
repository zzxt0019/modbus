package com.github.zzxt0019.modbus.core;

import com.github.zzxt0019.codec.modbus.request.ModbusRequest;
import com.github.zzxt0019.codec.modbus.response.ModbusResponse;

@FunctionalInterface
public interface ModbusFunction<REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> {
    RES apply(REQ t) throws ModbusException;
}

