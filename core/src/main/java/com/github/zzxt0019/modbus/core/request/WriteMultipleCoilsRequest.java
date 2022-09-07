package com.github.zzxt0019.modbus.core.request;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.response.WriteMultipleCoilsResponse;
import com.github.zzxt0019.modbus.core.utils.ModbusUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriteMultipleCoilsRequest extends ModbusRequest<WriteMultipleCoilsRequest, WriteMultipleCoilsResponse> {
    private int address;
    private int quantity;
    private byte[] bytes;
    private boolean[] bits;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.bits = null;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        this.quantity = bytes.length << 3;
        this.bits = null;
    }

    public void setBits(boolean[] bits) {
        this.bits = bits;
        this.quantity = bits.length;
        this.bytes = null;
    }

    public byte[] getBytes() {
        if (this.bytes == null && this.bits != null) {
            this.bytes = ModbusUtil.bitsToBytes(bits);
        }
        return bytes;
    }

    public boolean[] getBits() {
        if (this.bits == null && this.bytes != null) {
            this.bits = ModbusUtil.bytesToBits(bytes, quantity);
        }
        return bits;
    }

    private WriteMultipleCoilsRequest(int address, int quantity, byte[] bytes, boolean[] bits) {
        super.code = FunctionCode.WriteMultipleCoils;
        this.address = address;
        this.quantity = quantity;
        this.bytes = bytes;
        this.bits = bits;
    }

    public WriteMultipleCoilsRequest(int address, int quantity, byte[] bytes) {
        this(address, quantity, bytes, null);
    }

    public WriteMultipleCoilsRequest(int address, boolean[] bits) {
        this(address, bits.length, null, bits);
    }
}
