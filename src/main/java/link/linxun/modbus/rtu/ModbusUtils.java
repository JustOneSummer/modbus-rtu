package link.linxun.modbus.rtu;

import gnu.io.NRSerialPort;
import link.linxun.modbus.config.ModbusFunctionCode;
import link.linxun.modbus.err.TimeOutException;
import link.linxun.modbus.utils.CrcUtils;
import link.linxun.modbus.utils.NumberUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

/**
 * modbus读写工具
 *
 * @author LinXun
 * @date 2021/7/20 14:08 星期二
 */
public class ModbusUtils {
    private ModbusUtils() {
    }

    /**
     * 处理计算CRC
     *
     * @param address 地址
     * @param code    功能码
     * @param data    数据
     * @return 结果
     */
    public static byte[] process(int address, ModbusFunctionCode code, byte[] data) {
        //计算CRC
        byte[] temp = new byte[data.length + 4];
        temp[0] = (byte) address;
        temp[1] = code.getCodeByte();
        int len = 2;
        for (byte d : data) {
            temp[len] = d;
            len++;
        }
        final byte[] bytes = NumberUtils.shortToByte(ByteOrder.LITTLE_ENDIAN, CrcUtils.check(temp, temp.length - 2));
        temp[temp.length - 2] = bytes[1];
        temp[temp.length - 1] = bytes[0];
        return temp;
    }

    /**
     * crc校验
     *
     * @param data 数据
     * @return 结果
     */
    public static boolean crcCheck(byte[] data) {
        final byte one = data[data.length - 2];
        final byte two = data[data.length - 1];
        final byte[] bytes = NumberUtils.shortToByte(ByteOrder.LITTLE_ENDIAN, CrcUtils.check(data, data.length - 2));
        return bytes[1] == one && bytes[0] == two;
    }

    /**
     * 发送
     *
     * @param port    串口
     * @param data    数据
     * @param timeout 应答超时时间
     * @return 结果
     * @throws IOException      串口异常
     * @throws TimeOutException 超时异常
     */
    public static byte[] send(NRSerialPort port, byte[] data, int timeout) throws IOException, TimeOutException {
        final OutputStream stream = port.getOutputStream();
        stream.write(data);
        //等待
        final byte[] accept = accept(port, timeout);
        if (accept.length <= 0) {
            throw new TimeOutException("accept time out");
        }
        return accept;
    }


    /**
     * 接收
     *
     * @param port    串口
     * @param timeout 超时时间毫秒
     * @return 结果数据
     * @throws IOException io超时
     */
    private static byte[] accept(NRSerialPort port, int timeout) throws IOException {
        long time = System.currentTimeMillis();
        final int dataFrame = MasterRtu.dataFrame(port);
        final DataInputStream stream = new DataInputStream(port.getInputStream());
        int available = 0;
        while (!Thread.interrupted()) {
            //尝试读取数据
            boolean tempBool;
            int tempLen = stream.available();
            if (tempLen <= 0) {
                tempBool = timeout(time, timeout);
            } else {
                if (tempLen != available) {
                    available = tempLen;
                    time = System.currentTimeMillis();
                    continue;
                } else {
                    tempBool = timeout(time, dataFrame);
                }
            }
            if (tempBool) {
                byte[] data = new byte[stream.available()];
                stream.readFully(data);
                return data;
            }
        }
        return new byte[]{};
    }

    /**
     * 超时判断
     *
     * @param time    起始时间
     * @param timeout 读等待时间
     * @return 是否超时或结束
     */
    private static boolean timeout(long time, int timeout) {
        //超时判断
        return (System.currentTimeMillis() - time) > timeout;
    }
}
