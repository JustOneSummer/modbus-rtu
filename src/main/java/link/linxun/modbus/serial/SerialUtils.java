package link.linxun.modbus.serial;

import gnu.io.NRSerialPort;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 串口管理
 *
 * @author LinXun
 * @date 2021/7/20 13:41 星期二
 */
public class SerialUtils {
    private SerialUtils() {

    }

    private static final Map<String, NRSerialPort> PORT_MAP = new ConcurrentHashMap<>();

    /**
     * 串口列表
     *
     * @return  com
     */
    public static Set<String> serialPorts() {
        return NRSerialPort.getAvailableSerialPorts();
    }

    /**
     * 获取串口
     *
     * @param com 串口
     * @return null表示不存在
     */
    public static NRSerialPort getPort(String com) {
        return PORT_MAP.get(com);
    }

    /**
     * 打开串口
     *
     * @param port 串口
     * @return 串口
     */
    public static NRSerialPort open(SerialPort port) {
        final NRSerialPort serialPort = PORT_MAP.get(port.getPort());
        if (serialPort != null) {
            return serialPort;
        }
        NRSerialPort p = new NRSerialPort(port.getPort(), port.getBaud(), port.getParity(), port.getDataBits(), port.getStopBits());
        p.connect();
        PORT_MAP.put(port.getPort(), p);
        return p;
    }

    /**
     * 关闭串口
     *
     * @param com 串口
     */
    public static void close(String com) {
        final NRSerialPort port = PORT_MAP.get(com);
        if (port != null) {
            port.disconnect();
        }
    }
}
