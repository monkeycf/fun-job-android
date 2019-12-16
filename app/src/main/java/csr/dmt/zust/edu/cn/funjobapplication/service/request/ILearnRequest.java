package csr.dmt.zust.edu.cn.funjobapplication.service.request;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.learn.getModule.LearnGetModuleResModule;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ILearnRequest {
    // 获取学习模块
    @GET("/api/v1/learn/get/modules")
    Call<BaseResult<List<LearnGetModuleResModule>>> getModule(@Query("index") String index);
}
