package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.topicModule;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.TopicApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.topic.TopicInfoModule;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;

/**
 * created by monkeycf on 2019/12/15
 */
public class ClassifyFragment extends Fragment {

    private int mClassifyId;
    private RecyclerView mRecyclerView;
    private TextView mTitleTextView;
    private String TAG = "ClassifyFragment";
    private static final String CREATE_CLASSIFY_FRAGMENT_KEY = "CREATE_CLASSIFY_FRAGMENT_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_topic_fragment_classify, container, false);
        mRecyclerView = v.findViewById(R.id.rv_index_topic_classify);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // 获得数据
        getTopicDate(mClassifyId + "");
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
                            ClassifyAdapter mAdapter = new ClassifyAdapter(data.getData());
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Toast.makeText(getActivity(), "Sorry,出错了", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "That is error:" + data.getMsg());
                        }
                    }

                    @Override
                    public void ErrorCallBack(String msg) {
                        Log.e(TAG, msg);
                    }
                });
    }

    /**
     * holder
     */
    private class ClassifyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ClassifyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.index_topic_classify_gird_item, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.tv_index_topic_classify_item);
        }

        public void bind(TopicInfoModule topicInfoModule) {
            mTitleTextView.setText(topicInfoModule.getTitle());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
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

        public ClassifyAdapter(List<TopicInfoModule> topicInfoModules) {
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
