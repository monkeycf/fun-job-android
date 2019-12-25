package csr.dmt.zust.edu.cn.funjobapplication.view.welcome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class WelcomeInteractionActivity extends AppCompatActivity implements WelcomeAdapter.IWelcomeInteraction {
    private List<Integer> mWelcomeImageArray = new ArrayList<>();
    private RadioGroup mRadioGroupPageIndex;
    public static final int WELCOME_PAGE_COUNT = 4; // 引导页的数量
    public static final int WELCOME_PAGE_BEGIN_INDEX = 0; // 起始下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ViewPager viewPagerWelcome = findViewById(R.id.vp_welcome);
        mRadioGroupPageIndex = findViewById(R.id.rg_welcome_indicate);

        mWelcomeImageArray.add(R.drawable.logo_vue);
        mWelcomeImageArray.add(R.drawable.logo_vue);
        mWelcomeImageArray.add(R.drawable.logo_vue);
        mWelcomeImageArray.add(R.drawable.logo_vue);

        // 设置adapter
        WelcomeAdapter adapter = new WelcomeAdapter(WelcomeInteractionActivity.this,
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                mWelcomeImageArray);
        viewPagerWelcome.setAdapter(adapter);
        viewPagerWelcome.setCurrentItem(WELCOME_PAGE_BEGIN_INDEX);

        // 生成下部按钮
        for (int j = 0; j < WELCOME_PAGE_COUNT; j++) {
            RadioButton radio = new RadioButton(WelcomeInteractionActivity.this);
            radio.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            radio.setPadding(10, 10, 10, 10);
            mRadioGroupPageIndex.addView(radio);
        }
        changeRadioGroup(WELCOME_PAGE_BEGIN_INDEX);
    }

    @Override
    public void onChangeHandler(int position) {
        changeRadioGroup(position);
    }

    /**
     * 改变按钮组显示的点击状态
     *
     * @param position 显示为点击状态的下标
     */
    private void changeRadioGroup(int position) {
        ((RadioButton) mRadioGroupPageIndex.getChildAt(position)).setChecked(true);
    }
}
