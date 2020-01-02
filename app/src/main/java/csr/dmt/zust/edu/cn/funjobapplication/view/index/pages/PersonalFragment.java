package csr.dmt.zust.edu.cn.funjobapplication.view.index.pages;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.synnapps.carouselview.CarouselView;

import butterknife.BindView;
import butterknife.ButterKnife;
import csr.dmt.zust.edu.cn.funjobapplication.R;
import csr.dmt.zust.edu.cn.funjobapplication.module.FunJobConfig;
import csr.dmt.zust.edu.cn.funjobapplication.module.database.helper.FunJobDbHelper;
import csr.dmt.zust.edu.cn.funjobapplication.service.api.UserApi;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.BaseResult;
import csr.dmt.zust.edu.cn.funjobapplication.service.core.IHttpCallBack;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.login.UserLoginResModule;
import csr.dmt.zust.edu.cn.funjobapplication.service.module.user.personal.UserInfoResModule;
import csr.dmt.zust.edu.cn.funjobapplication.view.JSBridge.WebViewActivity;
import csr.dmt.zust.edu.cn.funjobapplication.view.user.CollectActivity;

/**
 * created by monkeycf on 2019/12/19
 */
public class PersonalFragment extends Fragment {

    private String TAG = PersonalFragment.class.getSimpleName();
    private int[] sampleImages = {R.drawable.bg_banner_1, R.drawable.bg_banner_2, R.drawable.bg_banner_3};
    private UserLoginResModule mUserLoginResModule;

    @BindView(R.id.iv_personal_head)
    ImageView mImageViewHead;
    @BindView(R.id.tv_personal_user_name)
    TextView mTextViewUserName;
    @BindView(R.id.tv_personal_user_intro)
    TextView mTextViewUserIntro;
    @BindView(R.id.constraint_layout_collect)
    ConstraintLayout mConstraintLayoutCollect;
    @BindView(R.id.constraint_layout_note)
    ConstraintLayout mConstraintLayoutNote;
    @BindView(R.id.constraint_layout_blog)
    ConstraintLayout mConstraintLayoutBlog;
    @BindView(R.id.constraint_layout_history)
    ConstraintLayout mConstraintLayoutHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_personal_fragment, container, false);
        ButterKnife.bind(this, v);

        // 从数据库中获取用户信息
        getUserInfoByDataBase();
        getUserInfoApi();

        CarouselView carouselView = v.findViewById(R.id.carouselView);

        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener((position, imageView) -> imageView.setImageResource(sampleImages[position]));

        mConstraintLayoutCollect.setOnClickListener(view -> {
            // 我的收藏
            Intent intent = new Intent(getActivity(), CollectActivity.class);
            startActivity(intent);
        });
        mConstraintLayoutNote.setOnClickListener(view -> {
            // 我的笔记
            Toast.makeText(getContext(), "敬请期待", Toast.LENGTH_SHORT).show();
        });
        mConstraintLayoutBlog.setOnClickListener(view -> {
            // 我的博客
            Intent intent = WebViewActivity.newIntent(getActivity(), "https://blog.chensenran.top/", "我的博客");
            startActivity(intent);
        });
        mConstraintLayoutHistory.setOnClickListener(view->{
            // 我的记录
            Toast.makeText(getContext(), "暂未开放", Toast.LENGTH_SHORT).show();
        });
        return v;
    }

    /**
     * 获取用户信息（从数据库中）
     */
    private void getUserInfoByDataBase() {
        FunJobDbHelper funJobDbHelper = new FunJobDbHelper(getContext());
        mUserLoginResModule = funJobDbHelper.getUserInfo(funJobDbHelper);
    }

    /**
     * 通过接口获取个人信息
     */
    private void getUserInfoApi() {
        UserApi.getInstance().selectUserInfo(mUserLoginResModule.getId(),
                new IHttpCallBack<BaseResult<UserInfoResModule>>() {
                    @Override
                    public void successCallBack(BaseResult<UserInfoResModule> data) {
                        if (data.getCode() == FunJobConfig.REQUEST_CODE_SUCCESS) {
                            getUserInfoHandler(data.getData());
                        } else {
                            Log.e(TAG, "selectUserInfo was err:::" + data.getMsg());
                        }
                    }

                    @Override
                    public void errorCallBack(String msg) {
                        Log.e(TAG, "selectUserInfo was err:::" + msg);
                    }
                });
    }

    /**
     * 设置用户信息
     *
     * @param userLoginResModule 用户信息模型
     */
    private void getUserInfoHandler(UserInfoResModule userLoginResModule) {
        Glide.with(getContext())
                .load(userLoginResModule.getHeadPortraitUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .placeholder(R.drawable.bg_load_default)
                .into(mImageViewHead);
        mTextViewUserName.setText(userLoginResModule.getUsername());
        mTextViewUserIntro.setText(userLoginResModule.getIntro());
    }
}
