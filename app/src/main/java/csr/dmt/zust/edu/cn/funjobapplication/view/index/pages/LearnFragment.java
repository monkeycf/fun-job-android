package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.view.JSBridge.JSBridgeActivity;

/**
 * created by monkeycf on 2019/12/15
 * 学习模块Fragment
 */
public class LearnFragment extends Fragment {

    private Button mButtonShow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_learn_fragment, container, false);
        mButtonShow = v.findViewById(R.id.btn_learn_item_show);
        mButtonShow.setOnClickListener(view -> {
            Intent intent = JSBridgeActivity.newIntent(getActivity(), "123");
            startActivity(intent);
        });
        return v;
    }
}
