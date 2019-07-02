package dream.api.dmf.cn.dreaming.bean;

public class SignBean {
    /**
     * error : 1
     * msg : 用户未登录
     */

    private int error;
    private String msg;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
