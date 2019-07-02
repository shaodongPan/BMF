package dream.api.dmf.cn.dreaming.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by SongNing on 2019/6/24.
 * email: 836883891@qq.com
 */
public class BigBean {


    /**
     * data : [{"id":"3","sale_uid":null,"sale_username":null,"sale_mobile":null,"buy_uid":"4","buy_username":"15515828109","buy_mobile":"15515828109","amount":"1000.0000","realpay":"1670.0000","price":"1.6700","paytype":"0","bank_name":null,"bank_card":null,"bank_username":null,"bank_address":null,"addtime":"2019-06-24 09:26","buytime":"-","paytime":"-","donetime":null,"status":"0","pzimages":"","alipayfile":"","wechatfile":"","fe":"10%","tradetype":"1","uptime":null,"checked":"待匹配","payname":null,"paymount":"1100.00"},{"id":"2","sale_uid":null,"sale_username":null,"sale_mobile":null,"buy_uid":"4","buy_username":"15515828109","buy_mobile":"15515828109","amount":"100.0000","realpay":"167.0000","price":"1.6700","paytype":"0","bank_name":null,"bank_card":null,"bank_username":null,"bank_address":null,"addtime":"2019-06-24 09:26","buytime":"-","paytime":"-","donetime":null,"status":"0","pzimages":"","alipayfile":"","wechatfile":"","fe":"10%","tradetype":"1","uptime":null,"checked":"待匹配","payname":null,"paymount":"110.00"}]
     * error : 0
     */

    public int error;
    public List<DataBean> data;
    public String msg;

    public static class DataBean implements Parcelable {
        /**
         * id : 3
         * sale_uid : null
         * sale_username : null
         * sale_mobile : null
         * buy_uid : 4
         * buy_username : 15515828109
         * buy_mobile : 15515828109
         * amount : 1000.0000
         * realpay : 1670.0000
         * price : 1.6700
         * paytype : 0
         * bank_name : null
         * bank_card : null
         * bank_username : null
         * bank_address : null
         * addtime : 2019-06-24 09:26
         * buytime : -
         * paytime : -
         * donetime : null
         * status : 0
         * pzimages :
         * alipayfile :
         * wechatfile :
         * fe : 10%
         * tradetype : 1
         * uptime : null
         * checked : 待匹配
         * payname : null
         * paymount : 1100.00
         */

        public String id;
        public Object sale_uid;
        public String sale_username;
        public String sale_mobile;
        public String buy_uid;
        public String buy_username;
        public String buy_mobile;
        public String amount;
        public String realpay;
        public String price;
        public String paytype;
        public Object bank_name;
        public Object bank_card;
        public Object bank_username;
        public Object bank_address;
        public String addtime;
        public String buytime;
        public String paytime;
        public Object donetime;
        public String status;
        public String pzimages;
        public String alipayfile;
        public String wechatfile;
        public String fe;
        public String tradetype;
        public Object uptime;
        public String checked;
        public Object payname;
        public String paymount;


        protected DataBean(Parcel in) {
            id = in.readString();
            sale_username = in.readString();
            sale_mobile = in.readString();
            buy_uid = in.readString();
            buy_username = in.readString();
            buy_mobile = in.readString();
            amount = in.readString();
            realpay = in.readString();
            price = in.readString();
            paytype = in.readString();
            addtime = in.readString();
            buytime = in.readString();
            paytime = in.readString();
            status = in.readString();
            pzimages = in.readString();
            alipayfile = in.readString();
            wechatfile = in.readString();
            fe = in.readString();
            tradetype = in.readString();
            checked = in.readString();
            paymount = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(sale_username);
            dest.writeString(sale_mobile);
            dest.writeString(buy_uid);
            dest.writeString(buy_username);
            dest.writeString(buy_mobile);
            dest.writeString(amount);
            dest.writeString(realpay);
            dest.writeString(price);
            dest.writeString(paytype);
            dest.writeString(addtime);
            dest.writeString(buytime);
            dest.writeString(paytime);
            dest.writeString(status);
            dest.writeString(pzimages);
            dest.writeString(alipayfile);
            dest.writeString(wechatfile);
            dest.writeString(fe);
            dest.writeString(tradetype);
            dest.writeString(checked);
            dest.writeString(paymount);
        }
    }
}
