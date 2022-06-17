package com.github.zzxt0019.modbus.core.function;

@FunctionalInterface
public interface WriteMultipleCoilsFunction {
    void write(int address, boolean[] value);
}
