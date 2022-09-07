package com.github.zzxt0019.modbus.client;

import com.github.zzxt0019.modbus.core.ModbusMessage;
import com.github.zzxt0019.modbus.core.errorres.ErrorResponse;
import com.github.zzxt0019.modbus.core.request.ModbusRequest;
import com.github.zzxt0019.modbus.core.response.ModbusResponse;
import com.github.zzxt0019.modbus.core.ExceptionCause;
import com.github.zzxt0019.modbus.core.ModbusException;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.*;

public class VClient {
    private final int maxSize;
    private final byte slaveId;
    private final long requestTimeout;
    private final long responseTimeout;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    VClient(int maxSize, byte slaveId, long requestTimeout, long responseTimeout) {
        this.maxSize = maxSize;
        this.slaveId = slaveId;
        this.requestTimeout = requestTimeout;
        this.responseTimeout = responseTimeout;
    }

    private <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> int send(Channel channel, REQ request) throws ModbusException {
        if (!channel.isActive()) {
            throw new ModbusException(ExceptionCause.NotConnected);
        }
        long startTime = System.currentTimeMillis();
        Integer tempCount = channel.attr(AttributeKey.<Integer>valueOf("com.github.zzxt0019.modbus.MessageCount")).get();
        int msgCount = tempCount == null ? 0 : tempCount;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        while (true) {
            for (; msgCount < maxSize; msgCount++) {
                if (channel.attr(AttributeKey.<CountDownLatch>valueOf("com.github.zzxt0019.modbus.Lock-" + msgCount)).setIfAbsent(countDownLatch) == null) {
                    channel.attr(AttributeKey.<Integer>valueOf("com.github.zzxt0019.modbus.MessageCount")).set(msgCount);
                    request.setMsgCount((short) msgCount);
                    request.setSlaveId(slaveId);
                    channel.attr(AttributeKey.<ModbusMessage>valueOf(String.valueOf(msgCount))).set(request);
                    channel.writeAndFlush(request);
                    return msgCount;
                }
            }
            if (System.currentTimeMillis() - startTime > this.requestTimeout) {
                throw new ModbusException(ExceptionCause.RequestTimeout);
            }
            msgCount = 0;
        }
    }

    private <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> RES receive(Channel channel, int msgCount) throws ModbusException {
        try {
            if ((channel.attr(AttributeKey.<CountDownLatch>valueOf("com.github.zzxt0019.modbus.Lock-" + msgCount)).get()).await(this.responseTimeout, TimeUnit.MILLISECONDS)) {
                ModbusMessage response = channel.attr(AttributeKey.<ModbusMessage>valueOf(String.valueOf(msgCount))).get();
                if (response.getClass().equals(ErrorResponse.class)) {
                    ErrorResponse errorResponse = (ErrorResponse) response;
                    throw new ModbusException(ExceptionCause.ErrorMessage, errorResponse);
                } else {
                    return (RES) response;
                }
            } else {
                throw new ModbusException(ExceptionCause.ResponseTimeout);
            }
        } catch (InterruptedException e) {
            return null;
        } finally {
            channel.attr(AttributeKey.<CountDownLatch>valueOf("com.github.zzxt0019.modbus.Lock-" + msgCount)).set(null);
            channel.attr(AttributeKey.<ModbusMessage>valueOf(String.valueOf(msgCount))).set(null);
        }
    }

    public <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> CompletableFuture<RES> sendAsync(Channel channel, REQ request) throws ModbusException {
        int msgCount = send(channel, request);
        CompletableFuture<RES> future = new CompletableFuture<>();
        executorService.execute(() -> {
            try {
                future.complete(receive(channel, msgCount));
            } catch (ModbusException e) {
                future.completeExceptionally(new ModbusException(e.getReason(), e.getErrorMessage()));
            }
        });
        return future;
    }

    public <REQ extends ModbusRequest<REQ, RES>, RES extends ModbusResponse<REQ, RES>> RES sendSync(Channel channel, REQ request) throws ModbusException {
        return receive(channel, send(channel, request));
    }
}
