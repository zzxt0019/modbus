package com.github.zzxt0019.modbus.server;

import com.github.zzxt0019.codec.modbus.request.*;
import com.github.zzxt0019.codec.modbus.response.*;
import com.github.zzxt0019.modbus.core.ModbusConsumer;
import com.github.zzxt0019.modbus.core.ModbusFunction;
import com.github.zzxt0019.modbus.server.handler.*;
import com.github.zzxt0019.netty.decoder.HeadLengthDecoder;
import com.github.zzxt0019.netty.transfer.IntTransfer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Server {

    private final int port;
    private final HeadLengthDecoder headLengthDecoder;
    private final ModbusServerCodec modbusServerCodec;
    private final CheckInputServerHandler checkInputServerHandler;
    private final ReadCoilsServerHandler readCoilsServerHandler;
    private final ReadDiscreteInputsServerHandler readDiscreteInputsServerHandler;
    private final ReadHoldingRegistersServerHandler readHoldingRegistersServerHandler;
    private final ReadInputRegistersServerHandler readInputRegistersServerHandler;
    private final WriteSingleCoilServerHandler writeSingleCoilServerHandler;
    private final WriteSingleRegisterServerHandler writeSingleRegisterServerHandler;
    private final WriteMultipleCoilsServerHandler writeMultipleCoilsServerHandler;
    private final WriteMultipleRegistersServerHandler writeMultipleRegistersServerHandler;
    private final MaskWriteRegisterServerHandler maskWriteRegisterServerHandler;
    private final ReadWriteMultipleRegistersServerHandler readWriteMultipleRegistersServerHandler;

    Server(
            int port,
            ModbusFunction<ReadCoilsRequest, ReadCoilsResponse> readCoilsFunction,
            ModbusFunction<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> readDiscreteInputsFunction,
            ModbusFunction<ReadHoldingRegistersRequest, ReadHoldingRegistersResponse> readHoldingRegistersFunction,
            ModbusFunction<ReadInputRegistersRequest, ReadInputRegistersResponse> readInputRegistersFunction,
            ModbusConsumer<WriteSingleCoilRequest> writeSingleCoilConsumer,
            ModbusConsumer<WriteSingleRegisterRequest> writeSingleRegisterConsumer,
            ModbusConsumer<WriteMultipleCoilsRequest> writeMultipleCoilsConsumer,
            ModbusConsumer<WriteMultipleRegistersRequest> writeMultipleRegistersConsumer,
            ModbusConsumer<MaskWriteRegisterRequest> maskWriteRegisterRequestConsumer,
            ModbusFunction<ReadWriteMultipleRegistersRequest, ReadWriteMultipleRegistersResponse> readWriteMultipleFunction
    ) {
        this.port = port;
        headLengthDecoder = HeadLengthDecoder.builder(new Byte[]{null, null, 0, 0}, IntTransfer.buildDefault16()).build();
        modbusServerCodec = new ModbusServerCodec();
        checkInputServerHandler = new CheckInputServerHandler();
        readCoilsServerHandler = new ReadCoilsServerHandler(readCoilsFunction);
        readDiscreteInputsServerHandler = new ReadDiscreteInputsServerHandler(readDiscreteInputsFunction);
        readHoldingRegistersServerHandler = new ReadHoldingRegistersServerHandler(readHoldingRegistersFunction);
        readInputRegistersServerHandler = new ReadInputRegistersServerHandler(readInputRegistersFunction);
        writeSingleCoilServerHandler = new WriteSingleCoilServerHandler(writeSingleCoilConsumer);
        writeSingleRegisterServerHandler = new WriteSingleRegisterServerHandler(writeSingleRegisterConsumer);
        writeMultipleCoilsServerHandler = new WriteMultipleCoilsServerHandler(writeMultipleCoilsConsumer);
        writeMultipleRegistersServerHandler = new WriteMultipleRegistersServerHandler(writeMultipleRegistersConsumer);
        maskWriteRegisterServerHandler = new MaskWriteRegisterServerHandler(maskWriteRegisterRequestConsumer);
        readWriteMultipleRegistersServerHandler = new ReadWriteMultipleRegistersServerHandler(readWriteMultipleFunction);
    }

    public void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.channel(NioServerSocketChannel.class)
                    .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    // 返回丢包
                    .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, new WriteBufferWaterMark(1, 1024 * 1024 * 8))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(headLengthDecoder);
                            ch.pipeline().addLast(modbusServerCodec);
                            ch.pipeline().addLast(checkInputServerHandler);
                            ch.pipeline().addLast(readCoilsServerHandler);
                            ch.pipeline().addLast(readDiscreteInputsServerHandler);
                            ch.pipeline().addLast(readHoldingRegistersServerHandler);
                            ch.pipeline().addLast(readInputRegistersServerHandler);
                            ch.pipeline().addLast(writeSingleCoilServerHandler);
                            ch.pipeline().addLast(writeSingleRegisterServerHandler);
                            ch.pipeline().addLast(writeMultipleCoilsServerHandler);
                            ch.pipeline().addLast(writeMultipleRegistersServerHandler);
                            ch.pipeline().addLast(maskWriteRegisterServerHandler);
                            ch.pipeline().addLast(readWriteMultipleRegistersServerHandler);
                        }
                    });
            ChannelFuture f = b.bind(this.port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
