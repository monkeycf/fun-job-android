package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.LearnApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.learn.getModule.LearnGetModuleResModule;
import csr.dmt.zust.edu.cn.funjobapplication.view.JSBridge.JSBridgeActivity;

/**
 * created by monkeycf on 2019/12/15
 * 学习模块Fragment
 */
public class LearnFragment extends Fragment {

    private LearnAdapter mLearnAdapter;
    private static final String TAG = LearnFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_learn_fragment, container, false);

        RecyclerView LearnModuleRecyclerView;
        LearnModuleRecyclerView = v.findViewById(R.id.rv_learn_wall);
        LearnModuleRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        getModules();
        mLearnAdapter = new LearnAdapter(new ArrayList<>());
        LearnModuleRecyclerView.setAdapter(mLearnAdapter);
        return v;
    }

    /**
     * 获取模块数据
     */
    private void getModules() {
        final String LEARN_MODULES_INDEX = "1";
        LearnApi.getInstance().getModules(LEARN_MODULES_INDEX,
                new IHttpCallBack<BaseResult<List<LearnGetModuleResModule>>>() {
                    @Override
                    public void successCallBack(BaseResult<List<LearnGetModuleResModule>> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            mLearnAdapter.addModules(data.getData());
                            mLearnAdapter.notifyDataSetChanged();
                        } else {
                            Log.e(TAG, "getModules was error:::" + data.getMsg());
                            Toast.makeText(getContext(), R.string.app_error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "getModules was error:::" + msg);
                    }
                });
    }

    /**
     * Adapter
     */
    private class LearnAdapter extends RecyclerView.Adapter<LearnHolder> {
        private List<LearnGetModuleResModule> mLearnGetModuleResModules;

        private LearnAdapter(List<LearnGetModuleResModule> learnGetModuleResModules) {
            mLearnGetModuleResModules = learnGetModuleResModules;
        }

        /**
         * 添加模块
         *
         * @param learnGetModuleResModules 模块数组
         */
        private void addModules(List<LearnGetModuleResModule> learnGetModuleResModules) {
            mLearnGetModuleResModules.addAll(learnGetModuleResModules);
        }

        @Override
        public int getItemCount() {
            return mLearnGetModuleResModules.size();
        }

        @Override
        public void onBindViewHolder(@NonNull LearnHolder holder, int position) {
            holder.bind(mLearnGetModuleResModules.get(position));
        }

        @NonNull
        @Override
        public LearnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LearnHolder(layoutInflater, parent);
        }
    }

    private class LearnHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView mTextView;

        private LearnHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.index_learn_wall_item, parent, false));
            itemView.setOnClickListener(this);
            mImageView = itemView.findViewById(R.id.iv_wall_item);
            mTextView = itemView.findViewById(R.id.tv_wall_item);
        }

        private void bind(LearnGetModuleResModule learnGetModuleResModule) {
            if (getActivity() == null) {
                throw new IllegalArgumentException("LearnHolder:bind:::getActivity is null...");
            }
            mTextView.setText(learnGetModuleResModule.getTitle());
            Glide.with(getActivity())
                    .load(learnGetModuleResModule.getCover())
                    .override(800, 600)
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_false)
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = JSBridgeActivity.newIntent(getActivity(), mTextView.getText().toString());
            startActivity(intent);
        }
    }
}
