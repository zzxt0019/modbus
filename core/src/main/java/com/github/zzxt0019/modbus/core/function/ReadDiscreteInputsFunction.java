package com.github.zzxt0019.modbus.core.function;

@FunctionalInterface
public interface ReadDiscreteInputsFunction {
    boolean[] read(int address, int quantity);
}
