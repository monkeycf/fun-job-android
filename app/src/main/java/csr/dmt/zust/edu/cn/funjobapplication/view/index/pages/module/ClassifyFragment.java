package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.view.detail.DetailActivity;

/**
 * created by monkeycf on 2019/12/15
 */
public class ClassifyFragment extends Fragment {

    private int mClassifyId;
    private String TAG = ClassifyFragment.class.getSimpleName();
    private static final String CREATE_CLASSIFY_FRAGMENT_KEY = "CREATE_CLASSIFY_FRAGMENT_KEY";
    private ClassifyAdapter mAdapter = new ClassifyAdapter(new ArrayList<>());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_topic_fragment_classify, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.rv_index_topic_classify);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 获得数据
        getTopicDate(mClassifyId + "");

        recyclerView.setAdapter(mAdapter);
        return v;
    }


    public ClassifyFragment(int id) {
        mClassifyId = id;
    }

    /**
     * 获取对应分类ID的Topic数据
     *
     * @param labelId 分类id
     */
    private void getTopicDate(String labelId) {
        TopicApi.getInstance().getTopicByLabel(labelId,
                new IHttpCallBack<BaseResult<List<TopicInfoModule>>>() {
                    @Override
                    public void SuccessCallBack(BaseResult<List<TopicInfoModule>> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            // 设置Adapter
                            mAdapter.setTopicInfoModules(data.getData());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), R.string.app_error, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "getTopicByLabel was error:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void ErrorCallBack(String msg) {
                        Log.e(TAG, "getTopicByLabel was error:::" + msg);
                    }
                });
    }

    /**
     * holder
     */
    private class ClassifyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mCreateTimeTextView;
        private TopicInfoModule mTopicInfoModule;


        private ClassifyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.index_topic_classify_list_item, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.tv_index_topic_classify_item);
            mCreateTimeTextView = itemView.findViewById(R.id.tv_index_topic_classify_create_time);
        }

        private void bind(TopicInfoModule topicInfoModule) {
            mTitleTextView.setText(topicInfoModule.getTitle());
            mCreateTimeTextView.setText(topicInfoModule.getCreateTime());
            mTopicInfoModule = topicInfoModule;
        }

        @Override
        public void onClick(View v) {
            Intent intent = DetailActivity.newIntent(getContext(),
                    new Formatter().format("%d", mTopicInfoModule.getId()).toString());
            startActivity(intent);
        }
    }

    /**
     * adapter
     */
    private class ClassifyAdapter extends RecyclerView.Adapter<ClassifyHolder> {

        private List<TopicInfoModule> mTopicInfoModules;

        @Override
        public int getItemCount() {
            return mTopicInfoModules.size();
        }

        private ClassifyAdapter(List<TopicInfoModule> topicInfoModules) {
            mTopicInfoModules = topicInfoModules;
        }

        private void setTopicInfoModules(List<TopicInfoModule> topicInfoModules) {
            mTopicInfoModules = topicInfoModules;
        }


        /**
         * 创建时
         *
         * @param parent   ViewGroup
         * @param viewType int
         * @return 新建holder
         */
        @NonNull
        @Override
        public ClassifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ClassifyHolder(layoutInflater, parent);
        }

        /**
         * 当发生修改时
         *
         * @param holder   ClassifyHolder
         * @param position int
         */
        @Override
        public void onBindViewHolder(@NonNull ClassifyHolder holder, int position) {
            TopicInfoModule topicInfoModule = mTopicInfoModules.get(position);
            holder.bind(topicInfoModule);
        }
    }
}
