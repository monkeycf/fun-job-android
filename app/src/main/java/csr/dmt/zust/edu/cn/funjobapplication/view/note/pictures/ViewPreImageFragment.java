package csr.dmt.zust.edu.cn.funjobapplication.view.note.pictures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import csr.dmt.zust.edu.cn.funjobapplication.R;

public class ViewPreImageFragment extends Fragment {
    private Picture mPicture;
    private ImageView mImageView;
    private static final String VIEW_PRE_IMAGE_FRAGMENT_KEY = "VIEW_PRE_IMAGE_FRAGMENT_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pre_view, container, false);
        mImageView = v.findViewById(R.id.iv_pre_image);
        if (getArguments() != null) {
            mPicture = (Picture) getArguments().getSerializable(VIEW_PRE_IMAGE_FRAGMENT_KEY);
            Glide.with(getActivity())
                    .load(mPicture.getPath())
                    .into(mImageView);
        }
        return v;
    }

    public static ViewPreImageFragment getInstance(Picture picture) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(VIEW_PRE_IMAGE_FRAGMENT_KEY, picture);

        ViewPreImageFragment viewPreImageFragment = new ViewPreImageFragment();
        viewPreImageFragment.setArguments(bundle);
        return viewPreImageFragment;
    }

    private ViewPreImageFragment() {
    }
}
