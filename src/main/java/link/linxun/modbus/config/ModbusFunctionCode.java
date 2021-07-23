package link.linxun.modbus.config;

import java.util.Arrays;

/**
 * modbus协议
 *
 * @author LinXun
 * @date 2021/7/20 14:01 星期二
 */
public enum ModbusFunctionCode {
    /**
     * 读线圈寄存器
     */
    HEX_01(0x01),
    /**
     * 读离散输入寄存器
     */
    HEX_02(0x02),
    /**
     * 读保持寄存器
     */
    HEX_03(0x03),
    /**
     * 读输入寄存器
     */
    HEX_04(0x04),
    /**
     * 写单个线圈寄存器
     */
    HEX_05(0x05),
    /**
     * 写单个保持寄存器
     */
    HEX_06(0x06),
    /**
     * 写多个线圈寄存器
     */
    HEX_0F(0x0F),
    /**
     * 写多个保持寄存器
     */
    HEX_10(0x10);
    private final int code;

    ModbusFunctionCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public byte getCodeByte() {
        return (byte) code;
    }

    public static byte[] data(byte[] data) {
        switch (data[1]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return copyOfRange(data, 3);
            case 6:
            case 15:
            case 16:
                return copyOfRange(data, 2);
            default:
                return new byte[]{};
        }
    }

    private static byte[] copyOfRange(byte[] data, int index) {
        return Arrays.copyOfRange(data, index, data.length - 2);
    }
}
