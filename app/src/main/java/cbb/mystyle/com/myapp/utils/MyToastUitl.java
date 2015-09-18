package cbb.mystyle.com.myapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast提示
 * Created by BoBo on 2015/9/2.
 */
public class MyToastUitl {
    public final static int SHORT_TOAST = 0;
    public final static int LONG_TOAST = 1;
    private final static boolean TOAST_FLAG = false;

    /**
     * 内部测试的待开关
     * 开关为TOAST_FLAG
     *
     * @param context
     * @param str
     */
    public static void showToastFlag(Context context, String str) {
        if (TOAST_FLAG) {
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 需要与用户交互的
     *
     * @param context
     * @param str
     * @param sl
     */
    public static void showToast(Context context, String str, int sl) {
        switch (sl) {
            case SHORT_TOAST:
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                break;
            case LONG_TOAST:
                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
