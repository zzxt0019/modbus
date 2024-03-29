package com.github.zzxt0019.modbus.client;

import com.github.zzxt0019.modbus.client.handler.*;
import com.github.zzxt0019.modbus.core.ModbusException;
import com.github.zzxt0019.modbus.core.request.ModbusRequest;
import com.github.zzxt0019.modbus.core.response.ModbusResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
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
    private final Runnable succeed;
    private final Runnable errored;
    private final Runnable ended;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    Client(ClientFactory.Builder builder) {
        this.ip = builder.ip;
        this.port = builder.port;
        this.vClient = builder.vClient;
        this.succeed = builder.succeed;
        this.errored = builder.errored;
        this.ended = builder.ended;
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
                        if (builder.decoder != null) {
                            ch.pipeline().addLast(builder.decoder);
                        }
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
    }

    public ChannelFuture connect() {
        ChannelFuture channelFuture = bootstrap.connect(ip, port)
                .addListener((ChannelFuture listener) -> {
                    if (!listener.isSuccess()) {
                        if (errored != null) {
                            errored.run();
                        }
                    } else {
                        if (succeed != null) {
                            succeed.run();
                        }
                    }
                });
        channel = channelFuture
                .channel();
        channel.closeFuture()
                .addListener((ChannelFutureListener) future -> {
                    if (ended != null) {
                        ended.run();
                    }
                    if (!group.isShuttingDown() && !group.isShutdown()) {
                        if (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                            executorService.execute(this::connect);
                        }
                    }
                });
        return channelFuture;
    }

    public void close() {
        group.shutdownGracefully();
        executorService.shutdown();
    }

    public boolean isConnected() {
        return channel.isActive();
    }

    public <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> CompletableFuture<RES> sendAsync(REQ request) throws ModbusException {
        return vClient.sendAsync(channel, request);
    }

    public <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> RES sendSync(REQ request) throws ModbusException {
        return vClient.sendSync(channel, request);
    }
}
