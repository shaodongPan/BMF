package dream.api.dmf.cn.dreaming.bean;

import java.util.List;

/**
 * Created by SongNing on 2019/6/27.
 * email: 836883891@qq.com
 */
public class ReadBean {

    /**
     * status : 200
     * message : ok
     * data : {"info":{"id":1,"phone":"15111111111","level":1,"left_duipeng":"1200.00","right_duipeng":"0.00","number":"10000","total_rewqrd":"240.00","now_jifen":"826.00","commend_count":1,"team_count":1}}
     */

    public String status;
    public String message;
    public DataBean data;

    public static class DataBean {
        /**
         * info : {"id":1,"phone":"15111111111","level":1,"left_duipeng":"1200.00","right_duipeng":"0.00","number":"10000","total_rewqrd":"240.00","now_jifen":"826.00","commend_count":1,"team_count":1}
         */

        public InfoBean info;

        public List<String> arr;

        public static class InfoBean {
            /**
             * id : 1
             * phone : 15111111111
             * level : 1
             * left_duipeng : 1200.00
             * right_duipeng : 0.00
             * number : 10000
             * total_rewqrd : 240.00
             * now_jifen : 826.00
             * commend_count : 1
             * team_count : 1
             */

            public String id = "";
            public String phone = "0";
            public String level = "";
            public String left_duipeng = "0";
            public String right_duipeng = "0";
            public String number = "0";
            public String total_rewqrd = "0";
            public String now_jifen = "0";
            public String commend_count = "0";
            public String team_count = "0";
        }
    }
}
