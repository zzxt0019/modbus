package com.github.zzxt0019.modbus.core.function;

@FunctionalInterface
public interface WriteSingleRegisterFunction {
    void write(int address, short value);
}
