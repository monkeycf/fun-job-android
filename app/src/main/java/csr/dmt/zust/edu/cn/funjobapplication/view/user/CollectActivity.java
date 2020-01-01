package csr.dmt.zust.edu.cn.funjobapplication.view.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.module.database.helper.FunJobDbHelper;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.view.JSBridge.WebViewActivity;

public class CollectActivity extends AppCompatActivity {
    private static final String TAG = CollectActivity.class.getSimpleName();
    private CollectAdapter mCollectAdapter = new CollectAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        getTopics();
        getSupportActionBar().setTitle("我的收藏");

        RecyclerView recyclerViewCollect = findViewById(R.id.rv_collect_personal);
        recyclerViewCollect.setLayoutManager(new LinearLayoutManager(CollectActivity.this));
        recyclerViewCollect.setAdapter(mCollectAdapter);
    }

    private void getTopics() {
        TopicApi.getInstance().getTopicCollected(getUserInfoByDataBase(),
                new IHttpCallBack<BaseResult<List<TopicInfoModule>>>() {
                    @Override
                    public void successCallBack(BaseResult<List<TopicInfoModule>> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            mCollectAdapter.addTopicInfoModule(data.getData());
                            mCollectAdapter.notifyDataSetChanged();
                        } else {
                            Log.e(TAG, "getTopicCollected was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "getTopicCollected was error:::" + msg);
                    }
                });
    }

    /**
     * 获取用户信息（从数据库中）
     */
    private String getUserInfoByDataBase() {
        FunJobDbHelper funJobDbHelper = new FunJobDbHelper(CollectActivity.this);
        return funJobDbHelper.getUserInfo(funJobDbHelper).getId();
    }

    private class CollectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FoldingCell mFc;
        private TextView mTextViewTopicName;
        private TextView mTextViewTopicCreateTime;
        private TextView mTextViewShowTopicName;
        private TextView mTextViewShowTopicContent;
        private TextView mTextViewShowTopicCreateTime;
        private Button mButtonShow;

        public CollectHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_personal_collect, parent, false));
            itemView.setOnClickListener(this);
            mFc = itemView.findViewById(R.id.folding_cell);
            mTextViewTopicName = itemView.findViewById(R.id.cell_tv_topic_name);
            mTextViewTopicCreateTime = itemView.findViewById(R.id.cell_tv_topic_create_time);
            mTextViewShowTopicName = itemView.findViewById(R.id.cell_tv_show_topic_name);
            mTextViewShowTopicContent = itemView.findViewById(R.id.cell_tv_show_topic_content);
            mTextViewShowTopicCreateTime = itemView.findViewById(R.id.cell_tv_show_topic_create_time);
            mButtonShow = itemView.findViewById(R.id.cell_btn_show_topic);
        }

        public void bind(TopicInfoModule topicInfoModule) {
            mTextViewTopicName.setText(topicInfoModule.getTitle());
            mTextViewTopicCreateTime.setText(topicInfoModule.getCreateTime());
            mTextViewShowTopicName.setText(topicInfoModule.getTitle());
            mTextViewShowTopicContent.setText(topicInfoModule.getContent());
            mTextViewShowTopicCreateTime.setText(topicInfoModule.getCreateTime());
            mButtonShow.setOnClickListener(v -> {
                Intent intent = WebViewActivity.newIntent(
                        CollectActivity.this, topicInfoModule.getAnswerUrl(), topicInfoModule.getTitle());
                startActivity(intent);
            });
        }

        @Override
        public void onClick(View v) {
            mFc.toggle(false);
        }
    }

    private class CollectAdapter extends RecyclerView.Adapter<CollectHolder> {
        List<TopicInfoModule> mTopicInfoModules = new ArrayList<>();

        @NonNull
        @Override
        public CollectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(CollectActivity.this);
            return new CollectHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CollectHolder holder, int position) {
            holder.bind(mTopicInfoModules.get(position));
        }

        @Override
        public int getItemCount() {
            return mTopicInfoModules.size();
        }

        public void addTopicInfoModule(List<TopicInfoModule> topicInfoModules) {
            mTopicInfoModules.addAll(topicInfoModules);
        }
    }
}
