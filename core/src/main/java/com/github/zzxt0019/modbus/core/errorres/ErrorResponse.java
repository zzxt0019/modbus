package com.github.zzxt0019.modbus.core.errorres;

import com.github.zzxt0019.modbus.core.ModbusMessage;
import com.github.zzxt0019.modbus.core.enums.ExceptionCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ErrorResponse extends ModbusMessage {
    private com.github.zzxt0019.modbus.core.enums.ExceptionCode exceptionCode;

    public ErrorResponse(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
