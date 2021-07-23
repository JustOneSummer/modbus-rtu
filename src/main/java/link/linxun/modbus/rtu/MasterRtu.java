package link.linxun.modbus.rtu;

import gnu.io.NRSerialPort;
import link.linxun.modbus.config.ModbusFunctionCode;
import link.linxun.modbus.err.CrcException;
import link.linxun.modbus.err.TimeOutException;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 主站
 *
 * @author LinXun
 * @date 2021/7/20 13:58 星期二
 */
public class MasterRtu {
    private static int timeout = 500;

    private MasterRtu() {
    }

    /**
     * 01 读线圈寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 起始地址
     * @param numberRegisters 寄存器数量
     * @return 结果
     */
    public static ModbusData reaCoil(NRSerialPort port, int address, int startingAddress, int numberRegisters) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_01, toByte(startingAddress, numberRegisters));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }

    /**
     * 02 读离散输入寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 起始地址
     * @param numberRegisters 寄存器数量
     * @return 结果
     */
    public static ModbusData readDiscreteInputRegister(NRSerialPort port, int address, int startingAddress, int numberRegisters) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_02, toByte(startingAddress, numberRegisters));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }


    /**
     * 03 读保持寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 起始地址
     * @param numberRegisters 寄存器数量
     * @return 结果
     */
    public static ModbusData readHoldingRegister(NRSerialPort port, int address, int startingAddress, int numberRegisters) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_03, toByte(startingAddress, numberRegisters));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }

    /**
     * 04 读输入寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 起始地址
     * @param numberRegisters 寄存器数量
     * @return 结果
     */
    public static ModbusData readInputRegister(NRSerialPort port, int address, int startingAddress, int numberRegisters) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_04, toByte(startingAddress, numberRegisters));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }


    /**
     * 05 写单个线圈寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 起始地址
     * @param registersData   寄存器数据
     * @return 结果
     */
    public static ModbusData writeCoilRegister(NRSerialPort port, int address, int startingAddress, int registersData) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_05, toByte(startingAddress, registersData));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }

    /**
     * 06 写单个保持寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 起始地址
     * @param registersData   寄存器数据
     * @return 结果
     */
    public static ModbusData writeHoldingRegister(NRSerialPort port, int address, int startingAddress, int registersData) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_06, toByte(startingAddress, registersData));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }

    /**
     * 0F 写多个线圈寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 寄存器起始地址
     * @param numberRegisters 寄存器数量
     * @param registersData   寄存器数据
     * @return 结果
     */
    public static ModbusData writeMultipleCoilRegister(NRSerialPort port, int address, int startingAddress, int numberRegisters, int[] registersData) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_0F, toByte(startingAddress, numberRegisters, registersData));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }

    /**
     * 10 写多个保持寄存器
     *
     * @param port            串口
     * @param address         地址
     * @param startingAddress 寄存器起始地址
     * @param numberRegisters 寄存器数量
     * @param registersData   寄存器数据
     * @return 结果
     */
    public static ModbusData writeMultipleHoldingRegister(NRSerialPort port, int address, int startingAddress, int numberRegisters, int[] registersData) throws TimeOutException, IOException, CrcException {
        final ModbusData request = request(port, address, ModbusFunctionCode.HEX_10, toByte(startingAddress, numberRegisters, registersData));
        request.setData(ModbusFunctionCode.data(request.getAccept()));
        return request;
    }

    /**
     * 计算modbus帧间隔时间-毫秒
     *
     * @param port 串口
     * @return 结果
     */
    public static int dataFrame(NRSerialPort port) {
        if (port != null) {
            return (int) Math.ceil((port.getDataBits() + port.getParity() + port.getStopBits() + 1.0) / port.getBaud() * 1000.0 * 3.5);
        }
        return 30;
    }

    /**
     * 设置全局超时时间
     *
     * @param timeout 超时时间毫秒
     */
    public static synchronized void setTimeout(int timeout) {
        MasterRtu.timeout = timeout;
    }

    private static ModbusData request(final NRSerialPort port, int address, ModbusFunctionCode code, byte[] data) throws IOException, CrcException, TimeOutException {
        if (port == null) {
            throw new IOException("串口不存在！");
        }
        final byte[] send = ModbusUtils.process(address, code, data);
        final byte[] accept = ModbusUtils.send(port, send, timeout);
        if (!ModbusUtils.crcCheck(accept)) {
            throw new CrcException("crc check error!");
        }
        return new ModbusData(send, accept);
    }

    private static byte[] toByte(int startingAddress, int numberRegisters) {
        return ByteBuffer.allocate(4).putShort((short) startingAddress).putShort((short) numberRegisters).array();
    }

    private static byte[] toByte(int startingAddress, int numberRegisters, int[] registersData) {
        final ByteBuffer byteBuffer = ByteBuffer.allocate((registersData.length * 2) + 4).putShort((short) startingAddress).putShort((short) numberRegisters);
        for (int i : registersData) {
            byteBuffer.putShort((short) i);
        }
        return byteBuffer.array();
    }
}
