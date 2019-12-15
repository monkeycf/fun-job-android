package csr.dmt.zust.edu.cn.funjobapplication.module;

import androidx.fragment.app.Fragment;

/**
* created by monkeycf on 2019/12/15
*/
public class FragmentItem {
    private Fragment mFragment;
    private String mFragmentName;

    public FragmentItem(Fragment fragment, String fragmentName) {
        mFragment = fragment;
        mFragmentName = fragmentName;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getFragmentName() {
        return mFragmentName;
    }

    public void setFragmentName(String fragmentName) {
        mFragmentName = fragmentName;
    }
}
