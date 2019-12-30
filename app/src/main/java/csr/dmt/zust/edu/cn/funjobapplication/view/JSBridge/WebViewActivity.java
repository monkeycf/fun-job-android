package csr.dmt.zust.edu.cn.funjobapplication.view.JSBridge;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class WebViewActivity extends AppCompatActivity {
    private static final String WEB_VIEW_URL_KEY = "WEB_VIEW_URL_KEY";
    private static final String WEB_VIEW_TITLE_KEY = "WEB_VIEW_TITLE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.wv_web_view);

        String url = (String) getIntent().getSerializableExtra(WEB_VIEW_URL_KEY);
        String title = (String) getIntent().getSerializableExtra(WEB_VIEW_TITLE_KEY);

        webView.loadUrl(url);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /**
     * 新建intent
     *
     * @param context Context
     * @param url     需要显示网页的url
     * @return intent实例
     */
    public static Intent newIntent(Context context, String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(WEB_VIEW_URL_KEY, url);
        bundle.putSerializable(WEB_VIEW_TITLE_KEY, title);

        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
}
