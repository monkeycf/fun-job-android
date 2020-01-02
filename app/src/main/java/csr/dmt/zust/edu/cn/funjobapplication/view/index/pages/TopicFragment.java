package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

    private List<Integer> mTabIndicators;
    private List<Fragment> mFragmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_fragment_topic, container, false);
        mTabLayout = v.findViewById(R.id.tl_tab);
        mContentViewPager = v.findViewById(R.id.vp_content);
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

        for (int i = 0; i < 11; i++) {
            // 添加Fragment
            mFragmentList.add(new ClassifyFragment(i + 1));
            switch (i) {
                case 0:
                    mTabIndicators.add(R.string.classify_vue);
                    break;
                case 1:
                    mTabIndicators.add(R.string.classify_webpack);
                    break;
                case 2:
                    mTabIndicators.add(R.string.classify_babel);
                    break;
                case 3:
                    mTabIndicators.add(R.string.classify_HTML);
                    break;
                case 4:
                    mTabIndicators.add(R.string.classify_CSS);
                    break;
                case 5:
                    mTabIndicators.add(R.string.classify_browser);
                    break;
                case 6:
                    mTabIndicators.add(R.string.classify_performance);
                    break;
                case 7:
                    mTabIndicators.add(R.string.classify_network);
                    break;
                case 8:
                    mTabIndicators.add(R.string.classify_node);
                    break;
                case 9:
                    mTabIndicators.add(R.string.classify_engineering);
                    break;
                case 10:
                    mTabIndicators.add(R.string.classify_react);
                    break;
                default:
                    mTabIndicators.add(R.string.classify_vue);
                    break;

            }
        }
        // FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        // 设置为 setMaxLifecycle,Fragment懒加载
        ContentPagerAdapter contentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContentViewPager.setAdapter(contentPagerAdapter);
    }


    class ContentPagerAdapter extends FragmentPagerAdapter {

        /**
         * @param fm       FragmentManager管理器
         * @param behavior 确定是否只有当前片段处于恢复状态
         */
        private ContentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
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
            return getString(mTabIndicators.get(position));
        }
    }
}
