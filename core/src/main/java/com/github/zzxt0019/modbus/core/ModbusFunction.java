package com.github.zzxt0019.modbus.core;

import com.github.zzxt0019.modbus.core.request.ModbusRequest;
import com.github.zzxt0019.modbus.core.response.ModbusResponse;

@FunctionalInterface
public interface ModbusFunction<REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> {
    RES apply(REQ t) throws ModbusException;
}

