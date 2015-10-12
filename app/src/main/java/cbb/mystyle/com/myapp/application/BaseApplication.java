package cbb.mystyle.com.myapp.application;

import android.app.Application;
import android.content.Context;

import cbb.mystyle.com.myapp.error.CrashHandler;
import cbb.mystyle.com.myapp.utils.SharedPreferencesUitl;


/**
 * Created by BoBo on 2015/9/15.
 */
public class BaseApplication extends Application{

    /**
     * 全局上下文
     */
    public static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(mApplicationContext);
    }
}
