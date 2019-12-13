package csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create;
/**
* created by monkeycf on 2019/12/13
 * 新增笔记请求参数
*/
public class NoteCreateReqModule {
    private String userId;
    private String topicId;
    private String content;

    public NoteCreateReqModule(String userId, String topicId, String content) {
        this.userId = userId;
        this.topicId = topicId;
        this.content = content;
    }
}
