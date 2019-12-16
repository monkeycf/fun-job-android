package csr.dmt.zust.edu.cn.funjobapplication.view.JSBridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class JSBridgeActivity extends AppCompatActivity {
    private BridgeWebView mBridgeWebView;
    private Button mButton;
    private static final String LEARN_FRAGMENT_JS_BRIDGE_KEY = "LEARN_FRAGMENT_JS_BRIDGE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
        // 获取传入的title
        String title = (String) getIntent().getSerializableExtra(LEARN_FRAGMENT_JS_BRIDGE_KEY);

        mButton = (Button) findViewById(R.id.button3);
        mBridgeWebView = (BridgeWebView) findViewById(R.id.JsBridgeWebView);
        // 清楚缓存
        mBridgeWebView.clearCache(true);

        mBridgeWebView.setDefaultHandler(new DefaultHandler());
        mBridgeWebView.setWebChromeClient(new WebChromeClient());
        mBridgeWebView.setWebViewClient(new BridgeWebViewClient(mBridgeWebView));

        mBridgeWebView.loadUrl("http://app5.chensenran.top/index.html?title=" + title);

        /**
         * js发送给按住消息   submitFromWeb 是js调用的方法名    安卓\返回给js
         */
        mBridgeWebView.registerHandler("submitFromWeb", (data, callBackFunction) -> {
            //显示接收的消息
            showToast(data);
            //返回给html的消息
            callBackFunction.onCallBack("返回给Toast的alert");
        });


        mButton.setOnClickListener(v -> {
            /**
             * 给Html发消息   js接收并返回data
             */
            mBridgeWebView.callHandler("functionInJs", "调用js的方法", (data) -> {
                showToast("===" + data);
            });
        });

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取Intent
     *
     * @param packageContext 开始的Activity
     * @param name           加入的值
     * @return Intent实例
     */
    public static Intent newIntent(Context packageContext, String name) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(LEARN_FRAGMENT_JS_BRIDGE_KEY, name);

        Intent intent = new Intent(packageContext, JSBridgeActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
}
