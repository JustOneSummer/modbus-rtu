package link.linxun.modbus.err;

/**
 * 超时异常
 *
 * @author LinXun
 * @date 2021/7/20 15:08 星期二
 */
public class TimeOutException extends Exception {

    public TimeOutException(String msg) {
        super(msg);
    }
}
