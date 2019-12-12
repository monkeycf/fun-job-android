package csr.dmt.zust.edu.cn.funjobapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WriteNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        InitActionBar();
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
