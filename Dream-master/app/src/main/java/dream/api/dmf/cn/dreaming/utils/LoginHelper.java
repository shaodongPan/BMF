package dream.api.dmf.cn.dreaming.utils;

import android.content.Context;
import android.content.SharedPreferences;

import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.app.MyApp;

/**
 * Created by SongNing on 2019/7/1.
 * email: 836883891@qq.com
 */
public class LoginHelper {
    private static SharedPreferences sharedPreferences;

    private static void check() {
        if (sharedPreferences == null) {
            sharedPreferences = MyApp.getInstance().getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        }
    }

    public static void login(String phone, String uid, String shell, String realname, String username, String groupname, String uuid) {
        check();
        sharedPreferences.edit().putString(UserApi.LOGIN_ACCOUNT, phone).commit();
        sharedPreferences.edit().putString(UserApi.Uid, uid).commit();
        sharedPreferences.edit().putString(UserApi.Shell, shell).commit();
        sharedPreferences.edit().putString(UserApi.TrueName, realname).commit();
        sharedPreferences.edit().putString(UserApi.UserName, username).commit();
        sharedPreferences.edit().putString(UserApi.Groupname_cn, groupname).commit();
        sharedPreferences.edit().putString(UserApi._UUID, uuid).commit();
    }

    public static void logout() {
        check();
        sharedPreferences.edit().putString(UserApi.LOGIN_ACCOUNT, "").commit();
        sharedPreferences.edit().putString(UserApi.Uid, "").commit();
        sharedPreferences.edit().putString(UserApi.Shell, "").commit();
        sharedPreferences.edit().putString(UserApi.TrueName, "").commit();
        sharedPreferences.edit().putString(UserApi.UserName, "").commit();
        sharedPreferences.edit().putString(UserApi.Groupname_cn, "").commit();
        sharedPreferences.edit().putString(UserApi._UUID, "").commit();
    }
}
