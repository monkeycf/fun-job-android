package csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register;

/**
 * created by monkeycf on 2019/12/12
 * 用户注册请求参数
 */
public class UserRegisterReqModule {
    private String phone;
    private String pwd;

    public UserRegisterReqModule(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }
}
