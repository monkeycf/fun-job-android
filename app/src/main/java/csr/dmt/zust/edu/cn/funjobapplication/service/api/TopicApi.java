package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.collect.TopicCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.collect.TopicCollectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.status.TopicCollectStatusResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.ITopicRequest;

/**
 * created by monkeycf on 2019/12/12
 */
public class TopicApi {
    private ITopicRequest mTopicRequest;
    private static TopicApi sTopicApi;

    public static TopicApi getInstance() {
        if (sTopicApi == null) {
            sTopicApi = new TopicApi();
        }
        return sTopicApi;
    }

    private TopicApi() {
        mTopicRequest = HttpRetrofit.get().create(Request.getTopicRequest());
    }

    // 获取全部主题
    public void getAllTopic(IHttpCallBack<BaseResult<List<TopicInfoModule>>> httpCallBack) {
        new Request<>(mTopicRequest.getAllTopic(), httpCallBack);
    }

    // 根据ID获取主题
    public void getTopicById(String topicId, String userId, IHttpCallBack<BaseResult<TopicInfoModule>> httpCallBack) {
        new Request<>(mTopicRequest.getTopicById(topicId, userId), httpCallBack);
    }

    // 获取分类查询主题列表
    public void getTopicByLabel(String labelId, IHttpCallBack<BaseResult<List<TopicInfoModule>>> callBack) {
        new Request<>(mTopicRequest.getTopicByLabel(labelId), callBack);
    }

    // 搜索主题
    public void searchTopic(String key, IHttpCallBack<BaseResult<List<TopicInfoModule>>> callBack) {
        new Request<>(mTopicRequest.getSearchTopic(key), callBack);
    }

    // 查询收藏状态
    public void selectTopicCollectStatus(String userId, String topicId,
                                         IHttpCallBack<BaseResult<TopicCollectStatusResModule>> callBack) {
        new Request<>(mTopicRequest.selectTopicCollectStatus(topicId, userId), callBack);
    }

    // 收藏主题
    public void collectTopic(TopicCollectReqModule topicCollectReqModule,
                             IHttpCallBack<BaseResult<TopicCollectResModule>> callBack) {
        new Request<>(mTopicRequest.collectTopic(topicCollectReqModule), callBack);
    }

    // 取消收藏
    public void cancelCollectTopic(TopicCancelCollectReqModule topicCancelCollectReqModule,
                                   IHttpCallBack<BaseResult<TopicCancelCollectResModule>> callBack) {
        new Request<>(mTopicRequest.cancelCollectTopic(topicCancelCollectReqModule), callBack);
    }

    // 根据用户获取已收藏的列表
    public void getTopicCollected(String userId, IHttpCallBack<BaseResult<List<TopicInfoModule>>> callBack) {
        new Request<>(mTopicRequest.getTopicCollected(userId), callBack);
    }
}
