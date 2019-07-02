package dream.api.dmf.cn.dreaming.bean;

public class PhotoBean {

    /**
     * error : 0
     * url : http://api.xg360.cc/upload/voucher/20190702172402643.jpg
     * trueurl : bea6518c-dffb-4345-812f-58f9b8fdbd5c.jpg
     * width : null
     * height : null
     */

    private int error;
    private String url;
    private String trueurl;
    private String width;
    private String height;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrueurl() {
        return trueurl;
    }

    public void setTrueurl(String trueurl) {
        this.trueurl = trueurl;
    }

    public Object getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
