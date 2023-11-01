package com.github.zzxt0019.modbus.client;

import com.github.zzxt0019.netty.decoder.HeadLengthDecoder;
import com.github.zzxt0019.netty.transfer.IntTransfer;
import io.netty.channel.ChannelHandler;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ClientFactory {
    public static Builder builder(String ip) {
        return new Builder(ip);
    }

    @Accessors(chain = true)
    @Setter
    public static class Builder {
        final String ip;
        int port = 502;
        VClient vClient = VClientFactory.builder().build();
        ChannelHandler decoder = HeadLengthDecoder.builder(new Byte[]{null, null, 0, 0}, IntTransfer.buildDefault16()).build();
        Runnable succeed;
        Runnable errored;
        Runnable ended;

        private Builder(String ip) {
            this.ip = ip;
        }

        public Client build() {
            return new Client(this);
        }
    }
}
