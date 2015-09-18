package cbb.mystyle.com.myapp.application;

import android.app.Application;
import android.content.Context;


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
        mApplicationContext = this;
    }
}
