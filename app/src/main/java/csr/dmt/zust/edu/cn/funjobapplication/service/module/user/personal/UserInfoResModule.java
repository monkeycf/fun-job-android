package csr.dmt.zust.edu.cn.funjobapplication.service.module.user.personal;

import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;

public class UserInfoResModule extends UserLoginResModule {
    private int collectCount;
    private int noteCount;

    public int getCollectCount() {
        return collectCount;
    }

    public int getNoteCount() {
        return noteCount;
    }
}
