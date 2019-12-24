package csr.dmt.zust.edu.cn.funjobapplication.module.database;

/**
 * created by monkeycf on 2019/12/24
 */
public class Schema {
    /**
     * 用户表
     */
    public static final class UserTable {
        // 表名
        public static final String NAME = "fun_job_user";

        // 列名
        public static final class Cols {
            public static final String USER_ID = "user_id";
            public static final String USER_NAME = "name";
            public static final String USER_HEAD_PORTRAIT_URL = "head_portrait_url";
            public static final String USER_INTRO = "intro";
            public static final String USER_PHONE = "phone";
        }
    }
}
