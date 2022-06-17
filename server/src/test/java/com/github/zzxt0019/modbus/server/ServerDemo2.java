package com.github.zzxt0019.modbus.server;

import java.util.HashMap;
import java.util.Map;

public class ServerDemo2 {
    public static void main(String[] args) throws Exception {
        Map<Integer, Boolean> map1 = new HashMap<>();
        Map<Integer, Short> map2 = new HashMap<>();
        int port = 505;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Server server = ServerFactory2.builder(port)
                .setReadCoils((address, quantity) -> {
                    boolean[] booleans = new boolean[quantity];
                    for (int i = 0; i < quantity; i++) {
                        if (map1.containsKey(i + address)) {
                            booleans[i - address] = map1.get(i);
                        }
                    }
                    return booleans;
                })
                .setReadDiscreteInputs((address, quantity) -> {
                    boolean[] booleans = new boolean[quantity];
                    for (int i = 0; i < quantity; i++) {
                        if (map1.containsKey(i + address)) {
                            booleans[i - address] = map1.get(i);
                        }
                    }
                    return booleans;
                })
                .setReadHoldingRegisters((address, quantity) -> {
                    byte[] bytes = new byte[quantity << 1];
                    for (int i = 0; i < quantity; i++) {
                        if (map2.containsKey(address + i)) {
                            bytes[i * 2] = (byte) ((map2.get(address + i) & 0xFF00) >>> 8);
                            bytes[i * 2 + 1] = (byte) (map2.get(address + i) & 0x00FF);
                        }
                    }
                    return bytes;
                })
                .setReadInputRegisters((address, quantity) -> {
                    byte[] bytes = new byte[quantity << 1];
                    for (int i = 0; i < quantity; i++) {
                        if (map2.containsKey(address + i)) {
                            bytes[i * 2] = (byte) ((map2.get(address + i) & 0xFF00) >>> 8);
                            bytes[i * 2 + 1] = (byte) (map2.get(address + i) & 0x00FF);
                        }
                    }
                    return bytes;
                })
                .setWriteSingleCoil(map1::put)
                .setWriteSingleRegister(map2::put)
                .build();
        server.init();
    }
}
