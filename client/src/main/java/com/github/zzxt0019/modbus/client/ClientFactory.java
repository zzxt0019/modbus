package com.github.zzxt0019.modbus.client;

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

        private Builder(String ip) {
            this.ip = ip;
        }

        public Client build() {
            return new Client(ip, port, vClient);
        }
    }
}
