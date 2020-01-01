package csr.dmt.zust.edu.cn.funjobapplication.service.request;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.personal.UserInfoResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterResModule;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * created by monkeycf on 2019/12/12
 */
public interface IUserRequest {
    // 注册用户
    @POST("/api/v1/user/register")
    Call<BaseResult<UserRegisterResModule>> RegisterUserPost(@Body UserRegisterReqModule module);

    // 用户登录
    @POST("/api/v1/user/login")
    Call<BaseResult<UserLoginResModule>> LoginUserPost(@Body UserLoginReqModule module);

    // 用户信息查询
    @GET("/api/v1/user/info")
    Call<BaseResult<UserInfoResModule>> SelectUserInfo(@Query("userId") String userId);
}
