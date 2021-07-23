package link.linxun.modbus;

import gnu.io.NRSerialPort;
import link.linxun.modbus.err.CrcException;
import link.linxun.modbus.err.TimeOutException;
import link.linxun.modbus.rtu.MasterRtu;
import link.linxun.modbus.rtu.ModbusData;
import link.linxun.modbus.serial.SerialPort;
import link.linxun.modbus.serial.SerialUtils;

import java.io.IOException;

/**
 * @author LinXun
 * @date 2021/7/20 13:41 星期二
 */
public class Main {
    public static void main(String[] args) {
        /*
            注意串口是半双工的，所以要注意控制读写不能一起发送 否则会串
         */
        SerialUtils.serialPorts().forEach(System.out::println);
        final NRSerialPort open = SerialUtils.open(new SerialPort("COM8", 9600));
        MasterRtu.setTimeout(1000);
        try {
            for (int i = 0; i < 100; i++) {
                final ModbusData modbusData = MasterRtu.readDiscreteInputRegister(open, 2, 0, 2);
                System.out.println("发->" + modbusData.getSendHex());
                System.out.println("收->" + modbusData.getAcceptHex());
                System.out.println("数据->" + modbusData.getDataHex());
                System.out.println("=====================================");
                Thread.sleep(300);
            }
        } catch (CrcException | TimeOutException | IOException | InterruptedException crcException) {
            crcException.printStackTrace();
        }
    }
}
