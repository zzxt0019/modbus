package com.github.zzxt0019.modbus.server;

import com.github.zzxt0019.codec.modbus.response.ReadCoilsResponse;
import com.github.zzxt0019.codec.modbus.response.ReadDiscreteInputsResponse;
import com.github.zzxt0019.codec.modbus.response.ReadHoldingRegistersResponse;
import com.github.zzxt0019.codec.modbus.response.ReadInputRegistersResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 打包Server
 * 将ServerDemo复制到Sources Root
 * 复制build.xml到pom.xml中
 * 启动: "java -jar modbus-server.jar 端口号"
 * 默认端口号 502
 */
public class ServerDemo {
    // 为方便DiscreteInput和InputRegister数据配置, 令
    //   Coil 和 DiscreteInput 同数据
    //   HoldingRegister 和 InputRegister 同数据
    public static void main(String[] args) throws Exception {
        Map<Integer, Boolean> map1 = new HashMap<>();
        Map<Integer, Short> map2 = new HashMap<>();
        int port = 502;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Server server = ServerFactory.builder(port)
                .setReadCoils(request -> {
                    boolean[] booleans = new boolean[request.getQuantity()];
                    for (int i = 0; i < request.getQuantity(); i++) {
                        if (map1.containsKey(i + request.getAddress())) {
                            booleans[i - request.getAddress()] = map1.get(i);
                        }
                    }
                    return new ReadCoilsResponse(booleans);
                })
                .setReadDiscreteInputs(request -> {
                    boolean[] booleans = new boolean[request.getQuantity()];
                    for (int i = 0; i < request.getQuantity(); i++) {
                        if (map1.containsKey(i + request.getAddress())) {
                            booleans[i] = map1.get(i);
                        }
                    }
                    return new ReadDiscreteInputsResponse(booleans);
                })
                .setReadHoldingRegisters(request -> {
                    byte[] bytes = new byte[request.getQuantity() << 1];
                    for (int i = 0; i < request.getQuantity(); i++) {
                        if (map2.containsKey(request.getAddress() + i)) {
                            bytes[i * 2] = (byte) ((map2.get(request.getAddress() + i) & 0xFF00) >>> 8);
                            bytes[i * 2 + 1] = (byte) (map2.get(request.getAddress() + i) & 0x00FF);
                        }
                    }
                    return new ReadHoldingRegistersResponse(bytes);
                })
                .setReadInputRegisters(request -> {
                    byte[] bytes = new byte[request.getQuantity() << 1];
                    for (int i = 0; i < request.getQuantity(); i++) {
                        if (map2.containsKey(request.getAddress() + i)) {
                            bytes[i * 2] = (byte) ((map2.get(request.getAddress() + i) & 0xFF00) >>> 8);
                            bytes[i * 2 + 1] = (byte) (map2.get(request.getAddress() + i) & 0x00FF);
                        }
                    }
                    return new ReadInputRegistersResponse(bytes);
                })
                .setWriteSingleCoil(request -> {
                    map1.put(request.getAddress(), request.getValue().getBoolValue());
                })
                .setWriteSingleRegister(request -> {
                    map2.put(request.getAddress(), request.getValue());
                })
                .setWriteMultipleCoils(request -> {
                    for (int i = 0; i < request.getBits().length; i++) {
                        map1.put(request.getAddress() + i, request.getBits()[i]);
                    }
                })
                .setWriteMultipleRegisters(request -> {
                    for (int i = 0; i < request.getValue().length; i += 2) {
                        map2.put(request.getAddress() + i / 2, (short) (((request.getValue()[i] << 8) & 0xFF00) | (request.getValue()[i + 1] & 0x00FF)));
                    }
                })
                .build();
        server.init();
    }
}