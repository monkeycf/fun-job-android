package csr.dmt.zust.edu.cn.funjobapplication.view.welcome;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * created by monkeycf on 2019/12/24
 */
public class WelcomeAdapter extends FragmentPagerAdapter {
    private List<Integer> mImageArray;
    private IWelcomeInteraction listener;
    private int mPosition;

    public interface IWelcomeInteraction {
        void onChangeHandler(int position);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // 当页面进行切换时触发
        if (mPosition != position) {
            listener.onChangeHandler(position);
            mPosition = position;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mImageArray.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return WelcomeFragment.newInstance(position, mImageArray.get(position));
    }

    public WelcomeAdapter(Context context, @NonNull FragmentManager fm, int behavior, List<Integer> imageArray) {
        super(fm, behavior);
        mImageArray = imageArray;
        mPosition = WelcomeInteractionActivity.WELCOME_PAGE_BEGIN_INDEX;
        if (context instanceof IWelcomeInteraction) {
            listener = (IWelcomeInteraction) context;
        } else {
            throw new IllegalArgumentException("Context not implement IWelcomeInteraction...");
        }
    }
}