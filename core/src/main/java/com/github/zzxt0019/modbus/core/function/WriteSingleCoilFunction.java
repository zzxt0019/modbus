package com.github.zzxt0019.modbus.core.function;

@FunctionalInterface
public interface WriteSingleCoilFunction {
    void write(int address, boolean value);
}
