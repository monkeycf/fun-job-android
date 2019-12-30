package csr.dmt.zust.edu.cn.funjobapplication.service.module.note.select;

import java.util.List;

/**
 * created by monkeycf on 2019/12/13
 */
public class NoteSelectResModule {
    private String noteId;
    private String topicId;
    private String userId;
    private String content;
    private String createTime;
    private String lastModificationTime;
    private String noteStatus;
    private String weather;
    private List<String> pictures;

    public List<String> getPictures() {
        return pictures;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(String lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public String getNoteStatus() {
        return noteStatus;
    }

    public void setNoteStatus(String noteStatus) {
        this.noteStatus = noteStatus;
    }


}
