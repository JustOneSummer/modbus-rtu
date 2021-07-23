package link.linxun.modbus.rtu;

import link.linxun.modbus.utils.HexUtils;

/**
 * @author LinXun
 * @date 2021/7/23 14:22 星期五
 */
public class ModbusData {
    private final byte[] send;
    private final byte[] accept;
    private byte[] data;

    public ModbusData(byte[] send, byte[] accept) {
        this.send = send;
        this.accept = accept;
    }

    public ModbusData(byte[] send, byte[] accept, byte[] data) {
        this.send = send;
        this.accept = accept;
        this.data = data;
    }

    public byte[] getSend() {
        return send;
    }

    public String getSendHex() {
        return HexUtils.hexDecodeMark(send);
    }

    public byte[] getAccept() {
        return accept;
    }

    public String getAcceptHex() {
        return HexUtils.hexDecodeMark(accept);
    }

    public byte[] getData() {
        return data;
    }

    public String getDataHex() {
        return HexUtils.hexDecodeMark(data);
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
