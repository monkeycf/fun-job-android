package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.learn.getModule.LearnGetModuleResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.ILearnRequest;

/**
 * created by monkeycf on 2019/12/16
 */
public class LearnApi {
    private ILearnRequest mLearnRequest;
    private static LearnApi sLearnApi;

    private LearnApi() {
        mLearnRequest = HttpRetrofit.get().create(Request.getLearnRequst());
    }

    public static LearnApi getInstance() {
        if (sLearnApi == null) {
            sLearnApi = new LearnApi();
        }
        return sLearnApi;
    }

    public void getModules(String index, IHttpCallBack<BaseResult<List<LearnGetModuleResModule>>> callBack) {
        new Request<>(mLearnRequest.getModule(index), callBack);
    }
}
