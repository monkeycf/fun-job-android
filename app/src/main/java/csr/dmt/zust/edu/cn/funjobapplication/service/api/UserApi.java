package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.IUserRequest;

/**
 * created by monkeycf on 2019/12/12
 */
public class UserApi {
    private IUserRequest mUserRequest;

    public UserApi() {
        mUserRequest = HttpRetrofit.get().create(Request.getUserRequest());
    }

    // 用户注册
    public void RegisterUser(UserRegisterReqModule userRegisterReqModule,
                             IHttpCallBack callback) {
        new Request<>(mUserRequest.RegisterUserPost(userRegisterReqModule), callback);
    }

    // 用户登录
    public void LoginUser(UserLoginReqModule userLoginReqModule, IHttpCallBack callback) {
        new Request<>(mUserRequest.LoginUserPost(userLoginReqModule), callback);
    }
}
