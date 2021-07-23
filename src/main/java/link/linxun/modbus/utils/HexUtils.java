package link.linxun.modbus.utils;

import java.nio.ByteBuffer;
import java.util.Locale;

/**
 * hex工具
 *
 * @author LinXun
 * @date 2021/7/20 17:03 星期二
 */
public class HexUtils {
    private HexUtils() {

    }

    /**
     * hex字符串解码-带符号
     *
     * @param hex 字符串-连续的
     * @return 16进制字节
     */
    public static byte[] hexEncodeMark(String hex) {
        return hexEncode(hex.replace("-", ""));
    }

    /**
     * hex字符串解码
     *
     * @param hex 字符串-连续的
     * @return 16进制字节
     */
    public static byte[] hexEncode(String hex) {
        final int index = 2;
        // 奇数位补0
        if (hex.length() % index != 0) {
            hex = "0" + hex;
        }
        final int len = hex.length();
        ByteBuffer buffer = ByteBuffer.allocate(len / index);
        for (int i = 0; i < len; i += index) {
            buffer.put((byte) Short.parseShort(hex.substring(i, i + index), 16));
        }
        return buffer.array();
    }

    /**
     * hex编码 带符号
     *
     * @param b byte
     * @return 十六进制字符
     */
    public static String hexDecodeMark(byte[] b) {
        StringBuilder builder = new StringBuilder();
        for (byte value : b) {
            builder.append(hexDecode(value)).append("-");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString().toUpperCase(Locale.ROOT);
    }

    /**
     * hex编码
     *
     * @param b byte
     * @return 十六进制字符
     */
    public static String hexDecode(byte[] b) {
        StringBuilder builder = new StringBuilder();
        for (byte value : b) {
            builder.append(hexDecode(value));
        }
        return builder.toString().toUpperCase(Locale.ROOT);
    }

    /**
     * hex编码
     *
     * @param b byte
     * @return 十六进制字符
     */
    public static String hexDecode(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            return '0' + hex.toUpperCase(Locale.ROOT);
        }
        return hex.toUpperCase(Locale.getDefault());
    }
}
