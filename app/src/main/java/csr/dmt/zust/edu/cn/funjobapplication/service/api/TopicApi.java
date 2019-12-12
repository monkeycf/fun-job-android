package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.ITopicRequest;

/**
 * created by monkeycf on 2019/12/12
 */
public class TopicApi {
    private ITopicRequest mTopicRequest;

    public TopicApi() {
        mTopicRequest = HttpRetrofit.get().create(Request.getTopicRequest());
    }

    // 获取全部主题
    public void getAllTopic(IHttpCallBack httpCallBack) {
        new Request<>(mTopicRequest.getAllTopic(), httpCallBack);
    }

    // 根据ID获取主题
    public void getTopicById(String topicId, String userId, IHttpCallBack httpCallBack) {
        new Request<>(mTopicRequest.getTopicById(topicId, userId), httpCallBack);
    }
}
