package dream.api.dmf.cn.dreaming.bean;

import java.util.List;

/**
 * Created by SongNing on 2019/6/27.
 * email: 836883891@qq.com
 */
public class BuyListBean {

    /**
     * data : [{"id":"3","sale_uid":null,"sale_username":null,"sale_mobile":null,"buy_uid":"1","buy_username":"17739265558","buy_mobile":"17739265558","amount":"10.0000","realpay":"31.7920","price":"3.1792","paytype":"0","bank_name":null,"bank_card":null,"bank_username":null,"bank_address":null,"addtime":"2019-06-29 09:17","buytime":"-","paytime":"-","donetime":null,"status":"0","pzimages":"","alipayfile":"","wechatfile":"","fe":"4%","uptime":null,"checked":"待匹配","payname":null,"pzimagesUrl":"http://api.xg360.cc","alipayfileUrl":"http://api.xg360.cc","wechatfileUrl":"http://api.xg360.cc"},{"id":"1","sale_uid":null,"sale_username":null,"sale_mobile":null,"buy_uid":"1","buy_username":"17739265558","buy_mobile":"17739265558","amount":"10.0000","realpay":"31.7920","price":"3.1792","paytype":"0","bank_name":null,"bank_card":null,"bank_username":null,"bank_address":null,"addtime":"2019-06-28 16:46","buytime":"-","paytime":"-","donetime":null,"status":"0","pzimages":"","alipayfile":"","wechatfile":"","fe":"4%","uptime":null,"checked":"待匹配","payname":null,"pzimagesUrl":"http://api.xg360.cc","alipayfileUrl":"http://api.xg360.cc","wechatfileUrl":"http://api.xg360.cc"}]
     *      * error : 0
     */

    public int error;
    public List<ConstomData> data;
    public String msg;

}
