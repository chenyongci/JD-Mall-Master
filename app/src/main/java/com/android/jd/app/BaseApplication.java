package com.android.jd.app;

import android.app.Application;

/**
 * @Author : chenyongci
 * @date : 2018/7/26
 * @desc :
 */

public class BaseApplication extends Application {
    private static BaseApplication mContext;
    //private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //BaseSharePrefence.initSharePref(this);
        //FileUtils.init("xiaodai");
        //配置数据库
        setDatabase();
        //Stetho.initializeWithDefaults(this);
    }

    public static BaseApplication getInstance(){
        return mContext;
    }

    public void exitApp() {
        //removeAllActivity();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 配置数据库
     */
    private void setDatabase() {
        //创建数据库
        //BaseOpenHelper helper = new BaseOpenHelper(this, "xiaodai.db", null);
        //获取可写数据库
        //SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        //DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        //daoSession = daoMaster.newSession();

    }
   /* public static DaoSession getDaoInstant() {
        return daoSession;
    }*/
}
