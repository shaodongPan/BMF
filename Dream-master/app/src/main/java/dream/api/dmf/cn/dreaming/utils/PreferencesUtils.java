package dream.api.dmf.cn.dreaming.utils;

import android.content.Context;
import android.content.SharedPreferences;

import dream.api.dmf.cn.dreaming.api.UserApi;

public class PreferencesUtils {

    /**
     * 传入application的context，要不然会发生内存泄漏
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
