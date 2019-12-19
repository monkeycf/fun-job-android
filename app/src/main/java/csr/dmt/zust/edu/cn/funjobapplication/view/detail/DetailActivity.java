package csr.dmt.zust.edu.cn.funjobapplication.view.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.NoteCreateActivity;

public class DetailActivity extends AppCompatActivity {

    private static final String DETAIL_TOPIC_ID_KEY = "DETAIL_TOPIC_ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViewById(R.id.btn_detail_edit).setOnClickListener(v -> {
            Intent intent = NoteCreateActivity.newIntent(this);
            startActivity(intent);
        });
    }

    /**
     * 获得intent
     *
     * @param context Context
     * @param topicId 主题id
     * @return intent
     */
    public static Intent newIntent(Context context, String topicId) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(DETAIL_TOPIC_ID_KEY, topicId);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtras(bundle);
        return intent;
    }
}
