package csr.dmt.zust.edu.cn.funjobapplication.view.topic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.view.note.NoteTextShowFragment;

public class HomePageActivity extends AppCompatActivity {

    private TabLayout mTabTl;
    private ViewPager mContentVp;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mTabTl = (TabLayout) findViewById(R.id.tl_tab);
        mContentVp = (ViewPager) findViewById(R.id.vp_content);

        initContent();
        initTab();
    }

    private void initTab() {
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(Color.BLUE, Color.GREEN);
        mTabTl.setSelectedTabIndicatorColor(Color.RED);
        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(mContentVp);
    }

    private void initContent() {
        // 设置标题
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tabIndicators.add("Tab " + i);
        }
        tabFragments = new ArrayList<>();
        for (String s : tabIndicators) {
            // 添加Fragment
            tabFragments.add(NoteTextShowFragment.getInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
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
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }
}
