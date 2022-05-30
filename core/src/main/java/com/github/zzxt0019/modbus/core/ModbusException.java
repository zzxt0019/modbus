package com.github.zzxt0019.modbus.core;

import com.github.zzxt0019.codec.modbus.errorres.ErrorResponse;
import lombok.Data;

@Data
public class ModbusException extends Exception {
    private final ExceptionCause reason;
    private ErrorResponse errorMessage;

    public ModbusException(ExceptionCause reason) {
        this.reason = reason;
    }

    public ModbusException(ExceptionCause reason, ErrorResponse errorMessage) {
        this.reason = reason;
        this.errorMessage = errorMessage;
    }
}
