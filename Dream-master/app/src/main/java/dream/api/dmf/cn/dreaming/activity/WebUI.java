package dream.api.dmf.cn.dreaming.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dream.api.dmf.cn.dreaming.R;

/**
 * Created by SongNing on 2019/7/1.
 * email: 836883891@qq.com
 */
public class WebUI extends Activity {

    private WebView webView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regst);
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra("data")) {
            finish();
            return;
        }
        mUrl = intent.getStringExtra("data");
        webView = findViewById(R.id.webView);
        initWebsettings();
        webView.loadUrl(mUrl);
    }

    private void initWebsettings() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }

        });
    }
}
