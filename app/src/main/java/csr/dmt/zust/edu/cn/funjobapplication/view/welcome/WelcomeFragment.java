package csr.dmt.zust.edu.cn.funjobapplication.view.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import csr.dmt.zust.edu.cn.funjobapplication.R;

/**
 * created by monkeycf on 2019/12/24
 */
public class WelcomeFragment extends Fragment {

    private static final String WELCOME_FRAGMENT_POSITION_KEY = "WELCOME_FRAGMENT_POSITION_KEY";
    private static final String WELCOME_FRAGMENT_IMAGE_ID_KEY = "WELCOME_FRAGMENT_IMAGE_ID_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int position = -1; // 位置序号
        int imageId = -1; // 图片的资源编号
        if (getArguments() != null) { // 如果碎片携带有包裹，则打开包裹获取参数信息
            position = getArguments().getInt(WELCOME_FRAGMENT_POSITION_KEY, 0);
            imageId = getArguments().getInt(WELCOME_FRAGMENT_IMAGE_ID_KEY, 0);
        }
        View v = inflater.inflate(R.layout.item_welcome, container, false);
        ImageView imageViewPage = v.findViewById(R.id.iv_welcome_page);
        Button btnStart = v.findViewById(R.id.btn_welcome_start);
        if (imageId != -1) {
            Glide.with(getContext())
                    .load(imageId)
                    .into(imageViewPage);
        }
        // 如果是最后一个引导页，则显示入口按钮，以便用户点击按钮进入首页
        if (position == WelcomeInteractionActivity.WELCOME_PAGE_COUNT - 1) {
            btnStart.setVisibility(View.VISIBLE);
            btnStart.setOnClickListener(view -> Toast.makeText(getContext(), "欢迎您开启美好生活", Toast.LENGTH_SHORT).show());
        }
        return v; // 返回该碎片的视图对象
    }

    /**
     * 获取WelcomeFragment实例
     *
     * @param position 下标
     * @param imageId  图片id
     * @return WelcomeFragment实例
     */
    public static WelcomeFragment newInstance(int position, int imageId) {
        Bundle bundle = new Bundle();
        bundle.putInt(WELCOME_FRAGMENT_POSITION_KEY, position);
        bundle.putInt(WELCOME_FRAGMENT_IMAGE_ID_KEY, imageId);

        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
