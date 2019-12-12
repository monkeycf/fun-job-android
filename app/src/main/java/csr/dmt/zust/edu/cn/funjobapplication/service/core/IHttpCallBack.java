package csr.dmt.zust.edu.cn.funjobapplication.service.core;

/**
 * created by monkeycf on 2019/12/12
 *
 * @param <T>
 */
public interface IHttpCallBack<T> {
    void SuccessCallBack(T data);

    void ErrorCallBack(String msg);
}
