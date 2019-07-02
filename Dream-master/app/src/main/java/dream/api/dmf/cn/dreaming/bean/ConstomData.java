package dream.api.dmf.cn.dreaming.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ConstomData implements Parcelable {
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
    public String pzimagesUrl;
    public Object uptime;
    public String checked;
    public Object payname;
    public String paymount;

    protected ConstomData(Parcel in) {
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
        pzimagesUrl = in.readString();
        checked = in.readString();
        paymount = in.readString();
    }

    public static final Creator<ConstomData> CREATOR = new Creator<ConstomData>() {
        @Override
        public ConstomData createFromParcel(Parcel in) {
            return new ConstomData(in);
        }

        @Override
        public ConstomData[] newArray(int size) {
            return new ConstomData[size];
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
        dest.writeString(pzimagesUrl);
        dest.writeString(checked);
        dest.writeString(paymount);
    }
}
