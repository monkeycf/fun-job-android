package csr.dmt.zust.edu.cn.funjobapplication.service.request;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.cancel.TopicCancelCollectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.collect.TopicCollectReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.collect.TopicCollectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.status.TopicCellectStatusReaModule;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * created by monkeycf on 2019/12/12
 */
public interface ITopicRequest {
    // 获取全部主题
    @GET("/api/v1/topic/select/all")
    Call<BaseResult<List<TopicInfoModule>>> getAllTopic();

    // 根据ID获取主题
    @GET("/api/v1/topic/select/topicId")
    Call<BaseResult<TopicInfoModule>> getTopicById(@Query("topicId") String topicId,
                                                   @Query("userId") String userId);

    // 获取主题分类查询
    @GET("/api/v1/topic/select/label")
    Call<BaseResult<List<TopicInfoModule>>> getTopicByLabel(@Query("labelId") String labelId);

    // 搜索主题
    @GET("/api/v1/topic/search")
    Call<BaseResult<List<TopicInfoModule>>> getSearchTopic(@Query("key") String key);

    // 查询收藏状态
    @GET("/api/v1/topic/collect/status")
    Call<BaseResult<TopicCellectStatusReaModule>> selectTopicCollectStatus(@Query("topicId") String topicId,
                                                                           @Query("userId") String userId);

    // 收藏主题
    @POST("/api/v1/topic/collect")
    Call<BaseResult<TopicCollectResModule>> collectTopic(@Body TopicCollectReqModule module);

    // 取消收藏
    @POST("/api/v1/topic/cancel/collection")
    Call<BaseResult<TopicCancelCollectResModule>> cancelCollectTopic(@Body TopicCancelCollectReqModule topicCancelCollectReqModule);

    // 获取用户已收藏主题
    @GET("/api/v1/topic/collect/list")
    Call<BaseResult<List<TopicInfoModule>>> getTopicCollected(@Query("userId") String userId);
}
