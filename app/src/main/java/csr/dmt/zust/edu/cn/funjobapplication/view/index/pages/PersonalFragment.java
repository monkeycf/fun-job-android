package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import csr.dmt.zust.edu.cn.funjobapplication.R;

/**
 * created by monkeycf on 2019/12/19
 */
public class PersonalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_personal_fragment, container, false);
        return v;
    }
}
