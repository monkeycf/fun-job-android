package csr.dmt.zust.edu.cn.funjobapplication.view.index;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FragmentItem;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.BottomNavigationFragment;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.LearnFragment;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.TopicFragment;

public class IndexActivity extends AppCompatActivity implements BottomNavigationFragment.FragmentInteraction {
    private FragmentManager mFragmentManager;
    private Fragment mFragmentButtonNagivation;
    private List<FragmentItem> mFragmentList; // 存储Fragment数组
    private static final String INDEX_BOTTOM_NAVIGATION_FRAGMENT_NAME = "INDEX_BOTTOM_NAVIGATION";
    private static final String INDEX_TOPIC_FRAGMENT_FRAGMENT_NAME =
            "INDEX_TOPIC_FRAGMENT_FRAGMENT_NAME";
    private static final String INDEX_LEARN_FRAGMENT_FRAGMENT_NAME =
            "INDEX_LEARN_FRAGMENT_FRAGMENT_NAME";

    /**
     * 底部导航栏item选中处理函数
     *
     * @param position 选中标签的序号
     */
    @Override
    public void onBottomNavigationItemSelected(int position) {
        Toast.makeText(this, position + "", Toast.LENGTH_LONG).show();
        FragmentItem fragmentItem = mFragmentList.get(position);
        addFragment(fragmentItem.getFragment(), fragmentItem.getFragmentName());
    }

    /**
     * 底部导航栏item取消选中处理函数
     *
     * @param position 取消标签的序号
     */
    @Override
    public void onBottomNavigationItemUnSelected(int position) {
        hideFragment(mFragmentList.get(position).getFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initFragmentList(); // 初始化view中的Fragment数组
        initFragment(); // 初始化view的Fragment和底部导航栏
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.findFragmentById(R.id.frame_layout_view);

        // 底部导航栏
        mFragmentButtonNagivation = BottomNavigationFragment.getInstance();
        // 上部view区域
        FragmentItem fragmentItem = mFragmentList.get(0);

        mFragmentManager.beginTransaction()
                .add(R.id.frame_layout_bottom_navigation,
                        mFragmentButtonNagivation,
                        INDEX_BOTTOM_NAVIGATION_FRAGMENT_NAME)
                .add(R.id.frame_layout_view,
                        fragmentItem.getFragment(),
                        fragmentItem.getFragmentName())
                .commit();
    }

    /**
     * 初始化view的Fragment数组
     */
    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < BottomNavigationFragment.BOTTOM_NAVIGATIOIN_ITEM_NUMBER; i++) {
            switch (i) {
                case 0:
                    // 主题
                    mFragmentList.add(new FragmentItem(
                            new TopicFragment(), INDEX_TOPIC_FRAGMENT_FRAGMENT_NAME));
                    break;
                case 1:
                    // 学习
                    mFragmentList.add(new FragmentItem(
                            new LearnFragment(), INDEX_LEARN_FRAGMENT_FRAGMENT_NAME));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 隐藏fragment
     *
     * @param fragment 需要隐藏的fragment
     */
    private void hideFragment(Fragment fragment) {
        mFragmentManager.beginTransaction()
                .hide(fragment)
                .commit();
    }

    /**
     * 添加fragment
     *
     * @param fragment 需要显示的fragment
     * @param name     需要显示的fragment的名字
     */
    private void addFragment(Fragment fragment, String name) {
        if (!fragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(fragment, name);
        } else {
            mFragmentManager.beginTransaction()
                    .show(fragment)
                    .commit();
        }
    }
}
