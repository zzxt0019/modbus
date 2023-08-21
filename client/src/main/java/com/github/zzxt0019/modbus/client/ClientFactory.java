package com.github.zzxt0019.modbus.client;

import com.github.zzxt0019.netty.decoder.HeadLengthDecoder;
import com.github.zzxt0019.netty.transfer.IntTransfer;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ClientFactory {
    public static Builder builder(String ip) {
        return new Builder(ip);
    }

    @Accessors(chain = true)
    @Setter
    public static class Builder {
        private final String ip;
        private int port = 502;
        private VClient vClient = VClientFactory.builder().build();
        private HeadLengthDecoder decoder = HeadLengthDecoder.builder(new Byte[]{null, null, 0, 0}, IntTransfer.buildDefault16()).build();

        private Builder(String ip) {
            this.ip = ip;
        }

        public Client build() {
            return new Client(ip, port, vClient, decoder);
        }
    }
}
