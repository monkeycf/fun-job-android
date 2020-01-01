package csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login;

/**
 * created by monkeycf on 2019/12/13
 * 用户登录出参
 */
public class UserLoginResModule {
    private String id;
    private String username;
    private String headPortraitUrl;
    private String intro;
    private String phone;
    private String createTime;
    private String latsModificationTime;

    public UserLoginResModule() {

    }

    public UserLoginResModule(String id, String username, String headPortraitUrl, String intro, String phone) {
        this.id = id;
        this.username = username;
        this.headPortraitUrl = headPortraitUrl;
        this.intro = intro;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPortraitUrl() {
        return headPortraitUrl;
    }

    public void setHeadPortraitUrl(String headPortraitUrl) {
        this.headPortraitUrl = headPortraitUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLatsModificationTime() {
        return latsModificationTime;
    }

    public void setLatsModificationTime(String latsModificationTime) {
        this.latsModificationTime = latsModificationTime;
    }
}
