package com.github.zzxt0019.modbus.core.function;

@FunctionalInterface
public interface WriteMultipleRegistersFunction {
    void write(int address, byte[] value);
}
