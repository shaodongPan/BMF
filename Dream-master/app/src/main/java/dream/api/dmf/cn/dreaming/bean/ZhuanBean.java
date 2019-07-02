package dream.api.dmf.cn.dreaming.bean;

/**
 * Created by SongNing on 2019/6/28.
 * email: 836883891@qq.com
 */
public class ZhuanBean {
    /**
     * status : -202
     * message : 积分不足
     * data :
     */

    public int status;
    public String message;
    public DataBean data;

    public static class DataBean {
        /**
         * realname : 1
         * level : 银卡
         * number : 1
         * total_rewqrd : 588.60
         * createtime : 2019-06-25 11:40:24
         */

        public String ratio;
        public String now_jifen;
    }
}
