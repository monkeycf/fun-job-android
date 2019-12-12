package csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login;

/**
 * created by monkeycf on 2019/12/13
 * 用户登录请求参数
 */
public class UserLoginReqModule {
    private String phone;
    private String pwd;

    public UserLoginReqModule(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }
}
