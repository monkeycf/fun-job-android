package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.IUserRequest;

/**
 * created by monkeycf on 2019/12/12
 */
public class UserApi {
    private IUserRequest mUserRequest;
    private static UserApi sUserApi;

    public static UserApi getInstance() {
        if (sUserApi == null) {
            sUserApi = new UserApi();
        }
        return sUserApi;
    }

    private UserApi() {
        mUserRequest = HttpRetrofit.get().create(Request.getUserRequest());
    }

    // 用户注册
    public void registerUser(UserRegisterReqModule userRegisterReqModule,
                             IHttpCallBack<BaseResult<UserRegisterResModule>> callback) {
        new Request<>(mUserRequest.RegisterUserPost(userRegisterReqModule), callback);
    }

    // 用户登录
    public void loginUser(UserLoginReqModule userLoginReqModule,
                          IHttpCallBack<BaseResult<UserLoginResModule>> callback) {
        new Request<>(mUserRequest.LoginUserPost(userLoginReqModule), callback);
    }
}
