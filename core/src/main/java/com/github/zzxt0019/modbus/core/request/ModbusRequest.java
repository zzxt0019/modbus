package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.ModbusMessage;
import com.github.zzxt0019.modbus.core.response.ModbusResponse;

public abstract class ModbusRequest<REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> extends ModbusMessage {
}
