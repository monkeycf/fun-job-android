package csr.dmt.zust.edu.cn.funjobapplication.module.database.helper.wrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import csr.dmt.zust.edu.cn.funjobapplication.module.database.Schema;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;

/**
 * created by monkeycf on 2019/12/25
 */
public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public UserLoginResModule getUserInfo() {
        String userId = getString(getColumnIndex(Schema.UserTable.Cols.USER_ID));
        String userName = getString(getColumnIndex(Schema.UserTable.Cols.USER_NAME));
        String userHeadPortraitUrl = getString(getColumnIndex(Schema.UserTable.Cols.USER_HEAD_PORTRAIT_URL));
        String userIntro = getString(getColumnIndex(Schema.UserTable.Cols.USER_INTRO));
        String userPhone = getString(getColumnIndex(Schema.UserTable.Cols.USER_PHONE));
        return new UserLoginResModule(userId, userName, userHeadPortraitUrl, userIntro, userPhone);
    }
}
