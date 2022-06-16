package com.github.zzxt0019.modbus.client;

import com.github.zzxt0019.codec.modbus.request.ModbusRequest;
import com.github.zzxt0019.codec.modbus.response.ModbusResponse;
import com.github.zzxt0019.modbus.client.handler.*;
import com.github.zzxt0019.modbus.core.ModbusException;
import com.github.zzxt0019.netty.decoder.HeadLengthDecoder;
import com.github.zzxt0019.netty.transfer.IntTransfer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
    private final Bootstrap bootstrap = new Bootstrap();
    private final NioEventLoopGroup group = new NioEventLoopGroup();
    private final String ip;
    private final int port;
    private final VClient vClient;
    private Channel channel;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    Client(String ip, int port, VClient vClient) {
        this.ip = ip;
        this.port = port;
        this.vClient = vClient;
        HeadLengthDecoder headLengthDecoder = HeadLengthDecoder.builder(new Byte[]{null, null, 0, 0}, IntTransfer.buildDefault16()).build();
        MessageClientCodec messageClientCodec = new MessageClientCodec();
        ReadCoilsClientHandler readCoilsClientHandler = new ReadCoilsClientHandler();
        ReadDiscreteInputsClientHandler readDiscreteInputsClientHandler = new ReadDiscreteInputsClientHandler();
        ReadHoldingRegistersClientHandler readHoldingRegistersClientHandler = new ReadHoldingRegistersClientHandler();
        ReadInputRegistersClientHandler readInputRegistersClientHandler = new ReadInputRegistersClientHandler();
        WriteSingleCoilClientHandler writeSingleCoilClientHandler = new WriteSingleCoilClientHandler();
        WriteSingleRegisterClientHandler writeSingleRegisterClientHandler = new WriteSingleRegisterClientHandler();
        WriteMultipleCoilsClientHandler writeMultipleCoilsClientHandler = new WriteMultipleCoilsClientHandler();
        WriteMultipleRegistersClientHandler writeMultipleRegistersClientHandler = new WriteMultipleRegistersClientHandler();
        MaskWriteRegisterClientHandler maskWriteRegisterClientHandler = new MaskWriteRegisterClientHandler();
        ReadWriteMultipleRegistersClientHandler readWriteMultipleRegistersClientHandler = new ReadWriteMultipleRegistersClientHandler();
        MessageExceptionClientHandler messageExceptionClientHandler = new MessageExceptionClientHandler();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                if (msg instanceof ByteBuf) {
                                    System.out.println(ByteBufUtil.prettyHexDump((ByteBuf) msg));

                                }
                                super.write(ctx, msg, promise);
                            }
                        });
                        ch.pipeline().addLast(headLengthDecoder);
                        ch.pipeline().addLast(messageClientCodec);
                        ch.pipeline().addLast(readCoilsClientHandler);
                        ch.pipeline().addLast(readDiscreteInputsClientHandler);
                        ch.pipeline().addLast(readHoldingRegistersClientHandler);
                        ch.pipeline().addLast(readInputRegistersClientHandler);
                        ch.pipeline().addLast(writeSingleCoilClientHandler);
                        ch.pipeline().addLast(writeSingleRegisterClientHandler);
                        ch.pipeline().addLast(writeMultipleCoilsClientHandler);
                        ch.pipeline().addLast(writeMultipleRegistersClientHandler);
                        ch.pipeline().addLast(maskWriteRegisterClientHandler);
                        ch.pipeline().addLast(readWriteMultipleRegistersClientHandler);
                        ch.pipeline().addLast(messageExceptionClientHandler);
                    }
                });
        connect();
    }

    private void connect() {
        channel = bootstrap.connect(ip, port)
                .addListener((ChannelFuture listener) -> {
                    if (!listener.isSuccess()) {
                        System.out.println("连接失败");
                    } else {
                        System.out.println("连接成功");
                    }
                })
                .channel();
        channel.closeFuture()
                .addListener((ChannelFutureListener) future -> {
                    System.out.println("结束");
                    if (!group.isShuttingDown() && !group.isShutdown()) {
                        if (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                            executorService.execute(this::connect);
                        }
                    }
                });
    }

    public void close() {
        group.shutdownGracefully();
    }

    public <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> CompletableFuture<RES> sendAsync(REQ request) throws ModbusException {
        return vClient.sendAsync(channel, request);
    }

    public <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> RES sendSync(REQ request) throws ModbusException {
        return vClient.sendSync(channel, request);
    }
}
