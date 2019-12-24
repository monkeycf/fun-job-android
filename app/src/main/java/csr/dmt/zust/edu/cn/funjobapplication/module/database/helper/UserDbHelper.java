package csr.dmt.zust.edu.cn.funjobapplication.module.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import csr.dmt.zust.edu.cn.funjobapplication.module.database.Schema.UserTable;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.register.UserRegisterResModule;

public class UserDbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mDB = null; // 数据库的实例
    private UserDbHelper mHelper = null; // 数据库帮助器的实例
    private static final String USER_DB_NAME = "userBase.db"; // 数据库的名称
    private static final int USER_DB_VERSION = 1; // 数据库的版本号

    public UserDbHelper(@Nullable Context context) {
        super(context, USER_DB_NAME, null, USER_DB_VERSION);
    }

//    /**
//     * 获取数据库助手实例
//     *
//     * @param context Context
//     * @return 数据库助手实例
//     */
//    public static UserDbHelper getInstance(Context context) {
//        if (mHelper == null) {
//            mHelper = new UserDbHelper(context, USER_DB_VERSION);
//        }
//        return mHelper;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + UserTable.USER_NAME + " ;");
        db.execSQL("create table " + UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.USER_ID + ", " +
                UserTable.Cols.USER_HEAD_PORTRAIT_URL + ", " +
                UserTable.Cols.USER_INTRO + ", " +
                UserTable.Cols.USER_NAME + ", " +
                UserTable.Cols.USER_PHONE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 创建写链接
     */
    private void openWriteLink(UserDbHelper userDbHelper) {
        if (mDB == null) {
            mDB = userDbHelper.getWritableDatabase();
        }
    }

    public void insertUser(UserDbHelper userDbHelper, UserLoginResModule userLoginResModule) {
        openWriteLink(userDbHelper);
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.Cols.USER_ID, userLoginResModule.getId());
        contentValues.put(UserTable.Cols.USER_NAME, userLoginResModule.getUsername());
        contentValues.put(UserTable.Cols.USER_HEAD_PORTRAIT_URL, userLoginResModule.getHeadPortraitUrl());
        contentValues.put(UserTable.Cols.USER_INTRO, userLoginResModule.getIntro());
        contentValues.put(UserTable.Cols.USER_PHONE, userLoginResModule.getPhone());
        mDB.insert(UserTable.NAME, null, contentValues);
    }

    public UserLoginResModule getUserInfo(UserDbHelper userDbHelper) {
        openWriteLink(userDbHelper);
        Cursor cursor = mDB.query(UserTable.NAME, null, null, null, null, null, null);
        UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
        try {
            if (userCursorWrapper.getCount() == 0) {
                return null;
            }
            userCursorWrapper.moveToLast();
            return userCursorWrapper.getUserInfo();
        } finally {
            userCursorWrapper.close();
        }
    }

    public class UserCursorWrapper extends CursorWrapper {

        public UserCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public UserLoginResModule getUserInfo() {
            String userId = getString(getColumnIndex(UserTable.Cols.USER_ID));
            String userName = getString(getColumnIndex(UserTable.Cols.USER_NAME));
            String userHeadPortraitUrl = getString(getColumnIndex(UserTable.Cols.USER_HEAD_PORTRAIT_URL));
            String userIntro = getString(getColumnIndex(UserTable.Cols.USER_INTRO));
            String userPhone = getString(getColumnIndex(UserTable.Cols.USER_PHONE));
            return new UserLoginResModule(userId, userName, userHeadPortraitUrl, userIntro, userPhone);
        }
    }
}
