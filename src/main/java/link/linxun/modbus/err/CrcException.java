package link.linxun.modbus.err;

/**
 * 校验异常
 *
 * @author LinXun
 * @date 2021/7/21 10:47 星期三
 */
public class CrcException extends Exception {
    public CrcException(String msg) {
        super(msg);
    }
}
