package dream.api.dmf.cn.dreaming.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    public static boolean isSuccess(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null) {
                String code = jsonObject.optString("status");
                return "200".equals("code");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getError(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject != null) {
                String error = jsonObject.optString("message");
                return error;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }
}
