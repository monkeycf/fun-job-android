package csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel;

/**
 * created by monkeycf on 2019/12/13
 * 取消收藏请求参数
 */
public class TopicCancelCollectReqModule {
    private String userId;
    private String topicId;

    public TopicCancelCollectReqModule(String userId, String topicId) {
        this.userId = userId;
        this.topicId = topicId;
    }
}
