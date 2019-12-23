package csr.dmt.zust.edu.cn.funjobapplication.service.api;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.HttpRetrofit;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.Request;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create.NoteCreateResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.delete.NoteDeleteResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.select.NoteSelectResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.update.NoteUpdateReqModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.note.update.NoteUpdateResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.request.INoteRequest;

/**
 * created by monkeycf on 2019/12/13
 */
public class NoteApi {
    private INoteRequest mNoteRequest;
    private static NoteApi sNoteApi;

    public NoteApi() {
        mNoteRequest = HttpRetrofit.get().create(Request.getNoteRequest());
    }

    public static NoteApi getInstance() {
        if (sNoteApi == null) {
            sNoteApi = new NoteApi();
        }
        return sNoteApi;
    }

    // 新增笔记
    public void createNote(NoteCreateReqModule noteCreateReqModule,
                           IHttpCallBack<BaseResult<NoteCreateResModule>> callBack) {
        new Request<>(mNoteRequest.createNote(noteCreateReqModule), callBack);
    }

    // 修改笔记
    public void updateNote(NoteUpdateReqModule noteUpdateReqModule,
                           IHttpCallBack<BaseResult<NoteUpdateResModule>> callBack) {
        new Request<>(mNoteRequest.updateNote(noteUpdateReqModule), callBack);
    }

    // 删除笔记
    public void deleteNote(NoteDeleteReqModule noteDeleteReqModule,
                           IHttpCallBack<BaseResult<NoteDeleteResModule>> callBack) {
        new Request<>(mNoteRequest.deleteNote(noteDeleteReqModule), callBack);
    }

    // 查询当前主题下自己的note
    public void selectMyNote(String topicId, String userId,
                             IHttpCallBack<BaseResult<List<NoteSelectResModule>>> callBack) {
        new Request<>(mNoteRequest.selectMyNote(topicId, userId), callBack);
    }
}
