package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.ModbusMessage;
import com.github.zzxt0019.modbus.core.request.ModbusRequest;

public abstract class ModbusResponse<REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> extends ModbusMessage {
}
