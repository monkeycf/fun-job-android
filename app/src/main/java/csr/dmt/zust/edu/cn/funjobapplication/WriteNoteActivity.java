package csr.dmt.zust.edu.cn.funjobapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.zzhoujay.richtext.RichText;

public class WriteNoteActivity extends AppCompatActivity {

    private TextView mTvShow;
    private EditText mEtEdit;
    MapView mMapView = null;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        mTvShow = findViewById(R.id.show_text_view);
        mEtEdit = findViewById(R.id.et_edit);
        InitActionBar();
        findViewById(R.id.btn_show).setOnClickListener(v -> changeValue());
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    public void changeValue() {
        // 设置为Markdown
        RichText.fromMarkdown(mEtEdit.getText().toString()).into(mTvShow);
    }

    protected void InitActionBar() {
        ActionBar actionBar = this.getSupportActionBar(); // 获取ActionBar
        if (actionBar == null) {
            return;
        }
        actionBar.setTitle(""); // 设置标题为空

        //左侧按钮
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_cancel);
    }
}
