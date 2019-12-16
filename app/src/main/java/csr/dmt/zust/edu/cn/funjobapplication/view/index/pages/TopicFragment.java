package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.module.ClassifyFragment;

/**
 * created by monkeycf on 2019/12/15
 * 主题Fragment
 */
public class TopicFragment extends Fragment {

    private TabLayout mTabLayout;
    /**
     * ViewPager是android扩展包中的类，这个类可以让用户左右切换当前的view
     * 继承于ViewGroup
     */
    private ViewPager mContentViewPager;

    private List<String> mTabIndicators;
    private List<Fragment> mFragmentList;
    private ContentPagerAdapter mContentPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_fragment_topic, container, false);
        mTabLayout = (TabLayout) v.findViewById(R.id.tl_tab);
        mContentViewPager = (ViewPager) v.findViewById(R.id.vp_content);
        initTab();
        initContent();
        return v;
    }

    /**
     * 初始化tabLayout导航栏样式
     */
    private void initTab() {
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabTextColors(Color.BLUE, Color.GREEN);
        mTabLayout.setSelectedTabIndicatorColor(Color.RED);
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mContentViewPager); // 设置关联的ViewPager
    }

    /**
     * 初始化内容
     */
    private void initContent() {
        // 设置标题
        mTabIndicators = new ArrayList<>();
        mFragmentList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mTabIndicators.add("Tab " + i);
            // 添加Fragment
            mFragmentList.add(new ClassifyFragment(i + 1));
        }
        mContentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager());
        mContentViewPager.setAdapter(mContentPagerAdapter);
    }


    class ContentPagerAdapter extends FragmentPagerAdapter {

        // setUserVisibleHint
        // setMaxLifecycle

        /**
         * // @Override
         * public void onResume() {
         * super.onResume();
         * Logger.d(TAG, "onResume");
         * if (!isLoaded) {
         * initData();
         * }
         * }
         *
         * @param fm FragmentManager管理器
         */
        public ContentPagerAdapter(FragmentManager fm) {
            // 设置为 setMaxLifecycle,Fragment懒加载
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mTabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabIndicators.get(position);
        }
    }
}
