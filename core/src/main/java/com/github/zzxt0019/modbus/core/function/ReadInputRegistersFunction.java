package com.github.zzxt0019.modbus.core.function;

@FunctionalInterface
public interface ReadInputRegistersFunction {
    byte[] read(int address, int quantity);
}
