package csr.dmt.zust.edu.cn.funjobapplication.service.core;

/**
 * created by monkeycf on 2019/12/12
 *
 * @param <T>
 */
public class BaseResult<T> {
    private int code; // 1 为成功
    private T data;
    private String msg; // 错误信息

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
