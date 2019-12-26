package csr.dmt.zust.edu.cn.funjobapplication.service.module.note.create;

import java.util.List;

/**
 * created by monkeycf on 2019/12/13
 * 新增笔记请求参数
 */
public class NoteCreateReqModule {
    private String userId;
    private String topicId;
    private String content;
    private List<String> pictures;
    private String weather;

    public NoteCreateReqModule(String userId, String topicId, String content, List<String> pictures, String weather) {
        this.userId = userId;
        this.topicId = topicId;
        this.content = content;
        this.pictures = pictures;
        this.weather = weather;
    }
}
