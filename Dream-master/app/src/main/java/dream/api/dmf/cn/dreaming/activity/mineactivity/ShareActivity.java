package dream.api.dmf.cn.dreaming.activity.mineactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dream.api.dmf.cn.dreaming.R;
import dream.api.dmf.cn.dreaming.api.UserApi;
import dream.api.dmf.cn.dreaming.base.BaseMvpActivity;
import dream.api.dmf.cn.dreaming.base.mvp.Contract;
import dream.api.dmf.cn.dreaming.base.mvp.presenter.presenter;
import dream.api.dmf.cn.dreaming.bean.HtmlBean;
import dream.api.dmf.cn.dreaming.bean.IsLoginBean;
import dream.api.dmf.cn.dreaming.utils.HtmlUtil;
import dream.api.dmf.cn.dreaming.utils.MD5Utils;

public class ShareActivity extends BaseMvpActivity<presenter> implements Contract.Iview {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.iv_back)
    ImageView mBack;
    private WebView webView;
    private SharedPreferences sharedPreferences;
    private String username;
    private String mUid;
    private String mShell;

    @Override
    public void getThisData() {
        getDate();
    }

    @Override
    public void getInitData() {
        sharedPreferences = getSharedPreferences(UserApi.SP, Context.MODE_PRIVATE);
        mUid = sharedPreferences.getString(UserApi.Uid, "");
        mShell = sharedPreferences.getString(UserApi.Shell, "");
        username = sharedPreferences.getString(UserApi.UserName, "");
        mTitle.setText("客服中心");
        mTitle.setTextSize(18);
        webView = findViewById(R.id.mweb);

        webView.setVerticalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.getSettings().setSupportZoom(true);//支持缩放
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);//不显示缩放按钮
        if (Build.VERSION.SDK_INT > 21) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        webView.getSettings().setDomStorageEnabled(false);

    }

    @Override
    public int getView() {
        return R.layout.activity_share;
    }

    @Override
    protected presenter createP() {
        return new presenter();
    }


    public void getDate() {
        HashMap<String, Object> headmap = new HashMap<>();
        HashMap<String, Object> map = new HashMap<>();
        mPresenter.postData(UserApi.getCenter, headmap, map, HtmlBean.class);
    }


    @Override
    public void getData(Object object) {
        if (object instanceof HtmlBean) {
            HtmlBean bean = (HtmlBean) object;
            if (TextUtils.isEmpty(bean.content)) {
                return;
            }
            String temp = HtmlUtil
                    .modifySrcFromImg(bean.content, 300, 200);
            StringBuffer buffer = new StringBuffer(
                    "<html><body>").append(temp)
                    .append("</body></html>");
            webView.loadData(buffer.toString(), "text/html;utf-8", null);


        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
