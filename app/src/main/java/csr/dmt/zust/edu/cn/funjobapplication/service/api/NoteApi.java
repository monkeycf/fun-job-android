package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.INoteRequest;

/**
* created by monkeycf on 2019/12/13
*/
public class NoteApi {
    private INoteRequest mNoteRequest;

    public NoteApi() {
        mNoteRequest = HttpRetrofit.get().create(Request.getNoteRequest());
    }

    // 新增笔记
    public void createNote(NoteCreateReqModule noteCreateReqModule, IHttpCallBack callBack) {
        new Request<>(mNoteRequest.createNote(noteCreateReqModule), callBack);
    }
}
