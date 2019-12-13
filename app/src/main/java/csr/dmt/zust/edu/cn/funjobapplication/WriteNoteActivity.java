package csr.dmt.zust.edu.cn.funjobapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zzhoujay.richtext.RichText;

public class WriteNoteActivity extends AppCompatActivity {

    private TextView mTvShow;
    private EditText mEtEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        mTvShow = findViewById(R.id.show_text_view);
        mEtEdit = findViewById(R.id.et_edit);
        InitActionBar();
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 设置为Markdown
                RichText.fromMarkdown(mEtEdit.getText().toString()).into(mTvShow);
            }
        });
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
