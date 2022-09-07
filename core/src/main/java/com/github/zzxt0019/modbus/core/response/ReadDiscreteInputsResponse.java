package com.github.zzxt0019.modbus.core.response;

import com.github.zzxt0019.modbus.core.enums.FunctionCode;
import com.github.zzxt0019.modbus.core.request.ReadDiscreteInputsRequest;
import com.github.zzxt0019.modbus.core.utils.ModbusUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReadDiscreteInputsResponse extends ModbusResponse<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> {
    private int quantity;  // 临时
    private byte[] bytes;  // 封装数据
    private boolean[] bits;  // 解析数据

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
        return this.bytes;
    }

    public boolean[] getBits() {
        if (this.bits == null && this.bytes != null) {
            this.bits = ModbusUtil.bytesToBits(this.bytes, this.quantity);
        }
        return this.bits;
    }

    private ReadDiscreteInputsResponse(int quantity, byte[] bytes, boolean[] bits) {
        super.code = FunctionCode.ReadDiscreteInputs;
        this.quantity = quantity;
        this.bytes = bytes;
        this.bits = bits;
    }

    public ReadDiscreteInputsResponse(int quantity, byte[] bytes) {
        this(quantity, bytes, null);
    }

    public ReadDiscreteInputsResponse(byte[] bytes) {
        this(bytes.length << 3, bytes, null);
    }

    public ReadDiscreteInputsResponse(boolean[] bits) {
        this(bits.length, null, bits);
    }
}
