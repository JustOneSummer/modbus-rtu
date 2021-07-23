package link.linxun.modbus.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 数字量处理
 *
 * @author LinXun
 * @date 2021/7/21 10:31 星期三
 */
public class NumberUtils {
    private NumberUtils() {

    }

    /**
     * short 转 字节
     *
     * @param order 大小端
     * @param data  数据
     * @return 结果
     */
    public static byte[] shortToByte(ByteOrder order, short data) {
        return ByteBuffer.allocate(2).putShort(data).order(order).array();
    }
}
