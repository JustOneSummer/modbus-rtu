package link.linxun.modbus.serial;

/**
 * 串口配置
 *
 * @author LinXun
 * @date 2021/7/20 13:44 星期二
 */
public class SerialPort {
    private final String port;
    private final int baud;
    private final int parity;
    private final int dataBits;
    private final int stopBits;

    public SerialPort(String port) {
        this.port = port;
        this.baud = 115200;
        this.parity = gnu.io.SerialPort.PARITY_NONE;
        this.dataBits = gnu.io.SerialPort.DATABITS_8;
        this.stopBits = gnu.io.SerialPort.STOPBITS_1;
    }

    public SerialPort(String port, int baud) {
        this.port = port;
        this.baud = baud;
        this.parity = gnu.io.SerialPort.PARITY_NONE;
        this.dataBits = gnu.io.SerialPort.DATABITS_8;
        this.stopBits = gnu.io.SerialPort.STOPBITS_1;
    }


    public SerialPort(String port, int baud, int parity) {
        this.port = port;
        this.baud = baud;
        this.parity = parity;
        this.dataBits = gnu.io.SerialPort.DATABITS_8;
        this.stopBits = gnu.io.SerialPort.STOPBITS_1;
    }


    public SerialPort(String port, int baud, int parity, int dataBits) {
        this.port = port;
        this.baud = baud;
        this.parity = parity;
        this.dataBits = dataBits;
        this.stopBits = gnu.io.SerialPort.STOPBITS_1;
    }

    public SerialPort(String port, int baud, int parity, int dataBits, int stopBits) {
        this.port = port;
        this.baud = baud;
        this.parity = parity;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
    }

    public String getPort() {
        return port;
    }

    public int getBaud() {
        return baud;
    }

    public int getParity() {
        return parity;
    }

    public int getDataBits() {
        return dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }
}
