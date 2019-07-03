package dream.api.dmf.cn.dreaming.bean;

import java.util.List;

public class Record {

    private int error;
    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 2019-07-03 15:16
         * jnums : 10.0000
         * content : 增加
         * classname : green
         */

        private String createtime;
        private String jnums;
        private String content;
        private String classname;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getJnums() {
            return jnums;
        }

        public void setJnums(String jnums) {
            this.jnums = jnums;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }
    }
}
