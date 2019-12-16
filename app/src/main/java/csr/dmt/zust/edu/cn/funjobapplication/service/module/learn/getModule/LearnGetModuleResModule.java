package csr.dmt.zust.edu.cn.funjobapplication.service.module.learn.getModule;

/**
 * created by monkeycf on 2019/12/16
 * 学习模块响应参数模型
 */
public class LearnGetModuleResModule {
    private String id;
    private String title;
    private String cover;

    public LearnGetModuleResModule(String id, String title, String cover) {
        this.id = id;
        this.title = title;
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
