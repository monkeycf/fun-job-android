package csr.dmt.zust.edu.cn.funjobapplication.service.module.topic;

/**
 * created by monkeycf on 2019/12/12
 * 主题返回参数
 */
public class TopicInfoModule {
    private int id;
    private String title;
    private String content;
    private String complexity;
    private String releaseUser;
    private String browseSum;
    private String collectSum;
    private String createTime;
    private String lastModificationTime;
    private String label;
    private String answerUrl;

    public String getAnswerUrl() {
        return answerUrl;
    }

    public void setAnswerUrl(String answerUrl) {
        this.answerUrl = answerUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public String getReleaseUser() {
        return releaseUser;
    }

    public void setReleaseUser(String releaseUser) {
        this.releaseUser = releaseUser;
    }

    public String getBrowseSum() {
        return browseSum;
    }

    public void setBrowseSum(String browseSum) {
        this.browseSum = browseSum;
    }

    public String getCollectSum() {
        return collectSum;
    }

    public void setCollectSum(String collectSum) {
        this.collectSum = collectSum;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
