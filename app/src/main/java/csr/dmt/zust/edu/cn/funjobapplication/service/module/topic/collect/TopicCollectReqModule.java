package csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.collect;

/**
 * created by monkeycf on 2019/12/13
 * 收藏主题请求参数
 */
public class TopicCollectReqModule {
    private String userId;
    private String topicId;

    public TopicCollectReqModule(String userId, String topicId) {
        this.userId = userId;
        this.topicId = topicId;
    }
}
