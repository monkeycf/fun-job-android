package csr.dmt.zust.edu.cn.funjobapplication.view.index;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.PersonalFragment;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.module.FragmentItem;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.BottomNavigationFragment;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.LearnFragment;
import csr.dmt.zust.edu.cn.funjobapplication.view.index.pages.TopicFragment;

public class IndexActivity extends AppCompatActivity implements BottomNavigationFragment.FragmentInteraction {
    private FragmentManager mFragmentManager;
    private Fragment mFragmentButtonNagivation;
    private List<FragmentItem> mFragmentList; // 存储Fragment数组
    private static final String INDEX_BOTTOM_NAVIGATION_FRAGMENT_NAME = "INDEX_BOTTOM_NAVIGATION";
    private static final String INDEX_TOPIC_FRAGMENT_FRAGMENT_NAME = "INDEX_TOPIC_FRAGMENT_FRAGMENT_NAME";
    private static final String INDEX_LEARN_FRAGMENT_FRAGMENT_NAME = "INDEX_LEARN_FRAGMENT_FRAGMENT_NAME";
    private static final String INDEX_PERSONAL_FRAGMENT_FRAGMENT_NAME = "INDEX_PERSONAL_FRAGMENT_FRAGMENT_NAME";

    private int mSelectPosition = 0;
    private int mUnSelectPosition = 0;

    /**
     * 底部导航栏item选中处理函数
     *
     * @param position 选中标签的序号
     */
    @Override
    public void onBottomNavigationItemSelected(int position) {
        System.out.println("out" + position);
        mSelectPosition = position;
//        Toast.makeText(this, position + "", Toast.LENGTH_LONG).show();
//        FragmentItem fragmentItem = mFragmentList.get(position);
//        addFragment(fragmentItem.getFragment(), fragmentItem.getFragmentName());

    }

    /**
     * 底部导航栏item取消选中处理函数
     *
     * @param position 取消标签的序号
     */
    @Override
    public void onBottomNavigationItemUnSelected(int position) {
        System.out.println("hide" + position);
//        hideFragment(mFragmentList.get(position).getFragment());
        mUnSelectPosition = position;
        addFragment(mFragmentList.get(mUnSelectPosition).getFragment(),
                mFragmentList.get(mSelectPosition).getFragment(),
                mFragmentList.get(mSelectPosition).getFragmentName());
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
        for (int i = 0; i < BottomNavigationFragment.BOTTOM_NAVIGATION_ITEM_NUMBER; i++) {
            switch (i) {
                case 1:
                    // 主题
                    mFragmentList.add(new FragmentItem(new TopicFragment(), INDEX_TOPIC_FRAGMENT_FRAGMENT_NAME));
                    break;
                case 0:
                case 2:
                    // 学习
                    mFragmentList.add(new FragmentItem(new LearnFragment(), INDEX_LEARN_FRAGMENT_FRAGMENT_NAME));
                    break;
                case 3:
                    // 个人中心
                    mFragmentList.add(new FragmentItem(new PersonalFragment(), INDEX_PERSONAL_FRAGMENT_FRAGMENT_NAME));
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
     * @param formFragment 需要隐藏的fragment
     * @param toFragment   需要显示的fragment
     * @param name         需要显示的fragment的名字
     */
    private void addFragment(Fragment formFragment, Fragment toFragment, String name) {
        if (!toFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .hide(formFragment)
                    .add(R.id.frame_layout_view, toFragment, name)
                    .commit();
        } else {
            mFragmentManager.beginTransaction()
                    .hide(formFragment)
                    .show(toFragment)
                    .commit();
        }
    }
}
