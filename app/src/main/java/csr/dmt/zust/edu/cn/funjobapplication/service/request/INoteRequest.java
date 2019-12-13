package csr.dmt.zust.edu.cn.funjobapplication.service.request;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateResModule;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface INoteRequest {

    // 新增笔记
    @POST("/api/v1/note/create")
    Call<BaseResult<NoteCreateResModule>> createNote(@Body NoteCreateReqModule noteCreateReqModule);
}
