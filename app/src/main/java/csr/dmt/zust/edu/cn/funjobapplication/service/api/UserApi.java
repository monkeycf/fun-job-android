package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.UserRegister.userRegisterReqModule;
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
    public void RegisterUser(userRegisterReqModule module, IHttpCallBack httpCallBack) {
        new Request<>(mUserRequest.RegisterUserPost(module), httpCallBack);
    }
}
