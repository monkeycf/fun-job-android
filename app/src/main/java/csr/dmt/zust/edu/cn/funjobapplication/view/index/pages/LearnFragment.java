package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;

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

    private RecyclerView mRecyclerView;
    private LearnAdapter mLearnAdapter;
    private static final String LEARN_FRAGMENT = "LEARN_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_learn_fragment, container, false);
//        mButtonShow = v.findViewById(R.id.btn_learn_item_show);
//        mButtonShow.setOnClickListener(view -> {
//            Intent intent = JSBridgeActivity.newIntent(getActivity(), "123");
//            startActivity(intent);
//        });

//        ImageView imageView = v.findViewById(R.id.iv_show);
//        Glide.with(getActivity())
//                .load("http://img.chensenran.top/1576477362006.gif")
//                .placeholder(R.drawable.ic_launcher_background)//图片加载出来前，显示的图片
//                .error(R.drawable.ic_launcher_background)//图片加载失败后，显示的图片
//                .into(imageView);

//        adapter = new ImageStageredAdapter(this);
//        recyclerView.setAdapter(adapter);
//        StaggeredGridLayoutManager staggeredGridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        SpaceViewItemLine itemDecoration = new SpaceViewItemLine(20);
//        recyclerView.addItemDecoration(itemDecoration);
        mRecyclerView = v.findViewById(R.id.rv_learn_wall);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mLearnAdapter = new LearnAdapter(new ArrayList<>());
        getModules();
        mRecyclerView.setAdapter(mLearnAdapter);
        return v;
    }

    private void getModules() {
        LearnApi.getInstance().getModules("1",
                new IHttpCallBack<BaseResult<List<LearnGetModuleResModule>>>() {
                    @Override
                    public void SuccessCallBack(BaseResult<List<LearnGetModuleResModule>> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            mLearnAdapter.addModules(data.getData());
                            mLearnAdapter.notifyDataSetChanged();
                        } else {
                            Log.e(LEARN_FRAGMENT, data.getMsg());
                        }
                    }

                    @Override
                    public void ErrorCallBack(String msg) {
                        Log.e(LEARN_FRAGMENT, msg);
                    }
                });
    }

    private class LearnAdapter extends RecyclerView.Adapter<LearnHolder> {
        private List<LearnGetModuleResModule> mLearnGetModuleResModules;

        public LearnAdapter(List<LearnGetModuleResModule> learnGetModuleResModules) {
            mLearnGetModuleResModules = learnGetModuleResModules;
        }

        public void addModules(List<LearnGetModuleResModule> learnGetModuleResModules) {
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

        public LearnHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.index_learn_wall_item, parent, false));
            itemView.setOnClickListener(this);
            mImageView = itemView.findViewById(R.id.iv_wall_item);
            mTextView = itemView.findViewById(R.id.tv_wall_item);
        }

        public void bind(LearnGetModuleResModule learnGetModuleResModule) {
            mTextView.setText(learnGetModuleResModule.getTitle());
            Glide.with(getActivity())
                    .load(learnGetModuleResModule.getCover())
                    .override(600, 800)
                    .placeholder(R.drawable.ic_loading)// 图片加载出来前，显示的图片
                    .error(R.drawable.ic_false)// 图片加载失败后，显示的图片
                    .into(mImageView);
        }


        @Override
        public void onClick(View v) {
            Intent intent = JSBridgeActivity.newIntent(getActivity(),
                    mTextView.getText().toString());
            startActivity(intent);
        }
    }
}
