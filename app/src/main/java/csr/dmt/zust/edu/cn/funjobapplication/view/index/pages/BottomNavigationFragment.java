package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import csr.dmt.zust.edu.cn.funjobapplication.R;

/**
 * created by monkeycf on 2019/12/12
 * 底部导航栏Fragment
 */
public class BottomNavigationFragment extends Fragment {

    private static BottomNavigationFragment sBottomNavigationFragment;

    public final static int BOTTOM_NAVIGATION_ITEM_NUMBER = 4;

    private FragmentInteraction listener; // 定义用来与外部activity交互，获取到宿主context

    /**
     * 定义了所有activity必须实现的接口方法
     */
    public interface FragmentInteraction {
        void onBottomNavigationItemSelected(int position);

        void onBottomNavigationItemUnSelected(int position);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_navigation, container, false);

        BottomNavigationBar bottomNavigationBar = v.findViewById(R.id.bottom_navigation_bar);
        InitBottomNavigationBar(bottomNavigationBar);

        return v;
    }

    /**
     * 实现单例
     *
     * @return BottomNavigationFragment实例
     */
    public static BottomNavigationFragment getInstance() {
        if (sBottomNavigationFragment == null) {
            sBottomNavigationFragment = new BottomNavigationFragment();
        }
        return sBottomNavigationFragment;
    }

    /**
     * 私有构造函数
     */
    private BottomNavigationFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 判断是否实现该接口
        if (context instanceof FragmentInteraction) {
            listener = (FragmentInteraction) context; // 获取到宿主context并赋值
        } else {
            throw new IllegalArgumentException("Context not found implements IFragmentInteraction.");
        }
    }

    /**
     * 初始化底部导航栏
     *
     * @param bottomNavigationBar 底部导航栏容器
     */
    private void InitBottomNavigationBar(BottomNavigationBar bottomNavigationBar) {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Music"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Movies & TV"))
                .initialise();// 所有的设置需在调用该方法前完成

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //未选中->选中
                listener.onBottomNavigationItemSelected(position);
            }

            @Override
            public void onTabUnselected(int position) {
                //选中->未选中
                listener.onBottomNavigationItemUnSelected(position);
            }

            @Override
            public void onTabReselected(int position) {
                //选中->选中
            }
        });
    }

}
