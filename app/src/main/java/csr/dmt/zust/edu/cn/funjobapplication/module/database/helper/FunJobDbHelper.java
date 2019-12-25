package csr.dmt.zust.edu.cn.funjobapplication.module.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import csr.dmt.zust.edu.cn.funjobapplication.module.database.Schema.UserTable;
import csr.dmt.zust.edu.cn.funjobapplication.module.database.helper.wrapper.UserCursorWrapper;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;

public class FunJobDbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mDB = null; // 数据库的实例
    private static final String FUN_JOB_DB_NAME = "funJobBase.db"; // 数据库的名称
    private static final int FUN_JOB_DB_VERSION = 1; // 数据库的版本号

    public FunJobDbHelper(@Nullable Context context) {
        super(context, FUN_JOB_DB_NAME, null, FUN_JOB_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
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
    private void openWriteLink(FunJobDbHelper funJobDbHelper) {
        if (mDB == null) {
            mDB = funJobDbHelper.getWritableDatabase();
        }
    }

    /**
     * 添加用户
     *
     * @param funJobDbHelper     链接助手
     * @param userLoginResModule 用户信息
     */
    public void insertUser(FunJobDbHelper funJobDbHelper, UserLoginResModule userLoginResModule) {
        openWriteLink(funJobDbHelper);
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserTable.Cols.USER_ID, userLoginResModule.getId());
        contentValues.put(UserTable.Cols.USER_NAME, userLoginResModule.getUsername());
        contentValues.put(UserTable.Cols.USER_HEAD_PORTRAIT_URL, userLoginResModule.getHeadPortraitUrl());
        contentValues.put(UserTable.Cols.USER_INTRO, userLoginResModule.getIntro());
        contentValues.put(UserTable.Cols.USER_PHONE, userLoginResModule.getPhone());
        mDB.insert(UserTable.NAME, null, contentValues);
    }

    /**
     * 读取最新用户
     *
     * @param funJobDbHelper 链接助手
     * @return 登录用户信息
     */
    public UserLoginResModule getUserInfo(FunJobDbHelper funJobDbHelper) {
        openWriteLink(funJobDbHelper);
        Cursor cursor = mDB.query(UserTable.NAME, null, null, null, null, null, null);
        UserCursorWrapper userCursorWrapper = new UserCursorWrapper(cursor);
        try {
            if (userCursorWrapper.getCount() == 0) {
                return null;
            }
            // 读取最新的登录信息
            userCursorWrapper.moveToLast();
            return userCursorWrapper.getUserInfo();
        } finally {
            userCursorWrapper.close();
        }
    }
}
