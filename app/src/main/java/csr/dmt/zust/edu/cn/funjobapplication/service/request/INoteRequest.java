package csr.dmt.zust.edu.cn.funjobapplication.service.request;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.select.NoteSelectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.update.NoteUpdateReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.update.NoteUpdateResModule;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface INoteRequest {

    // 新增笔记
    @POST("/api/v1/note/create")
    Call<BaseResult<NoteCreateResModule>> createNote(@Body NoteCreateReqModule noteCreateReqModule);

    // 编辑笔记
    @POST("/api/v1/note/update")
    Call<BaseResult<NoteUpdateResModule>> updateNote(@Body NoteUpdateReqModule noteUpdateReqModule);

    // 删除笔记
    @POST("/api/v1/note/delete")
    Call<BaseResult<NoteDeleteResModule>> deleteNote(@Body NoteDeleteReqModule noteDeleteReqModule);

    // 查询当前主题下的自己笔记
    @GET("/api/v1/note/select")
    Call<BaseResult<List<NoteSelectResModule>>> selectMyNote(@Query("topicId") String topicId, @Query(
            "userId") String userId);
}
