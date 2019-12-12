package csr.dmt.zust.edu.cn.funjobapplication.service.request;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.userRegisterReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterResModule;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * created by monkeycf on 2019/12/12
 */
public interface IUserRequest {
    // 注册用户
    @POST("/api/v1/user/register")
    Call<BaseResult<UserRegisterResModule>> RegisterUserPost(@Body userRegisterReqModule module);
}
