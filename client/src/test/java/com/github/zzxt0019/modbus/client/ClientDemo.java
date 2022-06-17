package com.github.zzxt0019.modbus.client;

import com.github.zzxt0019.codec.modbus.request.*;
import com.github.zzxt0019.modbus.client.handler.MaskWriteRegisterClientHandler;
import com.github.zzxt0019.modbus.core.ModbusException;

public class ClientDemo {
    public static void main(String[] args) throws Exception {
        try {
            Client client = ClientFactory.builder("127.0.0.1").setPort(505)
                    .setVClient(VClientFactory.builder()
                            .setMaxSize(256)
                            .build())
                    .build();
            Thread.sleep(100);
            //ReadHoldingRegistersResponse(value=[0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 0, 0, 1, 0, 3, 0, 0, 123, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
            //ReadHoldingRegistersResponse(value=[0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 0, 0, 1, 0, 3, 0, 0, 123, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
//ReadHoldingRegistersResponse(value=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 123, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
//ReadHoldingRegistersResponse(value=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 123, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])            Thread.sleep(100);
//ReadHoldingRegistersResponse(value=[0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 123, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
//            client.sendSync(new WriteMultipleCoilsRequest(3, new boolean[]{true, false, true}));
//            client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//            client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//            client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//            System.out.println(client.sendSync(new ReadCoilsRequest(100, 500)));
//           client.sendSync(new ReadWriteMultipleRegistersRequest(10, 10, 10,
//                    new byte[]{0, 11, 0, 22, 0, 33, 0, 44, 0, 55, 0, 66, 0, 77, 0, 88, 0, 99, 0, 100}));
            client.sendSync(new WriteSingleRegisterRequest(9, (short)123));
            client.sendSync(new WriteMultipleRegistersRequest(8, new byte[]{3, 0}));
            client.sendSync(new WriteSingleRegisterRequest(7, (short) 256));
            client.sendSync(new WriteSingleRegisterRequest(5, (short) 0x12));
            client.sendSync(new MaskWriteRegisterRequest(5, (short) 0xf2, (short) 0x25));
//            client.sendSync(new MaskWriteRegisterRequest(7, (short) 10, (short) 11));
            System.out.println(client.sendSync(new ReadHoldingRegistersRequest(1, 19)));
//                .exceptionally(new Function<Throwable, Void>() {
//                    @Override
//                    public Void apply(Throwable throwable) {
//                        System.out.println(throwable);
//                        return null;
//                    }
//                });
//            System.out.println(client.sendSync(new ReadCoilsRequest(0, 10)));

//            client.sendAsync(new ReadCoilsRequest(0, 10)).thenAccept(System.out::println);
//            client.sendAsync(new ReadCoilsRequest(0, 10)).thenAccept(System.out::println);
//            client.sendAsync(new ReadCoilsRequest(0, 10)).thenAccept(System.out::println);
//        System.out.println(client.sendSync(new ReadDiscreteInputsRequest(100, 10)));
//        System.out.println(client.sendSync(new ReadDiscreteInputsRequest(100, 10)));
//        System.out.println(client.sendSync(new ReadHoldingRegistersRequest(10000, 2)));
//        System.out.println(client.sendSync(new ReadHoldingRegistersRequest(10000, 2)));
//        System.out.println(client.sendSync(new ReadInputRegistersRequest(100, 10)));

//        System.out.println(client.sendSync(new WriteMultipleCoilsRequest(100, new boolean[]{true, true, true, false, false})));
//        System.out.println(client.sendSync(new WriteMultipleRegistersRequest(100, new byte[]{1, 2, 3, 4})));
//        System.out.println(client.sendSync(new WriteSingleCoilRequest(100, true)));
//        System.out.println(client.sendSync(new WriteSingleRegisterRequest(100, (short) 10)));
//        client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//        client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//        client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//        client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//        Thread.sleep(1000);
//        client.sendAsync(new ReadCoilsRequest(100, 10)).thenAccept(System.out::println);
//        System.out.println(client.sendSync(new ReadDiscreteInputsRequest(100, 10)));
//        System.out.println(client.sendSync(new ReadHoldingRegistersRequest(10000, 10)));
//        System.out.println(client.sendSync(new ReadInputRegistersRequest(10000, 10)));
////        System.out.println(response);
//        System.out.println(client.sendSync(new WriteSingleCoilRequest(100, true)));
//        System.out.println(client.sendSync(new WriteSingleRegisterRequest(100, (short) 10)));
//        System.out.println(client.sendSync(new WriteMultipleCoilsRequest(100, new boolean[]{true, false, true})));

            //|00000000| 00 26 00 02 08 ae 08 ae 00 2c 00 37 00 42 00 4d |.&.......,.7.B.M|
            //|00000010| 00 58 00 00                                     |.X..            |
//        System.out.println(ByteBufUtil.prettyHexDump(response.getData()));
        } catch (ModbusException e) {
            System.out.println(e.getReason());
            if (e.getErrorMessage() != null) {
                System.out.println(e.getErrorMessage().getCode());
                System.out.println(e.getErrorMessage().getMsgCount());
                System.out.println(e.getErrorMessage().getSlaveId());
                System.out.println(e.getErrorMessage().getExceptionCode());
            }
            e.printStackTrace();
        }
    }
}
