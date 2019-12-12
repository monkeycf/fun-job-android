package csr.dmt.zust.edu.cn.funjobapplication.bottomNavigationBar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import csr.dmt.zust.edu.cn.funjobapplication.R;

/**
 * created by monkeycf on 2019/12/12
 */
public class BottomNavigation {
    public static void InitBottomNavigationBar(BottomNavigationBar bottomNavigationBar) {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Home"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Books"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Music"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Movies & TV"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "Games"))
                .initialise();//所有的设置需在调用该方法前完成
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //未选中->选中
            }

            @Override
            public void onTabUnselected(int position) {
                //选中->未选中
            }

            @Override
            public void onTabReselected(int position) {
                //选中->选中
            }
        });
    }
}
