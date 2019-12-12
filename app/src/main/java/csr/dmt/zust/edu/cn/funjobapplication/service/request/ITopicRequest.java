package csr.dmt.zust.edu.cn.funjobapplication.service.request;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.TopicInfoModule;
import retrofit2.Call;
import retrofit2.http.GET;
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
}
