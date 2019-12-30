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
    private static final String LEARN_FRAGMENT_JS_BRIDGE_KEY = "LEARN_FRAGMENT_JS_BRIDGE_KEY";
    private static final String BASE_URL = "http://app15.chensenran.top/index.html?title=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js_bridge);
        // 获取传入的title
        String title = (String) getIntent().getSerializableExtra(LEARN_FRAGMENT_JS_BRIDGE_KEY);

        BridgeWebView bridgeWebView = findViewById(R.id.JsBridgeWebView);
        // 清楚缓存
        bridgeWebView.clearCache(true);

        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.setWebChromeClient(new WebChromeClient());
        bridgeWebView.setWebViewClient(new BridgeWebViewClient(bridgeWebView));

        bridgeWebView.loadUrl(BASE_URL + title);

        /**
         * js发送给按住消息   submitFromWeb 是js调用的方法名    安卓\返回给js
         */
        bridgeWebView.registerHandler("submitFromWeb", (data, callBackFunction) -> {
            //显示接收的消息
            //返回给html的消息
            //            callBackFunction.onCallBack("返回给Toast的alert");
            finish();
        });

//        mButton.setOnClickListener(v -> {
//            /**
//             * 给Html发消息   js接收并返回data
//             */
//            mBridgeWebView.callHandler("functionInJs", "调用js的方法", (data) -> {
//                showToast("===" + data);
//            });
//        });

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
