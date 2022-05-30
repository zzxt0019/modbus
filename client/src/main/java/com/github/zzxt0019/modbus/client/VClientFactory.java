package com.github.zzxt0019.modbus.client;

import lombok.Setter;
import lombok.experimental.Accessors;

public class VClientFactory {
    public static Builder builder() {
        return new Builder();
    }

    @Setter
    @Accessors(chain = true)
    public static class Builder {
        private int maxSize = 128;
        private byte slaveId = 1;
        private long requestTimeout = 10000;
        private long responseTimeout = 10000;

        public Builder setMaxSize(int maxSize) {
            if (maxSize > Short.MAX_VALUE - Short.MIN_VALUE) {
                this.maxSize = Short.MAX_VALUE - Short.MIN_VALUE;
            } else {
                this.maxSize = maxSize;
            }
            return this;
        }

        public VClient build() {
            return new VClient(maxSize, slaveId, requestTimeout, responseTimeout);
        }
    }
}
