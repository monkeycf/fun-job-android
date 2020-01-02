package csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class PreviewImageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String SELECT_PICTURE_PRE_KEY = "SELECT_PICTURE_PRE_KEY";
    private ViewPager mViewPager;
    private TextView mCount;
    private List<Picture> mSelectedPictures;
    private List<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        mCount = findViewById(R.id.tv_count);
        mViewPager = findViewById(R.id.viewPager);
        findViewById(R.id.btn_back).setOnClickListener(view -> finish());
        init();
    }

    /**
     * 初始化
     */
    protected void init() {
        mSelectedPictures = (ArrayList<Picture>) getIntent().getExtras().get(SELECT_PICTURE_PRE_KEY);
        if (mSelectedPictures != null) {
            for (Picture picture : mSelectedPictures) {
                mFragments.add(ViewPreImageFragment.getInstance(picture));
            }
        }

        mViewPager.setAdapter(new PreViewImageViewAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        mViewPager.addOnPageChangeListener(this);
        mCount.setText(new Formatter().format("%s/%s", 1, mSelectedPictures.size()).toString());
    }

    /**
     * 获取intent
     *
     * @param context  Context
     * @param pictures 需要预览的图片
     * @return intent
     */
    public static Intent newIntent(Context context, ArrayList<Picture> pictures) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SELECT_PICTURE_PRE_KEY, pictures);
        Intent intent = new Intent(context, PreviewImageActivity.class);
        intent.putExtras(bundle);
        return intent;
    }

    /**
     * Adapter
     */
    private class PreViewImageViewAdapter extends FragmentPagerAdapter {
        private PreViewImageViewAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCount.setText(String.format("%s/%s", (position + 1), mSelectedPictures.size()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
