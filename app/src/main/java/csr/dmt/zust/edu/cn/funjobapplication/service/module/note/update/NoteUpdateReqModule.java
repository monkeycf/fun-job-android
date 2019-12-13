package csr.dmt.zust.edu.cn.funjobapplication.service.module.note.update;

/**
 * created by monkeycf on 2019/12/13
 * 跟新
 */
public class NoteUpdateReqModule {
    private String noteId;
    private String content;

    public NoteUpdateReqModule(String noteId, String content) {
        this.noteId = noteId;
        this.content = content;
    }
}
